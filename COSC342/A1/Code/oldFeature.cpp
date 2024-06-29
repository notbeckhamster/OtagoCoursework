#include <opencv2/opencv.hpp>
#include <opencv2/features2d.hpp>
#include "Timer.h"
#include "TMatrix.h"
using namespace cv;
using namespace std;
int main(int argc, char *argv[])
{
	std::vector<double> flann_error_projections_list;
	std::vector<double> bf_error_projections_list;
	for (int i = 1; i < 105; i++)
	{
		Timer myTimer;
		std::string indexStr = std::to_string(i);
		std::string filename1 = "../images/" + indexStr + "/" + indexStr + "_l.jpg";
		std::string filename2 = "../images/" + indexStr + "/" + indexStr + "_r.jpg";
		cv::Mat image1 = cv::imread(filename1);
		cv::Mat image2 = cv::imread(filename2);
		double elapsedTime = myTimer.read();
		std::cout << "Loading images from drive" << elapsedTime << " seconds" << std::endl;

		if (image1.empty())
		{
			std::cerr << "Could not load image from image1.jpg" << std::endl;
			return -1;
		}
		if (image2.empty())
		{
			std::cerr << "Could not load image from image2.jpg" << std::endl;
			return -1;
		}

		myTimer.reset();
		Ptr<SIFT> sift = SIFT::create();
		std::vector<cv::KeyPoint> keypoints1;
		cv::Mat descriptors1;
		sift->detectAndCompute(image1, cv::noArray(), keypoints1, descriptors1);
		std::cout << "Found " << keypoints1.size() << " features in Image 1" << std::endl;
		std::cout << "Finding features in image1 took " << myTimer.read() << " seconds" << std::endl;

		Ptr<SIFT> sift2 = SIFT::create();
		std::vector<cv::KeyPoint> keypoints2;
		cv::Mat descriptors2;
		sift->detectAndCompute(image2, cv::noArray(), keypoints2, descriptors2);
		std::cout << "Found " << keypoints2.size() << " features in Image 2" << std::endl;

		// cv::namedWindow("Display 1");
		// cv::imshow("Display 1", image1);
		// cv::namedWindow("Display 2");
		// cv::imshow("Display 2", image2);

		// cv::Mat kptImage1;
		// cv::drawKeypoints(image1, keypoints1, kptImage1, cv::Scalar(0, 255, 0),
		// 				  cv::DrawMatchesFlags::DRAW_RICH_KEYPOINTS);
		// cv::namedWindow("Display 1 Features");
		// cv::imshow("Display 1 Features", kptImage1);

		// cv::Mat kptImage2;
		// cv::drawKeypoints(image2, keypoints2, kptImage2, cv::Scalar(0, 255, 0),
		// 				  cv::DrawMatchesFlags::DRAW_RICH_KEYPOINTS);
		// cv::namedWindow("Display 2 Features");
		// cv::imshow("Display 2 Features", kptImage2);

		cv::Ptr<cv::DescriptorMatcher> matcher = cv::BFMatcher::create();
		// std::vector<cv::DMatch> matches;
		// matcher->match(descriptors1, descriptors2, matches);
		std::vector<std::vector<cv::DMatch>> matches;
		myTimer.reset();
		matcher->knnMatch(descriptors1, descriptors2, matches, 2);
		std::cout << "Time taken to match via BF: " << myTimer.read() << " seconds" << std::endl;
		std::cout << "Found " << matches.size() << " matches via BF" << std::endl;
		std::vector<cv::DMatch> goodMatches;
		std::vector<cv::Point2f> goodPts1, goodPts2;

		for (const auto &match : matches)
		{
			if (match[0].distance < 0.8 * match[1].distance)
			{
				goodMatches.push_back(match[0]);
				goodPts1.push_back(keypoints1[match[0].queryIdx].pt);
				goodPts2.push_back(keypoints2[match[0].trainIdx].pt);
			}
		}
		std::cout << "Kept " << goodMatches.size() << " matches via BF" << std::endl;
		cv::Mat matchImg;
		// cv::drawMatches(image1, keypoints1, image2, keypoints2, goodMatches, matchImg);
		// cv::namedWindow("Matches via BF");
		// cv::imshow("Matches via BF", matchImg);
		std::vector<unsigned char> inliers;
		double ransac_threshold = 3;
		cv::Mat H = cv::findHomography(goodPts2, goodPts1, inliers, cv::RANSAC, ransac_threshold);
		std::cout << H << std::endl;

		/**
		 * Compute the reprojection error. Based on the opencv source code. See findInliers() in ptsetreg.cpp and computeError in fundam.cpp used by findHomography() in filtering by threshold
		 * m2 = H*m1
		 */

		const Point2f *M = goodPts2.data();
		const Point2f *m = goodPts1.data();
		const double *Hpt = H.ptr<double>();
		float Hf[] = {(float)Hpt[0], (float)Hpt[1], (float)Hpt[2], (float)Hpt[3], (float)Hpt[4], (float)Hpt[5], (float)Hpt[6], (float)Hpt[7]};

		std::vector<float> err;

		for (i = 0; i < goodPts1.size(); i++)
		{
			float ww = 1.f / (Hf[6] * M[i].x + Hf[7] * M[i].y + 1.f);
			float dx = (Hf[0] * M[i].x + Hf[1] * M[i].y + Hf[2]) * ww - m[i].x;
			float dy = (Hf[3] * M[i].x + Hf[4] * M[i].y + Hf[5]) * ww - m[i].y;

			if (inliers[i] == 1)
			{
				bf_error_projections_list.push_back(std::sqrt(dx * dx + dy * dy));
			}
		}
	
		int inlierCount = 0;
		int outlierCount = 0;
		for (unsigned char is_inlier : inliers)
		{
			if (is_inlier == 1)
			{
				inlierCount++;
			}
			else
			{
				outlierCount++;
			}
		}
		std::cout << "Inliers: " << inlierCount << std::endl;
		std::cout << "Outliers: " << outlierCount << std::endl;
		std::cout << "Total Matches: " << goodMatches.size() << std::endl;
		// cv::Mat mosaic = image1.clone();
		// Create frame big enough for both
		// cv::Mat originalCorners = (cv::Mat_<double>(3, 4) << 0, image2.size().width, image2.size().width, 0,
		// 						0, 0, image2.size().height, image2.size().height,
		// 						1, 1, 1, 1);

		// cv::Mat transformedCorners = H * originalCorners;

		// double minU = 0;
		// double minV = 0;
		// double maxU = image2.size().width;
		// double maxV = image2.size().height;
		// for (int i = 0; i < transformedCorners.cols; ++i)
		// {
		// 	double currentU = transformedCorners.at<double>(0, i);
		// 	double currentV = transformedCorners.at<double>(1, i);
		// 	currentU = (int)(currentU / transformedCorners.at<double>(2, i) + 0.5);
		// 	currentV = (int)(currentV / transformedCorners.at<double>(2, i) + 0.5);

		// 	if (currentU < minU)
		// 		minU = currentU;
		// 	if (currentV < minV)
		// 		minV = currentV;
		// 	if (currentU > maxU)
		// 		maxU = currentU;
		// 	if (currentV > maxV)
		// 		maxV = currentV;
		// }
		// cv::Mat tOrigin = TMatrix::translationMatrix((-1) * minU, (-1) * minV);
		// H = tOrigin * H;
		// cv::Mat mosaic(cv::Size(maxU - minU, maxV - minV), CV_8UC3, cv::Scalar(0, 0, 0));
		// cv::warpPerspective(image1, mosaic, tOrigin, mosaic.size(),
		// 					cv::INTER_NEAREST, cv::BORDER_TRANSPARENT);
		// cv::warpPerspective(image2, mosaic, H, mosaic.size(),
		// 					cv::INTER_NEAREST, cv::BORDER_TRANSPARENT);

		// cv::namedWindow("Mosacic" + indexStr, WINDOW_NORMAL);
		// cv::Mat downSized(cv::Size(1920,1080), CV_8UC3);
		// cv::resize(mosaic, downSized, downSized.size(), 0,0, cv::INTER_LINEAR);
		// cv::imshow("Mosacic" + indexStr, downSized);
		// /*
		// 	cv::Ptr<cv::DescriptorMatcher> matcherFlann = cv::FlannBasedMatcher::create();
		// 	// std::vector<cv::DMatch> matches;
		// 	// matcher->match(descriptors1, descriptors2, matches);
		// 	std::vector<std::vector<cv::DMatch>> matches2;
		// 	myTimer.reset();
		// 	matcherFlann->knnMatch(descriptors1, descriptors2, matches2, 2);
		// 	std::cout << "Time taken to match via FLANN: " << myTimer.read() << " seconds" << std::endl;
		// 	std::cout << "Found " << matches2.size() << " matches via FLANN" << std::endl;
		// 	std::vector<cv::DMatch> goodMatches2;

		// 	for (const auto &match : matches2)
		// 	{
		// 		if (match[0].distance < 0.8 * match[1].distance)
		// 		{
		// 			goodMatches2.push_back(match[0]);
		// 		}
		// 	}
		// 	std::cout << "Kept " << goodMatches2.size() << " matches via FLANN" << std::endl;
		// 	cv::Mat matchImg2;
		// 	cv::drawMatches(image1, keypoints1, image2, keypoints2, goodMatches2, matchImg2);
		// 	cv::namedWindow("Matches via FLANN");
		// 	cv::imshow("Matches via FLANN", matchImg2);
		// */
		// cv::waitKey();

		// // Write each image's data to a separate CSV file
		// for (size_t i = 0; i < image_distances_list1.size(); ++i) {
		//     ofstream outfile("image_" + to_string(i + 1) + "_distances.csv");
		//     if (outfile.is_open()) {
		//         outfile << "Distances_List1\n";
		//         for (size_t j = 0; j < image_distances_list1[i].size(); ++j) {
		//             outfile << image_distances_list1[i][j] << "\n";
		//         }
		//         outfile.close();
		//         cout << "CSV file for image " << i + 1 << " has been created successfully.\n";
		//     } else {
		//         cout << "Unable to open file for image " << i + 1 << ".\n";
		//     }

		//     outfile.open("image_" + to_string(i + 1) + "_distances_list2.csv");
		//     if (outfile.is_open()) {
		//         outfile << "Distances_List2\n";
		//         for (size_t j = 0; j < image_distances_list2[i].size(); ++j) {
		//             outfile << image_distances_list2[i][j] << "\n";
		//         }
		//         outfile.close();
		//         cout << "CSV file for image " << i + 1 << " (List 2) has been created successfully.\n";
		//     } else {
		//         cout << "Unable to open file for image " << i + 1 << " (List 2).\n";
		//     }
		// }
		// }
	}
	return 0;
}
