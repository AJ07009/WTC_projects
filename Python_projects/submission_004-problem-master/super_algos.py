from dataclasses import dataclass


def validate(element, t):
    '''
    Function to validate that a list contains one type of element
    parameters: element (the list), t (the specified type)
    return: True (if valid), False (if invalid)
    '''
    for e in element:
        if type(e) != t:
            return False
    return True


def find_min(elements):
    '''
    This function will hanlde the information after it is done recuring
    '''
    if not validate(elements, int):
        return -1
    
    unit_set = elements[0]
    rec_unit = rec_min(unit_set, elements[1:])
    return rec_unit
     
    


def rec_min(unit_set, elements):
    '''
    This will find the lowest number through recursion
    '''
    if len(elements) == 0:
        return  unit_set
    e = elements[0]
    if e < unit_set:
        unit_set = e
    min_unit = rec_min(unit_set, elements[1:])
    return min_unit     


def sum_all(elements):
    '''
    This function will hanlde the information after it is done recuring
    '''
    if not validate(elements, int):
        return -1
    
    m = elements[0]
    sum_unit = rec_sum(m, elements[1:])
    return sum_unit


def rec_sum(sum_unit, elements):
    '''
    This will add the units in the string together
    '''
    if len(elements) == 0:
        return sum_unit
    e = elements[0]
    sum_unit += e
    i = rec_sum(sum_unit, elements[1:])
    return i



def find_possible_strings(char_set, n):
    """The function find_possible_strings will return a list of all possible trings of"""
    """The function find_possible_strings will check if all values in the given parameter list is strings.
        This function will also get the legth of the given parameter list.
        This function will also call the print_possible_strings function."""
    character_set_lenth = len(char_set)

    for e in char_set :
        if type(e) != str:
            return []

    if character_set_lenth == 0:
        return []

    if(n==0):
        return [""]
    word = []
    try:
        for i in char_set:
            for k in find_possible_strings(char_set, n-1):
                word.append(i+k)
    except :
        return []
    return word


