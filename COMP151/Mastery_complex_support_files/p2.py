def print_words_longer_than(file_name, x):
    '''
    >>> print_words_longer_than('words_1.txt', 5)
    Line 1: apricot nectarine rockmelon
    Line 2: parsnip turnip pumpkin carrot
    Line 3: oregano rosemary
    >>> print_words_longer_than('words_2.txt', 7)
    Line 1: elephant
    Line 2: crocodile alligator
    Line 3: blackbird
    '''
    file_obj = open(file_name, "r")
    lines = [each_line.strip() for each_line in file_obj.readlines()]
    curr_line = 1
    result = ""
    for each_line in lines:
        words_longer_than_x_list = "Line " + str(curr_line) + ":"
        curr_line+=1
        words = each_line.split(",")
        for each_word in words:
            if len(each_word) > x:
                words_longer_than_x_list += " " + each_word
        print(words_longer_than_x_list)
    
        
    
    
if __name__ == '__main__':
    import doctest
    doctest.testmod(verbose = True)
