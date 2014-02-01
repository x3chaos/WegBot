/*
 *  Author:         Shawn Lutch
 *  Project:        MarkovBot
 *  Description:    Generates pseudo-random text, given my tweets
 *
 *  Class:          org.x3chaos.MarkovBot.TweetUtils
 *  Description:    Several static util methods for processing tweets
 */
package org.x3chaos.WegBot.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.x3chaos.WegBot.utils.TweetFormat;
import twitter4j.Status;

public class TweetUtils {

    public static List<String> extractText(List<Status> statuses) {
        int entries = statuses.size();
        List<String> result = new ArrayList<>(entries);

        for (int i = 0; i < entries; i++) {
            String text = statuses.get(i).getText();
            result.add(text);
        }

        return result;
    }

    public static void printList(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static List<String> getFirstWords(List<String> statuses) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < statuses.size(); i++) {
            String status = statuses.get(i);
            result.add(getFirstWord(status));
        }

        return result;
    }

    private static String getFirstWord(String line) {
        return line.split(" ")[0];
    }

    public static List<String> formatLines(List<String> lines) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String origLine = lines.get(i);
            result.add(formatLine(origLine));
        }

        return result;
    }

    private static String formatLine(String line) {
        String result;

        // strip whitespace
        Pattern whitespace = Pattern.compile("\\s+");
        Matcher matcher = whitespace.matcher(line);
        result = matcher.replaceAll(" ");

        return result;
    }

    public static String getRandomWord(List<String> words) {
        int random = new Random().nextInt(words.size());
        return words.get(random);
    }

    /* IMPORTANT STUFF BELOW */
    /**
     * Processes a list of Strings and returns a HashMap that is structured as
     * such:
     *
     * KEY: STRING word1<br />
     * VALUE: LIST { nextword1, nextword2, ... , nextwordN }
     *
     * This HashMap contains information about each word that can be found in
     * the list of Strings that is passed to the method.
     *
     * @param statuses The list of Strings to use
     * @return a HashMap containing lists of words that follow other words
     */
    public static HashMap<String, List<String>> markov(List<String> statuses) {
        HashMap<String, List<String>> result = new HashMap<>();

        for (int s = 0; s < statuses.size(); s++) {
            String status = statuses.get(s);

            // split the status into words at ANY WHITESPACE
            // ( this regex is shorthand for [\\t\\n\\x0B\\f\\r] )
            String[] words = status.split("\\s+");

            for (int w = 0; w < words.length - 1; w++) {

                String word = words[w];
                String nextWord = words[w + 1];

                if (!isAcceptable(word) || !isAcceptable(nextWord))
                    continue;

                String key1 = TweetFormat.hashMapKey(word);
                String key2 = TweetFormat.hashMapKey(nextWord);

                if (key1.equals("") || key2.equals(""))
                    continue;

                // get the following words, or new list if null
                List<String> followingWords = result.get(key1);
                if (followingWords == null)
                    followingWords = new ArrayList<>();

                // add to the list and put it back
                followingWords.add(nextWord);
                result.put(key1, followingWords);

            } // done with each word!

        } // done with each status!

        return result;
    }

    public static String createTweet(List<String> statuses) {
        StringBuilder result = new StringBuilder();

        // get first words
        List<String> firstWords = getFirstWords(statuses);

        // pick a random first word
        String firstWord;
        do {
            firstWord = getRandomWord(firstWords);
        } while (!isAcceptable(firstWord));
        result.append(firstWord).append(" ");

        // GO FROM THERE, BUD
        HashMap<String, List<String>> markov = markov(statuses);

        // DON'T STOP TIL YOU HIT 140 CHARS
        // or, y'know, you wind up using what was the last word in a tweet
        int chars = 0, tries = 0, words = 0;
        String lastWord = firstWord;

        do {

            List<String> possibles = markov.get(lastWord);
            if (possibles == null) {
                if (words <= 3) {
                    // reset the loop with a new first word
                    lastWord = getRandomWord(firstWords);
                    continue;
                } else {
                    // eh just forget it
                    break;
                }
            }

            String randomWord = getRandomWord(possibles);
            if (isLink(randomWord)) { // don't let it link pls, that's bannable
                tries++;

                if (tries >= 3)
                    break;
                else
                    continue;
            }

            chars += (randomWord.length() + 1); // don't forget about the space!
            if (chars <= 140) {
                result.append(randomWord).append(" ");
                words++;
            }

            lastWord = randomWord;

        } while (chars <= 140);

        return result.toString();
    }

    private static boolean isAcceptable(String word) {
        return !isLink(word)
                && !isMention(word)
                && !isRT(word);
    }

    private static boolean isMention(String word) {
        return word.startsWith("@");
    }

    private static boolean isRT(String word) {
        return word.startsWith("RT");
    }

    private static boolean isLink(String word) {
        return word.startsWith("http");
    }

}
