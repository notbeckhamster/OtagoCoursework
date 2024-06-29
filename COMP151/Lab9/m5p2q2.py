def strip_punctuation(file_name, destination):
    """
    >>> strip_punctuation("tycho_brahe.txt", "brahe_no_punc.txt")
    >>> f1 = open("brahe_no_punc.txt")
    >>> text1 = f1.read()
    >>> f2 = open("brahe_answer_no_punc.txt")
    >>> text2 = f2.read()
    >>> text1 == text2
    True
    >>> strip_punctuation("frances_oldham_kelsey.txt", "kelsey_no_punc.txt")
    >>> f1 = open("kelsey_no_punc.txt")
    >>> text1 = f1.read()
    >>> f2 = open("kelsey_answer_no_punc.txt")
    >>> text2 = f2.read()
    >>> text1 == text2
    True
    """
    file_obj_from = open(file_name,"r")
    file_obj_to = open(destination, "w")
    result  = ""
    lines = file_obj_from.readlines()

    for line in lines:
        last_char = "a"
        for char in line:
            if char.isalnum():
                result += char
            elif char.isspace() and last_char.isspace() == False:
                result += " "
            last_char = char
    
    file_obj_to.write(result)
    file_obj_from.close()
    file_obj_to.close()
    
import doctest

if __name__=="__main__":
    doctest.testmod(verbose = True)