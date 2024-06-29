def sum_of_evens(line):
    sum_of_line = 0
    for each_num_str in line:
        num = int(each_num_str)
        if (num % 2 == 0):
            sum_of_line += num
    return str(sum_of_line)


def sum_evens(file_name):
    '''
    >>> sum_evens('numbers_1.txt')
    '164 12 0'
    >>> sum_evens('numbers_2.txt')
    '7654 5360 1569200 46574 7635754 0 9859896'
    '''
    file_obj = open(file_name, "r")
    lines = file_obj.readlines()
    lines_as_list = [ each_line.strip().split(",") for each_line in lines]
    list_of_sums = [sum_of_evens(each_line) for each_line in lines_as_list]
    return " ".join(list_of_sums)

if __name__ == '__main__':
    import doctest
    doctest.testmod(verbose = True)
