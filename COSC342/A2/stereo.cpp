#include <iostream>
#include <fstream>
#include <opencv2/opencv.hpp>


void runBlockMatching(cv::Mat leftImg, cv::Mat rightImg, int maxDisparity, int blockSize);
void runSemiGlobalMatching(cv::Mat leftImg, cv::Mat rightImg, int maxDisparity, int blockSize);

int main(int argc, char* argv[]) {

	std::string pathPrefix = "../StereoPairs/";
	std::string calibrationFile = "../calibration.json"; 
	std::string imagePrefix = "desktop";
	std::string leftImagePath = pathPrefix + imagePrefix + "_left.jpg";
	std::string rightImagePath = pathPrefix + imagePrefix + "_right.jpg";

	//must be multiple of 16
	int maxDisparityBM = 128;
	int maxDisparitySGM = 128;
	//odd as square must be centered
	int blockSizeBM = 21;
	int blockSizeSGM = 21;





	//Load calibration params
	cv::Mat KL, KR, R,t, dL, dR, E, F;
	cv::FileStorage fs(calibrationFile, cv::FileStorage::READ);
	if (!fs.isOpened()){
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

	//Load left and right images
	cv::Mat leftImg = cv::imread(leftImagePath);
	cv::Mat rightImg = cv::imread(rightImagePath);
	cv::Size imageSize(1920, 1080);

	//Perform stereo retification to form parallel
	cv::Mat RL, RR, PL, PR, Q;
	cv::stereoRectify(KL, dL, KL, dR, imageSize, R, t, RL, RR, PL, PR, Q);
	cv::Mat mapL1, mapL2;
	cv::initUndistortRectifyMap(KL, dL, RL, PL, imageSize, CV_32F, mapL1, mapL2);
	cv::Mat mapR1, mapR2;
	cv::initUndistortRectifyMap(KR, dR, RR, PR, imageSize, CV_32F, mapR1, mapR2);
	cv::Mat retifiedLeftImg, retifiedRightImg;
	cv::remap(leftImg, retifiedLeftImg, mapL1, mapL2, cv::INTER_LINEAR);
	cv::remap(rightImg, retifiedRightImg, mapR1, mapR2, cv::INTER_LINEAR);

	//Resize images for matching
	cv::Mat leftImgScaled;
	double scalingFactor = 0.3;
	cv::resize(retifiedLeftImg, leftImgScaled, cv::Size(), scalingFactor, scalingFactor, cv::INTER_AREA);
	cv::Mat rightImgScaled;
	cv::resize(retifiedRightImg, rightImgScaled, cv::Size(), scalingFactor, scalingFactor, cv::INTER_AREA);

	//Run both methods for matching
	runBlockMatching(leftImgScaled, rightImgScaled, maxDisparityBM, blockSizeBM);
	runSemiGlobalMatching(leftImgScaled, rightImgScaled, maxDisparitySGM, blockSizeSGM);
	cv::waitKey();

	return 0;

}

void runBlockMatching(cv::Mat leftImg, cv::Mat rightImg, int maxDisparity, int blockSize){

	
	cv::Ptr<cv::StereoBM> blockMatcher = cv::StereoBM::create(maxDisparity, blockSize);

	//Create grey images
	cv::Mat greyScaleLeftImg;
	cv::cvtColor(leftImg, greyScaleLeftImg, cv::COLOR_BGR2GRAY);
	cv::Mat greyScaleRightImg;
	cv::cvtColor(rightImg, greyScaleRightImg, cv::COLOR_BGR2GRAY);

	//Perform matching
	cv::Mat disparity;
	blockMatcher->compute(greyScaleLeftImg, greyScaleRightImg, disparity);

	//Normalize values + display
	cv::Mat display;
	cv::normalize(disparity, display, 0, 255, cv::NORM_MINMAX, CV_8UC1);
	cv::imshow("DisparityBM", display);
	
}


void runSemiGlobalMatching(cv::Mat leftImg, cv::Mat rightImg, int maxDisparity, int blockSize){


	cv::Ptr<cv::StereoSGBM> sgMatcher = cv::StereoSGBM::create(0, maxDisparity, blockSize);

	//Create grey images
	cv::Mat greyScaleLeftImg;
	cv::cvtColor(leftImg, greyScaleLeftImg, cv::COLOR_BGR2GRAY);
	cv::Mat greyScaleRightImg;
	cv::cvtColor(rightImg, greyScaleRightImg, cv::COLOR_BGR2GRAY);

	//Perform matching
	cv::Mat disparity;
	sgMatcher->compute(greyScaleLeftImg, greyScaleRightImg, disparity);

	//Normalize values + display
	cv::Mat display;
	cv::normalize(disparity, display, 0, 255, cv::NORM_MINMAX, CV_8UC1);;
	cv::imshow("DisparitySGM", display);

}