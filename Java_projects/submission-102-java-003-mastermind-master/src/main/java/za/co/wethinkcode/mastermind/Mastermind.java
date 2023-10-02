package za.co.wethinkcode.mastermind;
import java.util.*;

public class Mastermind {
    private final String code;
    private final Player player;
    private int match, intersect;
    private boolean isRunning;

    public Mastermind(CodeGenerator generator, Player player){
        this.code = generator.generateCode();
        this.player = player;
        this.match = 0;
        this.intersect = 1;
        this.isRunning = true;
        //System.out.println("fuck");
    }
    public Mastermind(){
        this(new CodeGenerator(), new Player());
    }

    public void runGame(){
        //TODO: implement the main run loop logic
        String user_guess;
        String prompt;

        System.out.println("4-digit Code has been set. Digits in range 1 to 8. You have 12 turns to break it.");
        while (this.isRunning) 
        {
            //get user guess
            //System.out.println("IN");

            user_guess = this.player.getGuess();
            //System.out.println(code);

            //System.out.println("OUT");
            // get two variables allowing for identification of user_guess
            this.countMatches(this.code, user_guess);
            this.countIntersect(this.code, user_guess);
            
            //update system (isRunning)
            System.out.printf("Number of correct digits in correct place: %d\n",
            this.match);
            System.out.printf("Number of correct digits not in correct place: %d\n",
            this.intersect);
            this.isRunning = (this.match == 4 || this.player.noGuesses())?false:true;

            //interface
            prompt = this.isRunning?"Turns left: "+this.player.getGuesses()
            :(this.match == 4)?"Congratulations! You are a codebreaker!":"No more turns left.";
            System.out.println(prompt);
            if (this.player.getGuesses() == 0)
            {
                System.out.println("The code was: "+ this.code);
            }
            else if(this.match == 4)
            {
                System.out.println("The code was: " + this.code);
            }

         //System.out.println(prompt);
         
        }
    }

    public void countMatches(String user_guess, String code)
    {
        int i = 0;
        

        for(int j = 0; j < 4; j++) {
            char str1 = user_guess.charAt(j);
            char str2 = code.charAt(j);  
            if ((str1 == str2)) {
                i++;
            }
        }
        
        this.match = i;
        
    }
    public void countIntersect(String user_guess, String code)
    {
        int count = 0;

        for(int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if(code.charAt(i) == user_guess.charAt(j) && i != j)
                {
                    count += 1;
                }
            }
        }
        this.intersect = count;
    }

    public static void main(String[] args){
        Mastermind game = new Mastermind();
        game.runGame();
    }
}
