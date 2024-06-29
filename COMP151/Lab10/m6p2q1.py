def first_x_vowels(file_name, x):
    """
    >>> first_x_vowels('test.txt', 10)
    'aeiou'
    >>> first_x_vowels('helen_clark.txt', 50)
    'eeEiaeaOIoeuaiaeeaaoiiiaoeeaeieiieoeeaaooaaeAiiaoo'
    >>> first_x_vowels('peter_fraser.txt', 15)
    'eeaeAuueeeaaeea'
    """
    text = open(file_name).read()
    res = ""
    for char in text:
        if char in "aeiouAEIOU" and len(res) < x:
            res += char
    return res
        
if __name__ == "__main__":
    import doctest
    doctest.testmod(verbose = True)
