import random


def read_file(file_name):
    file = open(file_name,'r')
    return file.readlines()


def get_user_input():
    return input('Guess the missing letter: ')


def ask_file_name():
    file_name = input("Words file? [leave empty to use short_words.txt] : ")
    if not file_name:
        return 'short_words.txt'
    return file_name


def select_random_word(words):
    random_index = random.randint(0, len(words)-1)
    word = words[random_index].strip()
    return word


# TODO: Step 1 - update to randomly fill in one character of the word only
def random_fill_word(word):
   letter = random.choice(word)
   index = word.index(letter)
   remaining_letter = list("_"*len(word))
   remaining_letter[index] = word[index]
   display_word = ''.join(remaining_letter)
   return(display_word)



# TODO: Step 1 - update to check if character is one of the missing characters
def is_missing_char(original_word, answer_word, char):
  for a in range(len(original_word)):
        if char == original_word[a] and answer_word[a] == "_":
              return True
  return False
              


# TODO: Step 1 - fill in missing char in word and return new more complete word
def fill_in_char(original_word, answer_word, char):
    new_answer = list(answer_word)
    list_index = len(original_word) - 1
    a = 0
    while a <= list_index:
        if original_word[a] == char:
            new_answer[a] = char
            a = a+1
        else:
            a = a+1
    str1 = ""
    return str1.join(new_answer)


 

def do_correct_answer(original_word, answer, guess):
    answer = fill_in_char(original_word, answer, guess)
    print(answer)
    return answer


# TODO: Step 4: update to use number of remaining guesses
def do_wrong_answer(answer, number_guesses):
 if number_guesses > 0:
  print('Wrong! Number of guesses left: '+str(number_guesses))
  draw_figure(number_guesses)
 else:
  print('Wrong! Number of guesses left: '+str(number_guesses))
  draw_figure(number_guesses)
  print("Sorry, you are out of guesses. The word was: "+answer)
    
# TODO: Step 5: draw hangman stick figure, based on number of guesses remaining
def draw_figure(number_guesses):
  if number_guesses == 4:
    print("/----\n|\n|\n|\n|\n_______")
  elif number_guesses == 3:
    print("/----\n|   0\n|\n|\n|\n_______")
  elif number_guesses == 2:
    print("/----\n|   0\n|  /|\\\n|\n|\n_______")
  elif number_guesses == 1:
    print("/----\n|   0\n|  /|\\\n|   |\n|\n_______")
  elif number_guesses == 0:
    print("/----\n|   0\n|  /|\\\n|   |\n|  / \\\n_______")

# TODO: Step 2 - update to loop over getting input and checking until whole word guessed
# TODO: Step 3 - update loop to exit game if user types `exit` or `quit`
# TODO: Step 4 - keep track of number of remaining guesses
def run_game_loop(word, answer):
 print("Guess the word: "+answer)
 counter = 5
 while answer != word:
  guess = get_user_input()
  if guess == "quit" or guess == "exit":
    print("Bye!")
    break
  else:
    if is_missing_char(word, answer, guess):
      answer = do_correct_answer(word, answer, guess)
    else:
      counter = counter - 1
      do_wrong_answer(word, counter)
    if counter == 0 :
      break
                

  
  
  # guesses = 5
  # while guesses >= 0:
  #  if guesses == 0:
  #   print("Sorry, you are out of guesses. The word was: " + word)
  #   break
  # if word == answer:
  #   #break
  #   guesses = get_user_input()
  # if guesses == "quit" or guesses == "exit":
  #   print("Bye!")
  #   #break
  # if is_missing_char(word, answer, guesses):
  #       answer = do_correct_answer(word, answer, guesses)
  # else:
  #   guesses = do_wrong_answer(answer, guesses)     


             
  
# TODO: Step 6 - update to get words_file to use from commandline argument
if __name__ == "__main__":
    words_file = ask_file_name()
    words = read_file(words_file)
    selected_word = select_random_word(words)
    current_answer = random_fill_word(selected_word)

    run_game_loop(selected_word, current_answer)

