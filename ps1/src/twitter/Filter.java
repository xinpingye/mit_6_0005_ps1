/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.List;
import java.time.Instant;
import java.util.ArrayList;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        //throw new RuntimeException("not implemented");
        
        List<Tweet> result = new ArrayList<Tweet>();
        
        for(Tweet t : tweets)
        {
            if(username.equals(t.getAuthor()))
                result.add(t);
        }
        
        return result;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        //throw new RuntimeException("not implemented");
        
        List<Tweet> result = new ArrayList<Tweet>();
        
        long begin_seconds = timespan.getStart().getEpochSecond();
        long end_seconds = timespan.getEnd().getEpochSecond();
        
        for(Tweet t : tweets)
        {
            long tweet_seconds = t.getTimestamp().getEpochSecond();
            if(tweet_seconds < begin_seconds || tweet_seconds > end_seconds)
                continue;
            result.add(t);
        }
        return result;
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        //throw new RuntimeException("not implemented");
        
        List<Tweet> result = new ArrayList<Tweet>();
        
        int flag = 0;
        
        for(Tweet t : tweets)
        {
            flag = 0;
            
            String[] strs = t.getText().split("\\s+");
            
            for(String str : strs)
            {
                for(String word : words)
                {
                    if(str.equals(word))
                    {
                        flag = 1;
                        break;
                    }
                }
                if(flag == 1)
                {
                    break;
                }
            }
            
            if(flag == 1)
                result.add(t);
        }
        
        return result;
    }

}
