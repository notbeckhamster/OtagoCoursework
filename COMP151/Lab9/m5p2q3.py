def save_word_lengths(file_name, destination):
    """
    >>> save_word_lengths("tycho_brahe.txt", "brahe_word_lengths.txt")
    >>> f1 = open("brahe_word_lengths.txt")
    >>> text1 = f1.read()
    >>> f2 = open("brahe_answer_word_lengths.txt")
    >>> text2 = f2.read()
    >>> text1 == text2
    True
    >>> save_word_lengths("frances_oldham_kelsey.txt", "kelsey_word_lengths.txt")
    >>> f1 = open("kelsey_word_lengths.txt")
    >>> text1 = f1.read()
    >>> f2 = open("kelsey_answer_word_lengths.txt")
    >>> text2 = f2.read()
    >>> text1 == text2
    True
    """
    file_obj = open(file_name)
    words = file_obj.read().split()
    result = ""
    for word in words:
        result += str(len(word)) + " "
    file_obj_to = open(destination, "w")
    file_obj_to.write(result)
    
import doctest

if __name__=="__main__":
    doctest.testmod(verbose = True)
    
        