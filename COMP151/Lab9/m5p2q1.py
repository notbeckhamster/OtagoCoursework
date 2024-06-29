def capitalise_words(file_name, destination):
    """
    >>> capitalise_words("ernest_rutherford.txt", "rutherford_capitalised.txt")
    >>> f1 = open("rutherford_capitalised.txt")
    >>> text1 = f1.read()
    >>> f2 = open("rutherford_answer_capitalised.txt")
    >>> text2 = f2.read()
    >>> text1 == text2
    True
    >>> capitalise_words("marie_curie.txt", "curie_capitalised.txt")
    >>> f1 = open("curie_capitalised.txt")
    >>> text1 = f1.read()
    >>> f2 = open("curie_answer_capitalised.txt")
    >>> text2 = f2.read()
    >>> text1 == text2
    True
    """
    file_obj_from = open(file_name, "r")
    file_obj_to = open(destination, "w")
    
    result = ""
    
    lines = file_obj_from.readlines()
    for line in lines:
        for word in line.split():
            result += word.capitalize() + " "
    file_obj_to.write(result)
    
    file_obj_from.close()
    file_obj_to.close()
    
    
import doctest
if __name__=="__main__":
    doctest.testmod(verbose=True)
            