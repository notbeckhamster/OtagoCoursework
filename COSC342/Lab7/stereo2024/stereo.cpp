#include <iostream>
#include <fstream>
#include <opencv2/opencv.hpp>

void runBlockMatching(cv::Mat imgLeft, cv::Mat imgRight);
void runSemiFlobalMatching(cv::Mat imgLeft, cv::Mat imgRight);

int main(int argc, char* argv[]) {

	std::string calibrationFile = "../calibration_archway.json"; 
	std::string imagePrefix = "archway";
	std::string imageNameL = imagePrefix + "_left.jpg";
	std::string imageNameR = imagePrefix + "_right.jpg";
	std::string image1File = "../" + imageNameL;
	std::string image2File = "../" + imageNameR;

	cv::Mat KL, KR, R,t, dL, dR, E, F;
	cv::FileStorage fs(calibrationFile, cv::FileStorage::WRITE);
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

	// read from json file created from camera calibration
	std::cout << "K1" << std::endl << KL << std::endl;
	std::cout << "K2" << std::endl << KR << std::endl;
	std::cout << "R" << std::endl << R << std::endl;
	std::cout << "d1" << std::endl << dL << std::endl;
	std::cout << "d2" << std::endl << dR << std::endl;
	std::cout << "E" << std::endl << E << std::endl;
	std::cout << "F" << std::endl << F << std::endl;


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
	double scalingFactor = 0.3;
	cv::resize(retifiedImgLeft, imgLeftScaled, cv::Size(), scalingFactor, scalingFactor, cv::INTER_AREA);
	cv::Mat imgRightScaled;
	cv::resize(retifiedImgRight, imgRightScaled, cv::Size(), scalingFactor, scalingFactor, cv::INTER_AREA);


	runBlockMatching(imgLeftScaled, imgRightScaled);
	runSemiFlobalMatching(imgLeftScaled, imgRightScaled);
	cv::waitKey();
	
/* 	cv::imshow("LeftBefore", imgLeft);
	cv::imshow("LeftRetified", retifiedImgLeft);
	cv::imshow("RightBefore", imgRight);
	cv::imshow("RightRetified", retifiedImgRight);
	cv::waitKey();

	cv::namedWindow("Wiggle");
	bool isLeft = true;
	while (true){
		if (isLeft){
			cv::imshow("Wiggle", retifiedImgRight);
		}else{
			cv::imshow("Wiggle", retifiedImgLeft);
		}
		int key  = cv::waitKey(50);
		if (cv::getWindowProperty("Wiggle", 0) < 0){
			cv::destroyAllWindows();
			break;
		}
		isLeft = !isLeft;
		
	}
 */

	return 0;

}

void runBlockMatching(cv::Mat imgLeft, cv::Mat imgRight){
		//must be multiple of 16
	int maxDisparity = 64;
	//odd as sqr must be centered
	int blockSize = 21;

	cv::Ptr<cv::StereoBM> blockMatcher = cv::StereoBM::create(maxDisparity, blockSize);

	cv::Mat greyScaleLeft;
	cv::cvtColor(imgLeft, greyScaleLeft, cv::COLOR_BGR2GRAY);

	cv::Mat greyScaleRight;
	cv::cvtColor(imgRight, greyScaleRight, cv::COLOR_BGR2GRAY);
	cv::Mat disparity;

	blockMatcher->compute(greyScaleLeft, greyScaleRight, disparity);

	cv::Mat display;
	disparity.convertTo(display, CV_8UC1, 255.0 / (16*maxDisparity));

	cv::imshow("DisparityBM", display);
	
}


void runSemiFlobalMatching(cv::Mat imgLeft, cv::Mat imgRight){
	//must be multiple of 16
	int maxDisparity = 64;
	//odd as sqr must be centered
	int blockSize = 11;

	cv::Ptr<cv::StereoSGBM> sgMatcher = cv::StereoSGBM::create(0, maxDisparity, blockSize);

	cv::Mat greyScaleLeft;
	cv::cvtColor(imgLeft, greyScaleLeft, cv::COLOR_BGR2GRAY);

	cv::Mat greyScaleRight;
	cv::cvtColor(imgRight, greyScaleRight, cv::COLOR_BGR2GRAY);
	cv::Mat disparity;

	sgMatcher->compute(greyScaleLeft, greyScaleRight, disparity);

	cv::Mat display;
	disparity.convertTo(display, CV_8UC1, 255.0 / (16*maxDisparity));

	cv::imshow("DisparitySGM", display);

}