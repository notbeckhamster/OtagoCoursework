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
    cv::Mat invT = T.inv();
    std::vector<cv::Mat> arrayOfCorners[4];
    cv::Mat originalCorners = (cv::Mat_<double>(3, 4) << 0, original.size().width, original.size().width, 0,
                                                          0, 0, original.size().height, original.size().height,
                                                          1, 1, 1, 1);


    cv::Mat transformedCorners = T*originalCorners;

    double minU = 0;
    double minV = 0;
    double maxU = original.size().width;
    double maxV = original.size().height;
    for (int i = 0; i < transformedCorners.cols; ++i) {
        double currentU = transformedCorners.at<double>(0, i);
        double currentV = transformedCorners.at<double>(1, i);

        if (currentU < minU) minU = currentU;
        if (currentV < minV) minV = currentV;
        if (currentU > maxU) maxU = currentU;
        if (currentV > maxV) maxV = currentV;
    }
    cv::Mat tOrigin = TMatrix::translationMatrix((-1)*minU, (-1)*minV);
    cv::Mat transInvT = (tOrigin * T).inv();
    cv::Mat target(cv::Size(maxU-minU, maxV-minV), CV_8UC3);
    for (int v = 0; v < target.size().height; ++v)
    {
        for (int u = 0; u < target.size().width; ++u)
        {
            //non inv
            // The column vector [u, v, 1]
            // cv::Mat s(3, 1, CV_64F);
            // s.at<double>(0, 0) = u;
            // s.at<double>(1, 0) = v;
            // s.at<double>(2, 0) = 1;
            // cv::Mat t = T * s;
            // int u_ = (int)(t.at<double>(0) / t.at<double>(2) + 0.5);
            // int v_ = (int)(t.at<double>(1) / t.at<double>(2) + 0.5);
            // if (v_ > target.size().height || v_ < 0 || u_ > target.size().width || u_ < 0) continue;
            // target.at<cv::Vec3b>(v_, u_) = original.at<cv::Vec3b>(v, u);
            //inv method
            cv::Mat s(3, 1, CV_64F);
            s.at<double>(0, 0) = u;
            s.at<double>(1, 0) = v;
            s.at<double>(2, 0) = 1;
            cv::Mat t = transInvT * s;
            int u_ = (int)(t.at<double>(0) / t.at<double>(2) + 0.5);
            int v_ = (int)(t.at<double>(1) / t.at<double>(2) + 0.5);
            if (v_ > original.size().height || v_ < 0 || u_ > original.size().width || u_ < 0) continue;
            target.at<cv::Vec3b>(v, u) = original.at<cv::Vec3b>(v_, u_);
        }
    }
    
    // Save the result

	cv::namedWindow("Display");
	cv::imshow("Display", target);
	cv::waitKey();

    return 0;
}