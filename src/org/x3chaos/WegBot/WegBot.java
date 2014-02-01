/*
 *  Author:         Shawn Lutch
 *  Project:        WegBot
 *  Description:    Generates pseudo-random text, given my tweets
 *
 *  Class:          org.x3chaos.WegBot.WegBot
 *  Description:    The bot class
 */
package org.x3chaos.WegBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Logger;
import org.x3chaos.WegBot.tasks.BotTask;
import org.x3chaos.WegBot.tasks.FollowBackTask;
import org.x3chaos.WegBot.tasks.TweetTask;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class WegBot {

    private static final Logger log = Logger.getLogger("MarkovBot");
    private final Twitter twitter; // authorize as @x3chaos

    private final List<BotTask> tasks = new ArrayList<>();

    public WegBot() throws TwitterException {
        twitter = new TwitterFactory().getInstance();
    }

    public void startAllTasks() {
        System.out.println("Starting all tasks...");

        for (BotTask task : tasks) {
            System.out.println("Starting task: " + task.getName());
            new Timer().scheduleAtFixedRate(task, 10000L, task.getPeriod());
        }
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public void registerTask(BotTask task) {
        System.out.println("Registering task: " + task.getName());
        tasks.add(task);
    }

    /* ************
     * MAIN CLASS *
     ************ */
    public static void main(String[] args) throws Exception {
        System.out.println("Initializing...");

        WegBot bot = new WegBot();
        Twitter twitter = bot.getTwitter();

        // bot.registerTask(new FollowBackTask(twitter));
        bot.registerTask(new TweetTask(twitter));

        bot.startAllTasks();
    }
}
