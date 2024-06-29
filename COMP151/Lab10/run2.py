def highest_product(file_name):
    '''
    >>> highest_product('test.txt')
    120
    >>> highest_product('digits_4.txt')
    648
    >>> highest_product('digits_3.txt')
    729
    >>> highest_product('test_2.txt')
    0
    '''
    high_prod = 0
    
    text = open(file_name).read()
    index = 0
    second_last = 0
    first_last = 0
    while index < len(text):
        curr = int(text[index])
        high_prod = max(high_prod, curr*second_last*first_last)
        second_last = first_last
        first_last = curr
        index +=1
    return high_prod
        
    
if __name__ == "__main__":
    import doctest
    doctest.testmod(verbose = True)
