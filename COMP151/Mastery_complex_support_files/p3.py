def create_row(num_seats, row_letter):
    seat_idx = 1
    row_str = ""
    for col_idx in range(1,num_seats+1):
            if seat_idx == 13:
                seat_idx += 1
            row_str += row_letter + str(seat_idx) + " "
            seat_idx +=1
            
    return row_str



def seating_plan_opt1(num_rows, num_seats):
    '''
    >>> seating_plan_opt1(4, 6)== open('school_play_simple.txt').read()
    True
    >>> seating_plan_opt1(15,15)== open('school_play_opt1.txt').read()
    True
    >>> seating_plan_opt1(15, 15)== open('opera_opt1.txt').read()
    True
    '''
    result = ""
    row_idx = 0
    char_idx = ord("A")
    for row_idx in range(num_rows):
        
        if char_idx in (ord("O"), ord("I")):
            char_idx+=1
        row_letter = chr(char_idx)
        char_idx+=1
        
        result += create_row(num_seats, row_letter) + "\n"
    return result
if __name__ == '__main__':
    import doctest
    doctest.testmod(verbose = True)
print(seating_plan_opt1(15,15))
