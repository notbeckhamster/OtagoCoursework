#include <iostream>
#include <fstream>
#include <opencv2/opencv.hpp>
#include <opencv2/ximgproc.hpp>

void runBlockMatching(cv::Mat imgLeft, cv::Mat imgRight, int maxDisparity, int blockSize, cv::Mat originalImgLeft);
void runSemiGlobalMatching(cv::Mat scaledImgLeft, cv::Mat scaledImgRight, int maxDisparity, int blockSize, cv::Mat originalImgLeft);

int main(int argc, char* argv[]) {

	std::string pathPrefix = "../StereoPairs/" ;
	std::string calibrationFile = "../calibration.json"; 
	std::string imagePrefix = "desktop";
	std::string leftImagePath = pathPrefix + imagePrefix + "_left.jpg";
	std::string rightImagePath = pathPrefix + imagePrefix + "_right.jpg";

	//must be multiple of 16
	int maxDisparityBM = 192;
	int maxDisparitySGM = 192;
	//odd as square must be centered
	int blockSizeBM =11;
	int blockSizeSGM =7;

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

	//Perform stereo retification to form parralel pair
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
	cv::Mat leftImgScaled, rightImgScaled;
	double scalingFactor = 0.5;
	cv::resize(retifiedLeftImg, leftImgScaled, cv::Size(), scalingFactor, scalingFactor, cv::INTER_LINEAR_EXACT);
	cv::resize(retifiedRightImg, rightImgScaled, cv::Size(), scalingFactor, scalingFactor, cv::INTER_LINEAR_EXACT);

	//Run both methods for matching
	runBlockMatching(leftImgScaled, rightImgScaled, maxDisparityBM, blockSizeBM, leftImg);
	runSemiGlobalMatching(leftImgScaled, rightImgScaled, maxDisparitySGM, blockSizeSGM, leftImg);
	cv::waitKey();

	return 0;

}

void runBlockMatching(cv::Mat scaledLeftImg, cv::Mat scaledRightImg, int maxDisparity, int blockSize, cv::Mat originalLeftImg){

	//Create matching instances
	cv::Ptr<cv::StereoBM> leftMatcher = cv::StereoBM::create(maxDisparity, blockSize);
	cv::Ptr<cv::ximgproc::DisparityWLSFilter> wlsFilter = cv::ximgproc::createDisparityWLSFilter(leftMatcher);
	cv::Ptr<cv::StereoMatcher> rightMatcher = cv::ximgproc::createRightMatcher(leftMatcher);


	//Create grey views
	cv::Mat greyScaleLeftImg;
	cv::cvtColor(scaledLeftImg, greyScaleLeftImg, cv::COLOR_BGR2GRAY);
	cv::Mat greyScaleRightImg;
	cv::cvtColor(scaledRightImg, greyScaleRightImg, cv::COLOR_BGR2GRAY);

	//Perform matching
	cv::Mat leftDisparity;
	leftMatcher->compute(greyScaleLeftImg, greyScaleRightImg, leftDisparity);
	cv::Mat rightDisparity;
	rightMatcher->compute(greyScaleRightImg, greyScaleLeftImg, rightDisparity);
	
	 
	//Perform filtering
	wlsFilter->setLambda(8000.0);
	wlsFilter->setSigmaColor(1.5);
	cv::Mat filteredDisparity;
	wlsFilter->filter(leftDisparity,originalLeftImg,filteredDisparity,rightDisparity);

	//Display raw disparity and filtered disparity
	cv::Mat rawDisImg;
	double rawDisScaling = 1.0;
	cv::ximgproc::getDisparityVis(leftDisparity,rawDisImg, rawDisScaling);
	cv::namedWindow("raw disparity bm", cv::WINDOW_AUTOSIZE);
	cv::imshow("raw disparity bm", rawDisImg);
	cv::Mat filteredDisImg;
	cv::ximgproc::getDisparityVis(filteredDisparity,filteredDisImg,0.5);
	cv::namedWindow("filtered disparity bm", cv::WINDOW_AUTOSIZE);
	cv::imshow("filtered disparity bm", filteredDisImg);

	
	
}


void runSemiGlobalMatching(cv::Mat scaledLeftImg, cv::Mat scaledRightImg, int maxDisparity, int blockSize, cv::Mat originalLeftImg){

	//Create matching instances
	cv::Ptr<cv::StereoSGBM> leftMatcher = cv::StereoSGBM::create(0, maxDisparity, blockSize);
	cv::Ptr<cv::ximgproc::DisparityWLSFilter> wlsFilter = cv::ximgproc::createDisparityWLSFilter(leftMatcher);
	cv::Ptr<cv::StereoMatcher> rightMatcher = cv::ximgproc::createRightMatcher(leftMatcher);

	//Settings for improving smoothness from opencv example
	leftMatcher->setP1(24*blockSize*blockSize);
	leftMatcher->setP2(96*blockSize*blockSize);
	leftMatcher->setPreFilterCap(63);
	//Use 8 directional instead of default 5 directional version
	leftMatcher->setMode(cv::StereoSGBM::MODE_SGBM);

	//Create grey images
	cv::Mat greyScaleLeftImg;
	cv::cvtColor(scaledLeftImg, greyScaleLeftImg, cv::COLOR_BGR2GRAY);
	cv::Mat greyScaleRightImg;
	cv::cvtColor(scaledRightImg, greyScaleRightImg, cv::COLOR_BGR2GRAY);

	//Perform matching
	cv::Mat leftDisparity;
	leftMatcher->compute(greyScaleLeftImg, greyScaleRightImg, leftDisparity);
	cv::Mat rightDisparity;
	rightMatcher->compute(greyScaleRightImg, greyScaleLeftImg, rightDisparity);
	 
	//Perform filtering
	wlsFilter->setLambda(8000.0);
	wlsFilter->setSigmaColor(1.5);
	cv::Mat filteredDisparity;
	wlsFilter->filter(leftDisparity,originalLeftImg,filteredDisparity,rightDisparity);

	//Display raw disparity and filtered disparity
	cv::Mat rawDisImg;
	double rawDisScaling = 1.0;
	cv::ximgproc::getDisparityVis(leftDisparity,rawDisImg, rawDisScaling);
	cv::namedWindow("raw disparity sgm", cv::WINDOW_AUTOSIZE);
	cv::imshow("raw disparity sgm", rawDisImg);
	cv::Mat filteredDisImg;
	cv::ximgproc::getDisparityVis(filteredDisparity,filteredDisImg,0.5);
	cv::namedWindow("filtered disparity sgm", cv::WINDOW_AUTOSIZE);
	cv::imshow("filtered disparity sgm", filteredDisImg);

}