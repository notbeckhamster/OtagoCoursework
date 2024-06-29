def get_word_longer_than(length):
    is_longer = False
    word = ""
    while is_longer == False:
        word = input(f'Please enter a word longer than {length}:')
        if word.isalpha() and len(word)>length:
            is_longer=True
        else:
            print("Try Again")
    return word