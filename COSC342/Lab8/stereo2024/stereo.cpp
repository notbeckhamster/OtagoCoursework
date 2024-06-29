#include <iostream>
#include <fstream>
#include <opencv2/opencv.hpp>
#include <fstream>
void runBlockMatching(cv::Mat imgLeft, cv::Mat imgRight, cv::Size fullSize);

bool isValid(const cv::Vec3d &xyz, float disparity);
// void runSemiFlobalMatching(cv::Mat imgLeft, cv::Mat imgRight);

int main(int argc, char *argv[])
{

	std::string calibrationFile = "../calibration.json";
	std::string imagePrefix = "bell";
	std::string imageNameL = imagePrefix + "_left.jpg";
	std::string imageNameR = imagePrefix + "_right.jpg";
	std::string image1File = "../" + imageNameL;
	std::string image2File = "../" + imageNameR;

	cv::Mat KL, KR, R, t, dL, dR, E, F;
	cv::FileStorage fs(calibrationFile, cv::FileStorage::READ);
	if (!fs.isOpened())
	{
		std::cerr << "Failed to open calibration.json" << std::endl;
		return -1;
	}
	fs["KL"] >> KL;
	fs["KR"] >> KR;
	fs["R"] >> R;
	fs["T"] >> t;
	fs["d1"] >> dL;
	fs["d2"] >> dR;
	fs["E"] >> E;
	fs["F"] >> F;

	// read from json file created from camera calibration
	std::cout << "K1" << std::endl
			  << KL << std::endl;
	std::cout << "K2" << std::endl
			  << KR << std::endl;
	std::cout << "R" << std::endl
			  << R << std::endl;
	std::cout << "d1" << std::endl
			  << dL << std::endl;
	std::cout << "d2" << std::endl
			  << dR << std::endl;
	std::cout << "E" << std::endl
			  << E << std::endl;
	std::cout << "F" << std::endl
			  << F << std::endl;

	cv::Mat imgLeft = cv::imread(image1File);
	cv::Mat imgRight = cv::imread(image2File);
	cv::Size imageSize(1920, 1080);

	cv::Mat RL, RR, PL, PR, Q;
	cv::stereoRectify(KL, dL, KL, dR, imageSize, R, t, RL, RR, PL, PR, Q);

	cv::Mat mapL1, mapL2;
	cv::initUndistortRectifyMap(KL, dL, RL, PL, imageSize, CV_32F, mapL1, mapL2);

	cv::Mat mapR1, mapR2;
	cv::initUndistortRectifyMap(KR, dR, RR, PR, imageSize, CV_32F, mapR1, mapR2);

	cv::Mat retifiedImgLeft, retifiedImgRight;
	cv::remap(imgLeft, retifiedImgLeft, mapL1, mapL2, cv::INTER_LINEAR);
	cv::remap(imgRight, retifiedImgRight, mapR1, mapR2, cv::INTER_LINEAR);

	cv::Mat imgLeftScaled;
	double scalingFactor = 0.25;
	cv::resize(retifiedImgLeft, imgLeftScaled, cv::Size(), scalingFactor, scalingFactor, cv::INTER_AREA);
	cv::Mat imgRightScaled;
	cv::resize(retifiedImgRight, imgRightScaled, cv::Size(), scalingFactor, scalingFactor, cv::INTER_AREA);

	// must be multiple of 16
	int maxDisparity = 64;
	// odd as sqr must be centered
	int blockSize = 21;

	cv::Ptr<cv::StereoBM> blockMatcher = cv::StereoBM::create(maxDisparity, blockSize);

	cv::Mat greyScaleLeft;
	cv::cvtColor(imgLeftScaled, greyScaleLeft, cv::COLOR_BGR2GRAY);

	cv::Mat greyScaleRight;
	cv::cvtColor(imgRightScaled, greyScaleRight, cv::COLOR_BGR2GRAY);
	cv::Mat disparity;

	blockMatcher->compute(greyScaleLeft, greyScaleRight, disparity);

	// disparitySmall is computed on quarter-size images
	// fullSize is a cv::Size of the original image size
	// Scale disparity up to full size
	cv::Mat disparityFull;
	cv::resize(disparity, disparityFull, imageSize, 0, 0, cv::INTER_NEAREST);
	// Convert to floating point fractions for disparity
	cv::Mat disparityFloat(imageSize, CV_32F);
	disparityFull.convertTo(disparityFloat, CV_32F, 1. / 16.0);

	// Compute 3d locations
	cv::Mat points3D(imageSize, CV_32FC3);
	cv::reprojectImageTo3D(disparityFloat, points3D, Q);


	std::vector<std::string> plyPts;

	for (int y = 0; y < points3D.size().height; ++y)
	{
		for (int x = 0; x < points3D.size().width; ++x)
		{
		
			// Write location data for this point
			cv::Vec3f xyz = points3D.at<cv::Vec3f>(y, x);
			float disparity = disparityFull.at<float>(y,x);
			if (isValid(xyz, disparity) == false){
				continue;
			}
			std::stringstream temp;
			temp << xyz[0] << " " << xyz[1] << " " << xyz[2] << " ";
			// Write colour data for this point
			cv::Vec3b rgb = imgLeft.at<cv::Vec3b>(y, x);
			// Note: OpenCV uses BGR colour
			temp << int(rgb[2]) << " " << int(rgb[1])
				 << " " << int(rgb[0]) << "\n";
			plyPts.push_back(temp.str());
		}
	}
	std::ofstream fout("model.ply");
	fout << "ply\n"
		 << "format ascii 1.0\n"
		 << "element vertex " << plyPts.size() << "\n"
		 << "property float x\n"
		 << "property float y\n"
		 << "property float z\n"
		 << "property uchar red\n"
		 << "property uchar green\n"
		 << "property uchar blue\n"
		 << "end_header\n";
	for (std::string eachline : plyPts){
		fout << eachline;
	}
	fout.close();

	return 0;
}

bool isValid(const cv::Vec3d &xyz, float disparity)
{
	// Check for NaNs etc. in Z
	if (isnormal(xyz[2]) == false) {
			return false;
	//Note: 75.0 is 75mm.. so 1metre is 1000.0 
	} else if (xyz[2] > 1000.0*100 || xyz[2] < 0){
		return false;
	} else if (disparity <= 0 ){
		return false;
	}
	// Other checks can go here
	return true;
}