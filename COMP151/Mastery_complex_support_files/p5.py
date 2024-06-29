def to_oct_nums(num_string):
    '''
    >>> to_oct_nums('100 23 44 32 6 17 ')
    '144 27 54 40 6 21 '
    >>> to_oct_nums('0 1 8 64 512 4096 ')
    '0 1 10 100 1000 10000 '
    '''
    result = ""
    for each_num in num_string.split():
        x = int(each_num)
        octal_digits = ""
        if x==0:
            result += "0 "
            continue
        while x != 0:
            remainder = x % 8
            octal_digits += str(remainder)
            x = x // 8
        result += octal_digits[::-1] + " "
    return result
            
if __name__ == '__main__':
    import doctest
    doctest.testmod(verbose = True)
