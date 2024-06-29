#include <opencv2/opencv.hpp>
#include "TMatrix.h"

int main(int argc, char *argv[])
{
    cv::Mat original = cv::imread("../test.jpg");
    
    //cv::Mat T = cv::Mat::eye(3, 3, CV_64F);
    //cv::Mat T = TMatrix::translationMatrix(50,-50);
    //cv::Mat T = TMatrix::scaleMatrix(2);
    //cv::Mat T = TMatrix::rotationMatrix(22.5);
    //rotate about centre
    cv::Mat T_1 = TMatrix::translationMatrix(-original.size().width/2, -original.size().height/2);
    cv::Mat T_2 = T_1.inv();
    cv::Mat R = TMatrix::rotationMatrix(45);
    cv::Mat T = T_2*R*T_1;
    cv::Mat target(original.size(), CV_8UC3);
    cv::warpPerspective(original, target, T, target.size(), cv::INTER_LINEAR, cv::BORDER_REPLICATE, cv::BORDER_CONSTANT);
    
    // Save the result

	cv::namedWindow("Display");
	cv::imshow("Display", target);
	cv::waitKey();

    return 0;
}