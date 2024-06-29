def letter_count(file_name):
    """
    >>> letter_count("ernest_rutherford.txt")
    3245
    >>> letter_count("frances_oldham_kelsey.txt")
    4235
    """
    file_obj = open(file_name, "r")
    lines = file_obj.readlines()
    count = 0
    for line in lines:
        for char in line:
            if char.isalpha():
                count+=1
    return count

import doctest
if __name__=="__main__":
    doctest.testmod(verbose=True)
        
    

