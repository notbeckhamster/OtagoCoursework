#include <iostream>
#include <vector>
int main() {
    std::vector<int> squares(10); // array of 10 integers
    for (size_t i = 0; i < 10; ++i) {
        squares[i] = i*i;
    }
    squares.push_back(10*10);
    squares.push_back(11*11);
    for (const int& square: squares) {
        std::cout << square << " ";
    }
    for (auto iter = squares.begin(); iter != squares.end(); ++iter) {
	    std::cout << *iter << " ";
    }
    std::cout << std::endl;
    return 0;
}