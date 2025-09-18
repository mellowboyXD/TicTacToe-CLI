import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static final char PLAYER_X = 'X';
    public static final char PLAYER_O = 'O';
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Tic-Tac-Toe\n\tby mellowboy");
        int choice;
        do {
            printMenu();
            choice = inputValidateInt(input, "Enter choice: ");
            switch (choice) {
                case 0 -> {}
                case 1 -> runGame(input, true);
                case 2 -> runGame(input, false);
                default -> System.out.println("Invalid choice! Try again");
            }
        } while(choice != 0);

        input.close();
        System.out.println("\nmade with passion. ~ mellowboy");
    }

    /**
     * Prints the main menu to the screen.
     */
    public static void printMenu() {
        System.out.println();
        System.out.println("Main Menu");
        System.out.println("0. Quit");
        System.out.println("1. Single Player");
        System.out.println("2. Pass n Play");
    }

    /**
     * Prints the actual game board in a familiar pleasant style.
     * @param board
     */
    public static void printBoard(char[] board) {
        System.out.println();
        System.out.println(board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("- + - + -");
        System.out.println(board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("- + - + -");
        System.out.println(board[6] + " | " + board[7] + " | " + board[8]);
    }

    /**
     * Read input as an Integer from stdin.
     * Loops until valid input.
     * @param prompt - Message prompting for input.
     * @param errorMessage - Message to display if invalid input.
     * @return The integer user input.
     */
    public static int inputInt(Scanner input, String prompt, String errorMessage) {
        int choice;
        do {
            System.out.print(prompt);
            try {
                choice = input.nextInt();
            } catch (InputMismatchException err) {
                System.out.println(errorMessage);
                input.next();   // consumes invalid input
                choice  = -1;
            }
        } while (choice == -1);

        return choice;
    }

    /**
     * Runs inputValidateInt with default errorMessage as argument.
     * @param input - stdin
     * @param prompt - Message to display to user before reading input.
     * @return returns the integer choice made by user.
     */
    public static int inputValidateInt(Scanner input, String prompt) {
        return inputInt(input, prompt, "Invalid choice. Enter numbers only.");
    }

    /**
     * Checks for win conditions.
     * @param board - The current status of the game board.
     * @param valToCheck - Either PLAYER_X or PLAYER_O
     * @return Either True or False depending if a win condition was found.
     */
    public static boolean hasWon(char[] board, char valToCheck) {
        if (   board[0] + board[1] + board[2] == (valToCheck * 3)
            || board[0] + board[3] + board[6] == (valToCheck * 3) 
            || board[0] + board[4] + board[8] == (valToCheck * 3)
            || board[1] + board[4] + board[7] == (valToCheck * 3)
            || board[2] + board[5] + board[8] == (valToCheck * 3)
            || board[2] + board[4] + board[6] == (valToCheck * 3)
            || board[3] + board[4] + board[5] == (valToCheck * 3)
            || board[6] + board[7] + board[8] == (valToCheck * 3)
            ) {
                return true;
            }

        return false;
    }

    /**
     * Randomly selects a String number from slots and returns it as an int.
     * @param board - The actual status of the game board.
     * @param slots - The available game board slots.
     * @return An int representing a valid game slot if any.
     */
    public static int getComputerMove(char[] board, ArrayList<String> slots) {
        Random random = new Random();
        int computerO;
        if (slots.size() == 0) {
            System.out.println("It's a tie!");
            return 0;
        }

        System.out.println("Computer's turn...");
        try {
            Thread.sleep(500);
        } catch (InterruptedException _) {
            // Handle the condition where the thread is interupted during sleep
            Thread.currentThread().interrupt(); // Ensures interruption status is preserved
            System.out.println("Thread was interrupted during sleep.");
        }

        do {
            int i = random.nextInt(slots.size());
            computerO = Integer.parseInt(slots.get(i));
        } while (board[computerO - 1] == PLAYER_X || board[computerO - 1] == PLAYER_O);
        return computerO;
    }

    /**
     * Prompts user and returns a valid int representing a valid slot.
     * @param input - Standard Input
     * @param board - Actual status of the game board.
     * @param prompt - Message to display before requesting input.
     * @return An integer representing a valid slot.
     */
    public static int getPlayerMove(Scanner input, char[] board, String prompt) {
        int player = -1;
        do {
            player = inputInt(input, prompt, "Enter numbers only in range 1-9");
            if (player == 0)
                return 0;

            if (board[player - 1] == PLAYER_X || board[player - 1] == PLAYER_O) {
                System.out.println("Slots already taken! Try again!");
                player = -1;
            }
        } while (player < 0 || player > 9);
        return player;
    }

    /**
     * Main game loop.
     * @param input - Standard Input
     * @param singlePlayer - Determines if playing against computer or not.
     */
    public static void runGame(Scanner input, boolean singlePlayer){
        int playerX = -1;
        int playerO = -1;
        char[] board = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};

        ArrayList<String> availableSlots = new ArrayList<String>();
        for (int i = 1; i < 10; ++i) {
            availableSlots.add(Integer.toString(i));
        }

        while(playerX != 0 && playerO != 0) {
            printBoard(board);
            playerX = getPlayerMove(input, board, "Enter choice player X: ");
            if (playerX == 0)
                return;

            board[playerX - 1] = PLAYER_X;
            availableSlots.remove(Integer.toString(playerX));

            // check win condition
            if (hasWon(board, PLAYER_X)) {
                printBoard(board);
                System.out.println("Congratulations! Player X won.");
                return;
            } else if (availableSlots.size() == 0) {
                System.out.println("Its a tie!");
                return;
            }

            if (singlePlayer)
                playerO = getComputerMove(board, availableSlots);
            else { 
                printBoard(board);
                playerO = getPlayerMove(input, board, "Enter choice player O:");
            }

            if (playerO == 0)
                return;

            board[playerO - 1] = PLAYER_O;
            availableSlots.remove(Integer.toString(playerO));

            if (hasWon(board, PLAYER_O)) {
                printBoard(board);
                System.out.println(singlePlayer ? "Game Over! You lost." : "Congratulations! Player O won.");
                return;
            } else if (availableSlots.size() == 0) {
                System.out.println("Its a tie!");
                return;
            }
        }
    }
}
