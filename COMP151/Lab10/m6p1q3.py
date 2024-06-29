def print_multiples(x, y, limit):
    '''
    >>> print_multiples(3, 4, 30)
    3 6 9 15 18 21 27 30 
    >>> print_multiples(5, 7, 90)
    5 10 15 20 25 30 40 45 50 55 60 65 75 80 85 90 
    '''
    result = ""
    curr_num = x
    while curr_num <= limit:
        if curr_num % y != 0:
            result += str(curr_num) + " "
        curr_num += x
    print(result)
    

import doctest
if __name__ == "__main__":
    doctest.testmod(verbose=True)
