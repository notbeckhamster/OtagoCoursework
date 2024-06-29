def powers(num: int):
    print(f"The first 5 powers of {num} are: {num**1} {num**2} {num**3} {num**4} {num**5}")

def score_summary(name, num_1, num_2, num_3):
    print(f'{name} - Max: {max(num_1,num_2,num_3)}, Min: {min(num_1,num_2,num_3)}, Average: {(num_1+num_2+num_3)/3}')


powers(6)
powers(10)
score_summary("Bruce",9.5, 7, 8.5)
score_summary("Fred", 9, 8, 7)