import random

correct_digits_and_position = 0
correct_digits_only = 0
code = [0,0,0,0]

def num_gen():
    # this function generates the code and returns it """
    code = [0,0,0,0]
    #global code
    for i in range(4):
        value = random.randint(1, 8) # 8 possible digits
        while value in code:
            value = random.randint(1, 8)  # 8 possible digits
        code[i] = value
    print('4-digit Code has been set. Digits in range 1 to 8. You have 12 turns to break it.')
    return code
   
def user_input(code, answer):
    # this fuctions checks the input from the user to checks how many numbers in the guess are correct and returns whether the code is correct or not"""
    correct_digits_and_position = 0
    correct_digits_only = 0
    for i in range(len(answer)):
        if code[i] == int(answer[i]):
            correct_digits_and_position += 1
        elif int(answer[i]) in code:
            correct_digits_only += 1
    print('Number of correct digits in correct place:     '+str(correct_digits_and_position))
    print('Number of correct digits not in correct place: '+str(correct_digits_only))
    if correct_digits_and_position == 4:
        return True
    else:
        return False

def game_looper(code):
    # this function runs the main game loop, calls the other functions and inputs error checking"""
    correct = False
    turns = 0
    while not correct and turns < 12:
        answer = input("Input 4 digit code: ")
        if len(answer) > 4 or len(answer) < 4:
            print("Please enter exactly 4 digits.")
            continue
        correct = user_input(code, answer)
        turns += 1
        if correct == True:
            print('Congratulations! You are a codebreaker!')
        else:
            print('Turns left: '+str(12 - turns))

    print('The code was: '+str(code))

def run_game(): 
    # function runs game
    code = num_gen()
    game_looper(code) 

if __name__ == "__main__":
    run_game()