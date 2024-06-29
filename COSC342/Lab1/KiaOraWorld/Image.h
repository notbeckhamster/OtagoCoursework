#pragma once 
#include <string>
class Image{
    private:
        int width;
        int height;
        std::string color;
        
    public: 
        void setWidth(int w);
        void setHeight(int h);
        void setColor(std::string c);
        int getHeight();
};