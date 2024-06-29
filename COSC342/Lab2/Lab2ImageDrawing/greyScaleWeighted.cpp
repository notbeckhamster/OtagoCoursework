#include <opencv2/opencv.hpp>
#include <iostream>

int main(int argc, char *argv[])
{
    // Load an image from file
    cv::Mat image = cv::imread("../test.jpg");
    // Make a copy the same size, but a single channel
    cv::Mat greyscale(image.size(), CV_8UC1);
    // Basic greyscale conversion
    for (int y = 0; y < image.size().height; ++y)
    {
        for (int x = 0; x < image.size().width; ++x)
        {
            cv::Vec3b colour = image.at<cv::Vec3b>(y, x);
            std::cout << static_cast<int>(colour[0]) << std::endl;
            greyscale.at<unsigned char>(y, x) =
                (unsigned char)((colour[0]*0.1 + colour[1]*0.6 + colour[2]*0.3) + 0.5);
        }
    }
    // Save the result
    cv::imwrite("../greyWeighted.png", greyscale);

    return 0;
}