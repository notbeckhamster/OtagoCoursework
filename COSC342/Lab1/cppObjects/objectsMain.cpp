#include <iostream>
#include <memory>
#include <vector>

#include "Object.h"
#include "Derived.h"

template <typename T>
const T& minimum(const T& a, const T& b) {
return a < b ? a : b;
}

int main(int argc, char *argv[]) {
    std::cout << minimum(3,5) << std::endl;
    return 0;
}

