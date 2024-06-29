#pragma once

class TMatrix{

public:
	static cv::Mat translationMatrix(double dx, double dy);
	static cv::Mat scaleMatrix(double s);
	static cv::Mat rotationMatrix(double angle);
private:

};