package org.x3chaos.WegBot.tasks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Logger;
import twitter4j.Twitter;

/**
 *
 * @author Shawn
 */
public abstract class BotTask extends TimerTask implements Serializable {

    // instance fields
    protected Twitter twitter;
    protected String name;
    protected long period;

    public BotTask(Twitter twitter, String name, long period) {
        this.twitter = twitter;
        this.name = name;
        this.period = period;
    }

    public Logger getLogger() {
        return Logger.getLogger(name);
    }

    public String getName() {
        return name;
    }

    public long getPeriod() {
        return period;
    }

}
