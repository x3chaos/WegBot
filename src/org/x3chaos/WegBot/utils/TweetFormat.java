/*
 *  Author:       Shawn Lutch
 *  Project:      WegBot
 *  Description:  Generates pseudo-random text, given my tweets
 *
 *  Class:        org.x3chaos.WegBot.utils.TweetFormat
 *  Description:  Formatting methods
 *
 *  Date:         Feb 1, 2014
 */
package org.x3chaos.WegBot.utils;

/**
 * A FEW NOTES, BECAUSE I KNOW I'M GOING TO FORGET:
 *
 * - THE ORIGINAL WORD IN THE TWEET SHOULD BE LEFT WITH ALL OF ITS ORIGINAL
 * PUNCTUATION.
 *
 */
public class TweetFormat {

    /**
     * Formats the word to be used as a key in the Markov map
     *
     * @param word The word
     * @return The word, stripped of leading/trailing punctuation and converted
     * to lower-case
     */
    public static String hashMapKey(String word) {
        String result = word.replaceFirst("^[^a-zA-Z]+", "");
        result = result.replaceAll("[^a-zA-Z]+$", "");
        result = result.toLowerCase();

        // TODO anything else?
        return result;
    }

}
