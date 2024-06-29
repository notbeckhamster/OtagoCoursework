#include <opencv2/opencv.hpp>


int main(int argc, char* argv[]) {
	std::string filename = "../test.jpg";

	cv::Mat image = cv::imread(filename);
	
	if (image.empty()) {
		std::cerr << "Could not load image from " << filename << std::endl;
		return -1;
	}

	cv::namedWindow("Display");
	cv::imshow("Display", image);
	cv::waitKey();
	//cv::imwrite("copy.png", image);


	return 0;
}