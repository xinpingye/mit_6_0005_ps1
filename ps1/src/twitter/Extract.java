/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.time.Instant;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        //throw new RuntimeException("not implemented");
        Instant begin = tweets.get(0).getTimestamp();
        Instant end = tweets.get(0).getTimestamp();
        for(Tweet t : tweets)
        {
            if(t.getTimestamp().getEpochSecond() < begin.getEpochSecond())
                begin = t.getTimestamp();
            if(t.getTimestamp().getEpochSecond() > end.getEpochSecond())
                end = t.getTimestamp();
        }
        Timespan span = new Timespan(begin, end);
        return span;
        
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        //throw new RuntimeException("not implemented");
        
        Set<String> mentionedUsers = new HashSet<String>();
        
        for(Tweet t : tweets)
        {
            String[] strs = t.getText().split("\\s+");
            
            for(String str : strs)
            {
                /*misunderstanding for this method*/
                /*
                if(str.equalsIgnoreCase("@" + t.getAuthor()))
                {
                    mentionedUsers.add(t.getAuthor());
                    break;
                }
                */
                /*adjust logic for judge*/
                /*if(str.startsWith("@"))
                {
                    mentionedUsers.add(str.substring(1));
                }
                */
                /*new version*/
                if(str.startsWith("@"))
                {
                    String substr = str.substring(1);
                    int flag = 1;
                    for(String mentionedUsers_str : mentionedUsers)
                    {
                        if(mentionedUsers_str.equalsIgnoreCase(substr))
                        {
                            flag = 0;
                            break;
                        }  
                    }
                    if(flag == 1)
                        mentionedUsers.add(substr);
                }
                
            }
        }
        
        return mentionedUsers;
        
    }

}
