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

    /** Calculates the Flesch-Kincaid readability index.
     * @param words The amount of words in the text.
     * @param syllables The amount of syllables in the text.
     * @param sents The amount of sentences in the text.
     * @return The Flesch-Kincaid score of the text.
     */
    private static double fleschKincaid(int words, int syllables, int sents) {
        return 0.39 * words / sents + 11.8 * syllables / words - 15.59;
    }

    /** Calculates the SMOG index.
     * @param polysyllables The amount of polysyllabic words in the text.
     * @param sents The amount of sentences in the text.
     * @return The SMOG index.
     */
    private static double smogIndex(int polysyllables, int sents) {
        return 1.043 * Math.sqrt(polysyllables * 30.0 / sents) + 3.1291;
    }

    /** Calculates the Coleman-Liau index.
     * @param chars The amount of characters in the text, not counting whitespace.
     * @param words The amount of words in the text.
     * @param sents The amount of sentences in the text.
     * @return The Coleman-Liau index of the text.
     */
    private static double colemanLiau(int chars, int words, int sents) {
        double l = 100.0 * chars / words;   // Average number of characters per 100 words.
        double s = 100.0 * sents / words;   // Average number of sentences  per 100 words.
        return 0.0588 * l - 0.296 * s - 15.8;
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
        // TODO: Syllables
        // TODO: Polysyllables

        System.out.print("Enter the score you wish to calculate (ARI, FK, SMOG, CL, all): ");

        // TODO: finish the switch mechanics
        switch (new Scanner(System.in).next()) {
            case "ARI":
                System.out.printf("Automated Readability Index: %.2f (about %d year olds).\n", ari, readingLevel(ari));
                break;
            case "FK":
                // TODO: implement FK
                break;
            case "SMOG":
                // TODO: implement SMOG
                break;
            case "CL":
                // TODO: implement CL
                break;
            case "all":
                // TODO: implement the rest
                break;
            default:
                System.out.println("No such index exists, aborting...");
                break;
        }
    }

    /** Returns the reading age necessary for reading a text based on its readability index.
     * @param score Readability score of a given text.
     * @return Upper bound of reading age necessary.
     */
    private static int readingLevel(double score) {
        return switch ((int) Math.round(score)) {
            case 1 -> 6;
            case 2 -> 7;
            case 3 -> 9;
            case 4 -> 10;
            case 5 -> 11;
            case 6 -> 12;
            case 7 -> 13;
            case 8 -> 14;
            case 9 -> 15;
            case 10 -> 16;
            case 11 -> 17;
            case 12 -> 18;
            case 13 -> 24;
            default -> 25; // This MAY not be quite right.
        };
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
