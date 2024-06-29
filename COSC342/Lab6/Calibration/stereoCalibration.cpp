#include <opencv2/opencv.hpp>
#include <sstream>
#include <string>
#include <iomanip> // For std::setfill and std::setw
void showChessboardCorners();
void calibrateCamera(std::string directory, std::string filenameEnding, std::vector<std::vector<cv::Point3f>>& objectPoints, std::vector<std::vector<cv::Point2f>>& imagePoints, cv::Mat& K, cv::Mat& distCoeffs);

int main(int argc, char *argv[])
{

    cv::namedWindow("Left");
    cv::namedWindow("Right");

    // showChessboardCorners();
    std::vector<std::vector<cv::Point3f>> objectPoints;
    cv::Size patternSize(10, 5);
    cv::Size imageSize(1920, 1080); // in pixels

    for (int i = 457; i < 476; ++i)
    {
        std::vector<cv::Point3f> checkerboardPattern;
        for (int y = 0; y < patternSize.height; ++y)
        {
            for (int x = 0; x < patternSize.width; ++x)
            {
                // Add (x,y,0) to checkerboardPattern
                checkerboardPattern.push_back(cv::Point3f(x * 47, y * 47, 0));
            }
        }
        objectPoints.push_back(checkerboardPattern);
    }

    std::vector<std::vector<cv::Point2f>> imagePointsL;
    cv::Mat KL;
    cv::Mat distCoeffsL;
    std::vector<std::vector<cv::Point2f>> imagePointsR;
    cv::Mat KR;
    cv::Mat distCoeffsR;
    calibrateCamera("../data/CalibrationLeft/DSCF", "_L.JPG", objectPoints, imagePointsL, KL, distCoeffsL);
    calibrateCamera("../data/CalibrationRight/DSCF", "_R.JPG", objectPoints, imagePointsR, KR, distCoeffsR);

    // Output Params
    cv::Mat R;
    cv::Mat T;
    cv::Mat E;
    cv::Mat F;
    double err = cv::stereoCalibrate(objectPoints, imagePointsL, imagePointsR, KL, distCoeffsL, KR, distCoeffsR, imageSize, R, T, E, F);
    std::cout << "Reprojection error from stereoCalibrate " << err << std::endl;
    std::cout << R << std::endl;
    std::cout << T << std::endl;

    cv::FileStorage file("calibration.json", cv::FileStorage::WRITE);
    file << "KL" << KL;
    file << "KR" << KR;
    file << "R" << R;
    file << "T" << T;
    file << "d1" << distCoeffsL;
    file << "d2" << distCoeffsR;
    file << "E" << E;
    file << "F" << F;
    return 0;
}

void showChessboardCorners()
{
    for (int i = 457; i < 476; ++i)
    {
        std::ostringstream pathStreamL;
        pathStreamL << "../data/CalibrationLeft/DSCF"
                    << std::setfill('0') << std::setw(4) << i
                    << "_L.JPG";
        std::string fnameL = pathStreamL.str(); // Converts the stream to a string

        cv::Mat imageL = cv::imread(fnameL);
        cv::Size patternSizeL(10, 5);
        std::vector<cv::Point2f> cornersL;
        bool patternFoundL = cv::findChessboardCorners(imageL, patternSizeL, cornersL);
        cv::drawChessboardCorners(imageL, patternSizeL, cornersL, patternFoundL);
        cv::imshow("Left", imageL);
        cv::waitKey(10);

        std::ostringstream pathStreamR;
        pathStreamR << "../data/CalibrationRight/DSCF"
                    << std::setfill('0') << std::setw(4) << i
                    << "_R.JPG";
        std::string fnameR = pathStreamR.str(); // Converts the stream to a string

        cv::Mat imageR = cv::imread(fnameR);
        cv::Size patternSizeR(10, 5);
        std::vector<cv::Point2f> cornersR;
        bool patternFoundR = cv::findChessboardCorners(imageR, patternSizeR, cornersR);
        cv::drawChessboardCorners(imageR, patternSizeR, cornersR, patternFoundR);
        cv::imshow("Right", imageR);
        cv::waitKey(100);
    }
}

void calibrateCamera(std::string directory, std::string filenameEnding, std::vector<std::vector<cv::Point3f>>& objectPoints, std::vector<std::vector<cv::Point2f>>& imagePoints, cv::Mat& K, cv::Mat& distCoeffs)
{

    cv::Size imageSize(1920, 1080); // in pixels
    cv::Size patternSize(10, 5);    // in squares
    std::vector<cv::Mat> rvecs, tvecs;

    // Fill checkerboardPattern with the 3D corner locations
    for (int i = 457; i < 476; ++i)
    {
        std::ostringstream pathStream;
        pathStream << directory
                   << std::setfill('0') << std::setw(4) << i
                   << filenameEnding;
        std::string fname = pathStream.str(); // Converts the stream to a string

        cv::Mat image = cv::imread(fname);
        std::vector<cv::Point2f> corners;
        cv::findChessboardCorners(image, patternSize, corners);
        imagePoints.push_back(corners);
    }
    double err = cv::calibrateCamera(objectPoints, imagePoints, imageSize, K, distCoeffs, rvecs, tvecs);
    std::cout << K << std::endl;
    std::cout << err << std::endl;
}