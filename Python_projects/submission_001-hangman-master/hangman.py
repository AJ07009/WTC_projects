#TIP: use random.randint to get a random word from the list
import random


def read_file(file_name):    
    file = open(file_name,'r')
    words = file.readlines()
    return (words)


def select_random_word(words):
   wordgen = random.randint(0, len(words) -1)
   word = words[wordgen]
   rletter = list(word)
   letter = random.randint(0, len(rletter) -2)
   rletter[letter] = '_'
   s_word = ''.join(word)
   full = "".join(rletter)
   print ("Guess the word: " + full)
   return words[wordgen]



def get_user_input():
    answer = input("Guess the missing letter: ")
    return (answer) 



def run_game(file_name):
    words = read_file(file_name)
    word = select_random_word(words)
    answer = get_user_input()
    print('The word was: '+word)


if __name__ == "__main__":
    run_game('short_words.txt')

