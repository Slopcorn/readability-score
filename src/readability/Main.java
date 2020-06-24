package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) System.out.println("Please call the program with path to the input file.");
        else try (Scanner scanner = new Scanner(new File(args[0]))) {
            String text = getFileText(scanner);
            printTextInformation(text);
        } catch (FileNotFoundException e) {
            System.out.println("Invalid filepath: " + args[0]);
        }
    }

    /** Calculates the ARI.
     * @param chars The amount of characters in the text, not counting whitespace.
     * @param words The amount of words in the text.
     * @param sents The amount of sentences in the text.
     * @return The automated readability index of the text.
     */
    private static double automatedReadabilityIndex(int chars, int words, int sents) {
        return 4.71 * chars / words + 0.5 * words / sents - 21.43;
    }

    /** Gets and prints relevant information about input text.
     * @param text Input text string.
     */
    private static void printTextInformation(String text) {
        // Counters for sentences and words
        int sents = sentCount(text);
        int words = wordCount(text);
        int chars = charCount(text);

        double ari = automatedReadabilityIndex(chars, words, sents);

        System.out.println("The text is:");
        System.out.println(text);

        System.out.printf("Words: %d\n", words);
        System.out.printf("Sentences: %d\n", sents);
        System.out.printf("Characters: %d\n", chars);

        System.out.printf("The score is: %.2f\n", ari);
        System.out.println(readingLevelARI(ari));
    }

    /** Returns the description of the reading age necessary for reading a text
     * based on its ARI score.
     * @param ari ARI score of a given text.
     * @return Description of the necessary reading age.
     */
    private static String readingLevelARI(double ari) {
        String age = switch ((int) Math.ceil(ari)) {
            case 1 -> "5-6";
            case 2 -> "6-7";
            case 3 -> "7-9";
            case 4 -> "9-10";
            case 5 -> "10-11";
            case 6 -> "11-12";
            case 7 -> "12-13";
            case 8 -> "13-14";
            case 9 -> "14-15";
            case 10 -> "15-16";
            case 11 -> "16-17";
            case 12 -> "17-18";
            case 13 -> "18-24";
            default -> "24+";
        };

        return "This text should be understood by " + age + " year olds.";
    }

    /** Finds the amount of sentences in a text.
     * @param text The input text as String.
     * @return The amount of sentences in a text.
     */
    private static int sentCount(String text) {
        int sents = 0;
        for (String sentCand : text.split("[.!?]")) {
            // Naive check, newline necessary because we add it necessarily after every line on input.
            if (!"".equals(sentCand) && !"\n".equals(sentCand)) sents++;
        }
        return sents;
    }

    /** Finds the amount of words in a text.
     * @param text The input text as String.
     * @return The amount of words in the text.
     */
    private static int wordCount(String text) {
        int words = 0;
        for (String wordCand : text.split("[\t\n .!?]")) {
            if (!"".equals(wordCand)) words++;  // Naive check
        }
        return words;
    }

    /** Finds the amount of visible characters in the text.
     * @param text The input text as String.
     * @return The amount of visible chars (in regular text).
     */
    private static int charCount(String text) {
        int chars = 0;
        for (char c : text.toCharArray()) {
            if (!Character.isWhitespace(c)) chars++;
        }
        return chars;
    }

    /** Gets all of the text from a scanner.
     * @param scanner Input scanner.
     * @return A string containing all of the text.
     */
    private static String getFileText(Scanner scanner) {
        StringBuilder sb = new StringBuilder();

        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
            sb.append('\n');
        }

        return sb.toString();
    }
}
