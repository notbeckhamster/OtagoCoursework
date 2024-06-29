def find_slice(s, ch):
    '''
    >>> find_slice("abcdefghijk", "f")
    'abcdef'
    >>> find_slice("aaabbbccc", "b")
    'aaab'
    >>> find_slice("aaabbbccc", "d")
    ''
    '''
    return s[:s.find(ch)+1]

def first_half(s):
    '''
    >>> first_half("abcdef")
    'abc'
    >>> first_half("abcdefg")
    'abc'
    >>> first_half("a")
    ''
    >>> first_half("good dogs")
    'good'
    '''
    return s[:int(len(s)/2)]
if __name__ == "__main__":
    import doctest
    doctest.testmod(verbose=True)
