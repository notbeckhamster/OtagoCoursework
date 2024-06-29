def positions_of_words_longer_than(filename, cut_off):
    '''
    >>> positions_of_words_longer_than('richard_seddon.txt', 12)
    '46 197 565 692 825 840 '
    >>> positions_of_words_longer_than('helen_clark.txt', 12)
    '32 47 155 235 315 412 419 424 524 659 791 796 819 837 884 885 974 1137 '
    >>> positions_of_words_longer_than('robert_muldoon.txt', 12)
    '85 175 190 212 244 284 520 716 858 909 910 989 1003 '
    '''
    index = 0
    words = open(filename).read().split()
    result = ""
    while index < len(words):
        word = words[index]
        if len(word) > cut_off:
            result += str(index) + " "
        
        index += 1
    return result

if __name__ == "__main__":
    import doctest
    doctest.testmod(verbose = True)
