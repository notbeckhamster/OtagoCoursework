def convert_to_int(str_rep):
    '''
    >>> convert_to_int('0b11')
    3
    >>> convert_to_int('0xfae')
    4014
    >>> convert_to_int('0o545')
    357
    '''
    if (str_rep[0:2] == '0b'):
        return int(str_rep, 2)
    elif (str_rep[0:2] == '0x'):
        return int(str_rep, 16)
    elif (str_rep[0:2] == "0o"):
        return int(str_rep, 8)
if __name__ == "__main__":
    import doctest
    doctest.testmod(verbose=True)
    
