import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int minNumber = 1;
        int maxNumber = 100;
        int totalRounds = 3;
        int totalAttempts = 5;
        int totalScore = 0;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I'll generate a number between " + minNumber + " and " + maxNumber + " and you have " + totalAttempts + " attempts to guess it.");
        System.out.println("Let's start!");

        for (int round = 1; round <= totalRounds; round++) 
        {
            int targetNumber = random.nextInt(maxNumber - minNumber + 1) + minNumber;
            int attemptsLeft = totalAttempts;
           System.out.println("\n" + "\t".repeat(4) + "Round " + round + ":\n");
            System.out.println("Guess the number!");

            while (attemptsLeft > 0) 
            {
                System.out.print("Attempt " + (totalAttempts - attemptsLeft + 1) + "/" + totalAttempts + ": ");
                int guess = scanner.nextInt();
                attemptsLeft--;

                if (guess == targetNumber) 
                {
                    int roundScore = attemptsLeft + 1;
                    totalScore += roundScore;
                    System.out.println("Congratulations! You guessed the number in " + roundScore + " attempts.");
                    break;
                } else if (guess < targetNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }

            if (attemptsLeft == 0) {
                System.out.println("Sorry, you've run out of attempts. The correct number was " + targetNumber + ".");
            }
        }

        System.out.println("\nGame Over!");
        System.out.println("Your total score is: " + totalScore);

        scanner.close();
    }
}
