#include <opencv2/opencv.hpp>

int main(int argc, char *argv[])
{
    cv::Mat M(3, 3, CV_64F);
        // iterate over the matrix setting diagonals to 1 and others to 0;
        for (int r = 0; r < M.rows; ++r)
    {
        for (int c = 0; c < M.cols; ++c)
        {
            if (r == c)
            {
                M.at<double>(r, c) = 1;
            }
            else
            {
                M.at<double>(r, c) = 0;
            }
        }
    }
    // Print out the matrix
    std::cout << M << std::endl;
    return 0;
}