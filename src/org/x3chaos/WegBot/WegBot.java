/*
 *  Author:         Shawn Lutch
 *  Project:        WegBot
 *  Description:    Generates pseudo-random text, given a few of my tweets
 *
 *  Class:          org.x3chaos.WegBot.WegBot
 *  Description:    The bot class and main class
 */
package org.x3chaos.WegBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Logger;
import org.x3chaos.WegBot.logging.BotLogger;
import org.x3chaos.WegBot.tasks.BotTask;
import org.x3chaos.WegBot.tasks.FollowBackTask;
import org.x3chaos.WegBot.tasks.TweetTask;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class WegBot {

    // logging formats
    private static final String FMT_TaskStart = "Starting task: %s";
    private static final String FMT_TaskReg = "Registering task: %s";

    // logger
    private static final Logger log = BotLogger.getLogger("WegBot");

    // authorize as @wegbot
    private final Twitter twitter;

    // stores registered tasks that are started at runtime
    private final List<BotTask> tasks = new ArrayList<>();

    public WegBot() throws TwitterException {
        // create a new instance of twitter4j.Twitter
        twitter = new TwitterFactory().getInstance();
    }

    public void startAllTasks() {
        log.info("Starting all tasks...");

        for (BotTask task : tasks) {
            log.info(String.format(FMT_TaskStart, task.getName()));
            new Timer().scheduleAtFixedRate(task, 10000L, task.getPeriod());
        }
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public static Logger getLogger() {
        return log;
    }

    public void registerTask(BotTask task) {
        log.info(String.format(FMT_TaskReg, task.getName()));
        tasks.add(task);
    }

    /* ************
     * MAIN CLASS *
     ************ */
    public static void main(String[] args) throws Exception {
        log.info("Initializing...");

        WegBot bot = new WegBot();
        Twitter twitter = bot.getTwitter();

        bot.registerTask(new FollowBackTask(twitter));
        bot.registerTask(new TweetTask(twitter));

        bot.startAllTasks();
    }
}
