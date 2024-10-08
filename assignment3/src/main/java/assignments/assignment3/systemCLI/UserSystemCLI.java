package assignments.assignment3.systemCLI;

import java.util.Scanner;

public abstract class UserSystemCLI {
    protected static Scanner input = new Scanner(System.in);
    public void run() {
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int command = input.nextInt();
            input.nextLine();
            isLoggedIn = handleMenu(command);
        }
    }
    // override this
    abstract void displayMenu();
    abstract boolean handleMenu(int command);
}
