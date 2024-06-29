def find_last_capitalised(file_name):
    """
    >>> find_last_capitalised('ernest_rutherford.txt')
    'British'
    >>> find_last_capitalised('tycho_brahe.txt')
    'Together,'
    >>> find_last_capitalised('test_5.txt')
    ''
    """
    file_obj = open(file_name)
    lines = file_obj.readlines()
    last_word = ""
    for line in lines:
        for word in line.split():
            if word[0].isupper():
                last_word = word
    return last_word


import doctest

if __name__=="__main__":
    doctest.testmod(verbose = True)
            
            
print(find_last_capitalised('ernest_rutherford.txt'))


