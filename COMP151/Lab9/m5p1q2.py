def count_possible_sentences(file_name):
    """
    >>> count_possible_sentences("frances_oldham_kelsey.txt")
    45
    >>> count_possible_sentences("ernest_rutherford.txt")
    32
    >>> count_possible_sentences("marie_curie.txt")
    24
    """
    file_obj = open(file_name)
    lines = file_obj.readlines()
    count = 0
    for line in lines:
        words = line.split()
        for word in words:
            if word[-1] in ".?!":
                count += 1
    return count

import doctest

if __name__=="__main__":
    doctest.testmod(verbose=True)
    
    
print(count_possible_sentences("frances_oldham_kelsey.txt"))
