def speed_reader(file_name, destination):
    """
    >>> speed_reader("tycho_brahe.txt", "brahe_speed_reader.txt")
    >>> f1 = open("brahe_speed_reader.txt")
    >>> text1 = f1.read()
    >>> f2 = open("brahe_answer_speed_reader.txt")
    >>> text2 = f2.read()
    >>> text1 == text2
    True
    >>> speed_reader("frances_oldham_kelsey.txt", "kelsey_speed_reader.txt")
    >>> f1 = open("kelsey_speed_reader.txt")
    >>> text1 = f1.read()
    >>> f2 = open("kelsey_answer_speed_reader.txt")
    >>> text2 = f2.read()
    >>> text1 == text2
    True
    """
    file_obj = open(file_name)
    words = file_obj.read().split()
    result = ""
    for word in words:
        if len(word) < 4:
            result += word + " "
        else:
            result += word[0:3] + "*"*(len(word)-3) + " "
    file_obj_to = open(destination, "w")
    file_obj_to.write(result)
    
import doctest

if __name__=="__main__":
    doctest.testmod(verbose=True)
            