package org.x3chaos.WegBot.tasks;

import java.util.List;
import java.util.logging.Level;
import org.x3chaos.WegBot.utils.TweetUtils;
import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetTask extends BotTask {

    String lastTweet;

    public TweetTask(Twitter twitter) {
        super(twitter, "TweetTask", 3600000L);
    }

    @Override
    public void run() {
        try {
            String tweet = createTweet();
            // postTweet(tweet);
            System.out.println("Successfully posted tweet.");
            lastTweet = tweet;
        } catch (TwitterException ex) {
            getLogger().log(Level.SEVERE, null, ex);
        }
    }

    public String createTweet() throws TwitterException {
        List<String> tweets = TweetUtils.extractText(twitter.getUserTimeline(
                "x3chaos", new Paging(1, 75)));

        String tweet = TweetUtils.createTweet(tweets);
        System.out.println("Created new tweet: " + tweet);

        return tweet;
    }

    private void postTweet(String tweet) throws TwitterException {
        System.out.println("Posting tweet: " + tweet);
        twitter.updateStatus(tweet);
    }

}
