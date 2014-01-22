/*
 *  Author:         Shawn Lutch
 *  Project:        MarkovBot
 *  Description:    Generates pseudo-random text, given my last 20 tweets
 *
 *  Class:          org.x3chaos.MarkovBot.MarkovBot
 *  Description:    The bot class
 */
package org.x3chaos.MarkovBot;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class MarkovBot {

    private final Twitter twitter; // authorize as @x3chaos
    private static final Logger log = Logger.getLogger("MarkovBot");

    public MarkovBot() {
        twitter = new TwitterFactory().getInstance();
    }

    // get last 75 statuses
    public List<Status> getStatuses(int max) throws TwitterException {
        System.out.println("Retrieving x3chaos' last " + max + " tweets...");
        return twitter.getUserTimeline("x3chaos", new Paging(1, max));
    }

    //
    public void postTweet(String tweet) throws TwitterException {
        System.out.println("Posting new tweet...");
        twitter.updateStatus(tweet);
    }

    /* ************
     * MAIN CLASS *
     ************ */
    public static void main(String[] args) throws Exception {
        final MarkovBot bot = new MarkovBot();

        final long delay = 10000L;
        final long period = 3600000L;

        new Timer().scheduleAtFixedRate(new TimerTask() {

            /**
             * Attempts to create and post a new tweet. Recursively retries if
             * an exception is thrown.
             */
            private void attempt() {
                System.out.println();
                boolean success = false;

                try {
                    createTweet(bot);

                    success = true;
                    System.out.println("Successfully tweeted.");

                    long nM = Calendar.getInstance().getTimeInMillis() + period;
                    Date next = new Date(nM);

                    System.out.print("Next tweet will occur at ");
                    System.out.println(next.toString());
                } catch (TwitterException ex) {
                    log.log(Level.SEVERE, null, ex);
                } finally {
                    if (!success)
                        attempt();
                }
            }

            @Override
            public void run() {
                attempt();
            }

        }, delay, period);
    }

    public static void createTweet(MarkovBot bot) throws TwitterException {
        // get the statuses and extract just the text
        List<Status> statuses = bot.getStatuses(75);
        List<String> lines = TweetUtils.extractText(statuses);

        // create and print the tweet
        String tweet = TweetUtils.createTweet(lines);
        System.out.println("CREATED NEW TWEET: " + tweet);

        // send the tweet
        bot.postTweet(tweet);
    }
}
