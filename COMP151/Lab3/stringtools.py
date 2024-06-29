def remove_letter(letter, string):
    """
    >>> remove_letter("a", "apple")
    'pple'
    >>> remove_letter("a", "banana")
    'bnn'
    >>> remove_letter("z", "banana")
    'banana'
    >>> remove_letter("i", "Mississippi")
    'Msssspp'
    """
    result = ""
    for char in string:
        if (char==letter):
            continue
        result+=char
    return result
def reverse(s):
    """
    >>> reverse("happy")
    'yppah'
    >>> reverse("Python")
    'nohtyP'
    >>> reverse("")
    ''
    >>> reverse("P")
    'P'
    """
    return s[::-1]


if __name__ == "__main__":
    import doctest
    doctest.testmod(verbose=True)
