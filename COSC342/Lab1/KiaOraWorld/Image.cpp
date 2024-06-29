#include "Image.h"
void Image::setWidth(int w){
    width = w;
}

void Image::setHeight(int h)
{
    height = h;
}

void Image::setColor(std::string c)
{
    color = c;
}

int Image::getHeight(){
    return height;
}
