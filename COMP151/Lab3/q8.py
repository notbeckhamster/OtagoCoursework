def signature(from_name):
    print("Yours sincerely")
    print(from_name)
    
def reject(dear_name):
    print("Dear " + dear_name)
    print("I am sorry to inform you that you do not have the job")
    signature("Humphrey Hopalong")
    
def accept(dear_name):
    print("Dear " + dear_name)
    print("I am pleased to inform you that you have the job")
    signature("Humphrey Hopalong")
    
reject("Bill")
reject("Amanda")
accept("Vicki")