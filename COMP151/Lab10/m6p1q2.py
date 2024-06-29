def get_mass_unit():
    is_valid = False
    unit = ""
    while is_valid == False:
        unit = input("Please enter your preferred mass measurement (kg, lb, g or oz):")
        if unit in ["kg","lb","g", "oz"]:
            is_valid=True
        else:
            print(f'Sorry, {unit} is not a valid choice')
            
            
    return unit