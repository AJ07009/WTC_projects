import random
from random import randint


def run_game():
    user_input = ''
    system_range = ''

    number = []
    while len(number) < 4:
            digit = random.randint(1,8)
            if digit not in number:
                number.append(digit)
    # number = [5, 4, 3, 2]
    num = list(map(str, number))
    num = "".join(num)

    #print statement 
    print('4-digit Code has been set. Digits in range 1 to 8. You have 12 turns to break it.')
    
    
    state = True
    life_counter = 12
    
    while life_counter > 0:
        try:
         user_input = input("Input 4 digit code: ").strip()
         system_range = all(int(i)in range(1,9) for i in str(user_input))
        
        except Exception :
            pass
        # user_input = input("Input 4 digit code: ").strip()
        # system_range = all(int(i)in range(1,9) for i in str(user_input))
        
        if life_counter == 0:
            
            print("The code was: " + num)
            break
        else:
            
            if life_counter == 12:
                
                state != False
            # while life_counter:
            while len(user_input) != 4:
                
                # if len(user_input) != 4:
                #     print("if 3")
                print("Please enter exactly 4 digits.")
                user_input = input("Input 4 digit code: ").strip()
                system_range = all(int(i)in range(1,9) for i in str(user_input))  
            else: 
                # print(F"Turns left: {life_counter}")
                state = True
                while state:
                    if system_range == True:
                        state = False
                    else:
                        try:
                         print("Please enter exactly 4 digits.")
                         user_input = input("Input 4 digit code: ").strip()
                         system_range = all(int(i)in range(1,9) for i in str(user_input)) 
                        except Exception as identifier: 
                            pass
                        # print("Please enter exactly 4 digits.")
                        # user_input = input("Input 4 digit code: ").strip()
                        # system_range = all(int(i)in range(1,9) for i in str(user_input)) 
        a = 0
        counter1 = 0
        counter2 = 0
        
        while a < 4:
            number_list_index = str(number[a])
            guess_index = str(user_input[a])
            if guess_index==number_list_index:  
                counter1 = counter1 + 1
            elif guess_index in num:
                counter2 = counter2 + 1
            a=a+1
        # if state == number:
        if user_input == num:
            print("Number of correct digits in correct place:     "+str(counter1))
            print("Number of correct digits not in correct place: "+str(counter2))
            print("Congratulations! You are a codebreaker!")
            print("The code was: "+str(num))
            break
        else:
            print("Number of correct digits in correct place:     "+str(counter1))
            print("Number of correct digits not in correct place: "+str(counter2))
            
            life_counter = life_counter - 1
            print(F"Turns left: {life_counter}")
            

                    
        
                      



    # user_input = int(input("Input 4 digit code: "))


    
    # #Step 1. Randomization and unit storage
    # random_number = random.randint(0,9)
    # storage = {random_number}
    # return storage

    #Step 2. User input
    
    pass


if __name__ == "__main__":
    run_game()
