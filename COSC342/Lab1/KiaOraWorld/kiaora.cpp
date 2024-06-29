#include <iostream>
#include "Image.h"
int main(int argc, char *argv[]) {
  std::cout << "Kia ora from a C++ developer" << std::endl;
  Image image;
  image.setHeight(23);
  std::cout << image.getHeight() << std::endl;
  return 0;
}
