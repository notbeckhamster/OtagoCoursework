def match_brackets(s):
    '''
    >>> match_brackets('(7 - 4) * (3 + 2)')
    True
    >>> match_brackets('((2 + 5) / (13 +12)')
    False
    >>> match_brackets(')14 + 12) % 3')
    False
    >>> match_brackets(')(')
    False
    >>> match_brackets('())(')
    False
    >>> match_brackets('())(()()')
    False
    >>> match_brackets('())()(')
    False
    '''
    count=0
    for char in s:
        if char == "(":
            if count < 0:
                return False
            count+=1
        elif char==")":
            if count <= 0:
                return False
            count-=1
    return count==0

def get_ords(s):
    '''
    >>> get_ords('abc')
    '97 98 99 '
    >>> get_ords('a b c')
    '97 32 98 32 99 '
    >>> get_ords('a1 b2 c3')
    '97 49 32 98 50 32 99 51 '
    >>> get_ords('[(!)]')
    '91 40 33 41 93 '
    '''
    result = ""
    for char in s:
        result += str(ord(char)) + " "
    return result

if __name__== "__main__":
    import doctest
    doctest.testmod(verbose=True)
