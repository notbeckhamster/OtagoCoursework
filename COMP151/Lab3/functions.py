def new_line():
    print()
    
def nine_lines():
    three_lines()
    three_lines()
    three_lines()
    
def three_lines():
    new_line()
    new_line()
    new_line()
    

def clear_screen():
    nine_lines()
    nine_lines()
    nine_lines()
    nine_lines()
    three_lines()
    new_line()
    
print("Test")
three_lines()
print("Test")
nine_lines()
print("Test")
clear_screen()
print("Test")