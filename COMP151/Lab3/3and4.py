import numpy as np

def sum_squared_error(x1, x2):
    """
    >>> sum_squared_error(np.array([1.0,2.0]), np.array([3.0,4.0]))
    8.0
    >>> sum_squared_error(np.array([1.0,2.0,3.0]), np.array([3.0,4.0,5.0]))
    12.0
    >>> sum_squared_error(np.array([1.0,2.0,3.0,4.0]),np.array([1.0,2.0,3.0,4.0]))
    0.0
    """
    return np.sum((x1-x2)**2)


if __name__=="__main__":
    import doctest
    doctest.testmod(verbose=True)
    