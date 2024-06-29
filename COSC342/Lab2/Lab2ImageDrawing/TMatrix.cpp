#include <opencv2/opencv.hpp>
#include "TMatrix.h"
cv::Mat TMatrix::translationMatrix(double dx, double dy) {
    cv::Mat T = cv::Mat::eye(3, 3, CV_64F);
    T.at<double>(0, 2) = dx;
    T.at<double>(1, 2) = dy;
    return T;
}

cv::Mat TMatrix::scaleMatrix(double s){
    cv::Mat T = cv::Mat::eye(3, 3, CV_64F);
    T.at<double>(0, 0) = s;
    T.at<double>(1, 1) = s;
    return T;
}

cv::Mat TMatrix::rotationMatrix(double angle){
    cv::Mat T = cv::Mat::eye(3, 3, CV_64F);
    const double pi = 3.14159265358979311599796346854;
    double rads = (angle*pi)/180.0;
    T.at<double>(0, 0) = cos(rads);
    T.at<double>(0, 1) = (-1)*sin(rads);
    T.at<double>(1, 0) = sin(rads);
    T.at<double>(1, 1) = cos(rads);
    return T;
}