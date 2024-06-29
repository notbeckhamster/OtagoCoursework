#include <opencv2/opencv.hpp>
#include <opencv2/features2d.hpp>
#include "Timer.h"

using namespace cv;
using namespace std;
int main(int argc, char* argv[]) {

	Timer myTimer;
	cv::Mat image1 = cv::imread("../image1.jpg");
	cv::Mat image2 = cv::imread("../image2.jpg");
	double elapsedTime=myTimer.read();
	std::cout << "Loading images from drive" << elapsedTime << " seconds" << std::endl;

	if (image1.empty()) {
		std::cerr << "Could not load image from image1.jpg" << std::endl;
		return -1;
	}
	if (image2.empty()) {
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

	cv::namedWindow("Display 1");
	cv::imshow("Display 1", image1);
	cv::namedWindow("Display 2");
	cv::imshow("Display 2", image2);

	cv::Mat kptImage1;
	cv::drawKeypoints(image1, keypoints1, kptImage1, cv::Scalar(0,255,0),
	cv::DrawMatchesFlags::DRAW_RICH_KEYPOINTS);
	cv::namedWindow("Display 1 Features");
	cv::imshow("Display 1 Features", kptImage1);

	cv::Mat kptImage2;
	cv::drawKeypoints(image2, keypoints2, kptImage2, cv::Scalar(0,255,0),
	cv::DrawMatchesFlags::DRAW_RICH_KEYPOINTS);
	cv::namedWindow("Display 2 Features");
	cv::imshow("Display 2 Features", kptImage2);


	cv::Ptr<cv::DescriptorMatcher> matcher = cv::BFMatcher::create();
	// std::vector<cv::DMatch> matches;
	// matcher->match(descriptors1, descriptors2, matches);
	std::vector<std::vector<cv::DMatch>> matches;
	myTimer.reset();
	matcher->knnMatch(descriptors1, descriptors2, matches, 2);
	std::cout << "Time taken to match via BF: " << myTimer.read() << " seconds" << std::endl;
	std::cout << "Found " << matches.size() << " matches via BF" << std::endl;
	std::vector<cv::DMatch> goodMatches;
	for (const auto& match : matches) {
		if (match[0].distance < 0.8 * match[1].distance) {
		goodMatches.push_back(match[0]);
		}
	}
	std::cout << "Kept " << goodMatches.size() << " matches via BF" << std::endl;
	cv::Mat matchImg;
	cv::drawMatches(image1, keypoints1, image2, keypoints2, goodMatches, matchImg);
	cv::namedWindow("Matches via BF");
	cv::imshow("Matches via BF", matchImg);

	cv::Ptr<cv::DescriptorMatcher> matcherFlann = cv::FlannBasedMatcher::create();
	// std::vector<cv::DMatch> matches;
	// matcher->match(descriptors1, descriptors2, matches);
	std::vector<std::vector<cv::DMatch>> matches2;
	myTimer.reset();
	matcherFlann->knnMatch(descriptors1, descriptors2, matches2, 2);
	std::cout << "Time taken to match via FLANN: " << myTimer.read() << " seconds" << std::endl;
	std::cout << "Found " << matches2.size() << " matches via FLANN" << std::endl;
	std::vector<cv::DMatch> goodMatches2;
	for (const auto& match : matches2) {
		if (match[0].distance < 0.8 * match[1].distance) {
		goodMatches2.push_back(match[0]);
		}
	}
	std::cout << "Kept " << goodMatches2.size() << " matches via FLANN" << std::endl;
	cv::Mat matchImg2;
	cv::drawMatches(image1, keypoints1, image2, keypoints2, goodMatches2, matchImg2);
	cv::namedWindow("Matches via FLANN");
	cv::imshow("Matches via FLANN", matchImg2);


	cv::waitKey();

	return 0;
}