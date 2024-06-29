def highest_product(file_name):
    '''
    >>> highest_product('test.txt')
    120
    >>> highest_product('digits_4.txt')
    648
    >>> highest_product('digits_3.txt')
    729
    >>> highest_product('test_2.txt')
    0
    '''
    nums = open(file_name).read()
    if len(nums) < 3:
        return 0

    third_last = int(nums[0])
    second_last = int(nums[1])
    last = int(nums[2])
    max_prod = last*second_last*third_last
    idx = 3
    while idx < len(nums):
        third_last = second_last
        second_last = last
        last = int(nums[idx])
        idx+=1
        max_prod = max(last*second_last*third_last, max_prod)
    return max_prod

if __name__ == "__main__":
    import doctest
    doctest.testmod(verbose = True)
