package org.x3chaos.WegBot.tasks;

import java.util.logging.Level;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author Shawn
 */
public class FollowBackTask extends BotTask {

    long[] followers;
    long[] following;

    public FollowBackTask(Twitter twitter) throws TwitterException {
        super(twitter, "FollowBackTask", 60000L);

        updateFollowerLists();
    }

    @Override
    public void run() {
        try {
            updateFollowerLists();
            followBackFollowers();

            // trailing newline
            System.out.println();
        } catch (TwitterException ex) {
            getLogger().log(Level.SEVERE, "Unable to follow back", ex);
        }
    }

    private void updateFollowerLists() throws TwitterException {
        System.out.println("Updating follower lists...");
        followers = twitter.getFollowersIDs(-1).getIDs();
        following = twitter.getFriendsIDs(-1).getIDs();
    }

    private void followBackFollowers() throws TwitterException {
        System.out.println("Checking for new followers...");
        for (long follower : followers) {
            if (!isBotFollowing(follower)) {
                User user = twitter.showUser(follower);
                System.out.println("Now following @" + user.getScreenName());

                twitter.createFriendship(follower);
            }
        }
    }

    private boolean isBotFollowing(long id) {
        for (long friend : following) {
            if (friend == id)
                return true;
        }

        return false;
    }

}
