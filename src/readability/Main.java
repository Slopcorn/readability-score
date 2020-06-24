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

    /** Gets and prints relevant information about input text.
     * @param text Input text string.
     */
    private static void printTextInformation(String text) {
        // Counters for sentences and words
        String[] wordArr = text.toLowerCase().split("\\W+");
        int sents = sentCount(text);
        int words = wordArr.length;
        int chars = charCount(text);
        int sylls = syllCount(wordArr);
        int polys = polyCount(wordArr);

        // Scores
        double ari      = automatedReadabilityIndex(chars, words, sents);
        double flesch   = fleschKincaid(words, sylls, sents);
        double smog     = smogIndex(polys, sents);
        double coleman  = colemanLiau(chars, words, sents);

        // Ages
        int ariAge  = readingLevel(ari);
        int fkAge   = readingLevel(flesch);
        int smogAge = readingLevel(smog);
        int clAge   = readingLevel(coleman);
        double avgAge = (ariAge + fkAge + smogAge + clAge) / 4f;

        // Text printout
        System.out.println("The text is:");
        System.out.println(text);

        // Text basic information
        System.out.println("Basic information about the text");
        System.out.printf("Words: %d\n", words);
        System.out.printf("Sentences: %d\n", sents);
        System.out.printf("Characters: %d\n", chars);
        System.out.printf("Syllables: %d\n", sylls);
        System.out.printf("Polysyllables: %d\n", polys);

        System.out.println();
        System.out.println("Information derived by readability score indices");
        System.out.println("Method\tScore\tAge");
        System.out.printf("ARI\t\t%.2f\t%d\n", ari, ariAge);
        System.out.printf("FK\t\t%.2f\t%d\n", flesch, fkAge);
        System.out.printf("SMOG\t%.2f\t%d\n", smog, smogAge);
        System.out.printf("CL\t\t%.2f\t%d\n", coleman, clAge);

        System.out.printf("avg\t\t\t\t%.2f\n", avgAge);
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

    /** Returns the reading age necessary for reading a text based on its readability index.
     * @param score Readability score of a given text.
     * @return Upper bound of reading age necessary.
     */
    private static int readingLevel(double score) {
        switch ((int) Math.round(score)) {
            case 1:
                return 6;
            case 2:
                return 7;
            case 3:
                return 9;
            case 4:
                return 10;
            case 5:
                return 11;
            case 6:
                return 12;
            case 7:
                return 13;
            case 8:
                return 14;
            case 9:
                return 15;
            case 10:
                return 16;
            case 11:
                return 17;
            case 12:
                return 18;
            case 13:
                return 24;
            default:
                return 25;
        }
    }

    /** Finds the amount of sentences in a text.
     * @param text The input text as String.
     * @return The amount of sentences in a text.
     */
    private static int sentCount(String text) {
        int sents = 0;
        for (String sentCand : text.split("[.!?]+")) {
            // Naive check, newline necessary because we add it necessarily after every line on input.
            if (!"".equals(sentCand) && !"\n".equals(sentCand)) sents++;
        }
        return sents;
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

    /** Finds the amount of syllables in the text.
     * @param words The list of words in the text.
     * @return The amount of syllables.
     */
    private static int syllCount(String[] words) {
        int sylls = 0;
        for (String word : words) {
            sylls += syllables(word);
        }
        return sylls;
    }

    /** Finds the amount of polysyllabic words in the text.
     * @param words The list of words in the text.
     * @return The amount of polysyllabic words.
     */
    private static int polyCount(String[] words) {
        int polys = 0;
        for (String word : words) if (syllables(word) > 2) {
            polys++;
        }
        return polys;
    }

    /** Finds out how many syllables a certain word has.
     *  Input is assumed to be lowercase.
     * @param word A single word as a string.
     * @return The amount of syllables in the word.
     */
    private static int syllables(String word) {
        int     syllables = 0;
        boolean inVowel   = false;
        char[]  vowels    = {'a', 'e', 'i', 'o', 'u', 'y'};

        // I will use this to simplify logic further in.
        // If the end is an 'e', I simply ignore the e later on by
        // subtracting this value from the maximum index.
        int end = word.charAt(word.length() - 1) == 'e' ? 1 : 0;

        // Now we simply count syllables that do not directly follow one another.
        for (int i = 0; i < word.length() - end; i++) {
            char c = word.charAt(i);
            if (isIn(c, vowels) && !inVowel) {          // Newly meeting a vowel
                inVowel = true;
                syllables++;
            } else if (!isIn(c, vowels) && inVowel) {   // Newly meeting a non-vowel
                inVowel = false;
            }
        }

        // If we did not read any syllables, say the word is monosyllabic.
        return syllables == 0 ? 1 : syllables;
    }

    /** Method to check if a character is in an array of characters.
     * @param c Input character.
     * @param chArray Array that we check for the character.
     * @return Boolean value stating if the character is in the array.
     */
    private static boolean isIn(char c, char[] chArray) {
        for (char c1 : chArray) if (c1 == c) return true;
        return false;
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
