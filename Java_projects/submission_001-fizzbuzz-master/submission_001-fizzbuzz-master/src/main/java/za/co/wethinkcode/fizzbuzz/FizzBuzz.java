package za.co.wethinkcode.fizzbuzz;

public class FizzBuzz {
     public String checkNumber(int number) {
        boolean divisibleBy3 = number % 3 == 0;
        boolean divisibleBy5 = number % 5 == 0;

     if ( divisibleBy3 && divisibleBy5 ) {
          return "FizzBuzz";
          }
     else if (divisibleBy3) {
          return "Fizz";
          }
     else if (divisibleBy5){
          return "Buzz";
          }
     return String.valueOf(number);
     }
     
     
     public String countTo(int number) {
     String result = "[";
     for(int i = 1; i <= number; i++){  
          result += checkNumber(i);
          if(i < number){
               result += ", ";

          }
     }
     result += "]";       
     return result;
        
    }
}
