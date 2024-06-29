#include <opencv2/opencv.hpp>


int main(int argc, char* argv[]) {
	std::string filename = "../test.jpg";

	cv::Mat image(cv::Size(1920,1080), CV_8UC3);
	cv::Scalar green(0,255,0);
	cv::Scalar blue(255,0,0);
	cv::Scalar white(255,255,255);
	//Set bg to green
	image.setTo(green);
	
	cv::circle(image, cv::Point(1920/2, 1080/2), 200, blue, -1);
	cv::circle(image, cv::Point(1920/2, 1080/2), 100, white, 10);
	if (image.empty()) {
		std::cerr << "Could not load image from " << filename << std::endl;
		return -1;
	}

	cv::namedWindow("DisplayName");
	cv::imshow("DisplayName", image);
	cv::waitKey();
	//cv::imwrite("copy.png", image);


	return 0;
}