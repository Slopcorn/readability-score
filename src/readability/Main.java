package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        System.out.println(text);
        System.out.println(text.length() > 100 ? "HARD" : "EASY");
    }
}
