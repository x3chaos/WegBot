package org.x3chaos.WegBot.tasks;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.x3chaos.WegBot.WegBot;
import org.x3chaos.WegBot.utils.TweetUtils;
import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetTask extends BotTask {
    
    private final Logger log;

    public TweetTask(Twitter twitter) {
        super(twitter, "TweetTask", 3600000L);
        log = WegBot.getLogger();
    }

    @Override
    public void run() {
        try {
            String tweet = createTweet();
            postTweet(tweet);
            log.info("Successfully posted tweet.");
        } catch (TwitterException ex) {
            getLogger().log(Level.SEVERE, null, ex);
        }
    }

    public String createTweet() throws TwitterException {
        List<String> tweets = TweetUtils.extractText(twitter.getUserTimeline(
                "x3chaos", new Paging(1, 75)));

        String tweet = TweetUtils.createTweet(tweets);
        log.info(String.format("Created new tweet: %s", tweet));

        return tweet;
    }

    private void postTweet(String tweet) throws TwitterException {
        log.info(String.format("Posting tweet: %s", tweet));
        twitter.updateStatus(tweet);
    }

}
