package za.co.wethinkcode.examples.hangman;

import java.util.*;

public class Answer {
    private final String value;

   
    public Answer(String value) {
        this.value = value;
    }

    public Answer getHint(Answer lastAnswer, char guess) {                           
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < this.value.length(); i++) {
            if (guess == this.value.charAt(i)) {
                result.append(guess);
            } else {
                result.append(lastAnswer.toString().charAt(i));
            }
        }
        return new Answer(result.toString());
    }

   
    public Answer generateRandomHint() {
        Random random = new Random();
        int randomIndex = random.nextInt(this.value.length() - 1);

        String noLetters = "_".repeat(this.value.length());
        return this.getHint( new Answer(noLetters),                                                     
                this.value.charAt(randomIndex));
    }

   
    public boolean hasLetter(char letter) {
        return this.value.indexOf(letter) >= 0;
    }

    public boolean isGoodGuess(Answer wordToGuess, char letter) {
        return wordToGuess.hasLetter(letter) && !this.hasLetter(letter);                        
    }

    @Override
    public boolean equals(Object obj) {                                                                 
        Answer otherAnswer = (Answer) obj;                                                              
        return this.toString().equalsIgnoreCase(otherAnswer.toString());                                     
    }

    @Override
    public String toString() {
        return new String(value);
    }
}