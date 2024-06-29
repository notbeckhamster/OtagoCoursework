#include <opencv2/opencv.hpp>

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
            greyscale.at<unsigned char>(y, x) =
                (unsigned char)((colour[0] + colour[1] + colour[2]) / 3.0 + 0.5);
        }
    }
    // Save the result
    cv::imwrite("../grey.png", greyscale);

    return 0;
}