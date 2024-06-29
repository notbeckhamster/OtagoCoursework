def fibonacci(x):
    '''
    >>> fibonacci(10)
    1 1 2 3 5 8 
    >>> fibonacci(300)
    1 1 2 3 5 8 13 21 34 55 89 144 233 
    >>> fibonacci(377)
    1 1 2 3 5 8 13 21 34 55 89 144 233 
    '''
    second_last = 0
    last = 1
    result = ""
    while last < x:
        result += str(last) + " "
        old_last = last
        last = last + second_last
        second_last = old_last
    print(result)
        
        
if __name__ == "__main__":
    import doctest
    doctest.testmod(verbose=True)
