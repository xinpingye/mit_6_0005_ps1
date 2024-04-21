/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.Iterator;

import org.junit.Test;

public class ExtractTest {
    
    //test strategy for each operation of Extract
    //
    //getTimespan():
    //list len =  1, n // maybe no need 0
    //tweet = produced by Tweet construct
    //
    //getMentionedUsers
    //list len = 0, 1, n
    //username-mention nums = 0, 1, n
    //similar username-mention word nums = 0, 1
    //same username-mention occurs nums = 1, n
    //cover each partitions
    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T13:00:00Z");
    private static final Instant d5 = Instant.parse("2016-02-17T14:00:00Z");
    private static final Instant d6 = Instant.parse("2016-02-17T15:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "cacy", "rivest talk @cacy in 30 minutes #hype", d3);
    private static final Tweet tweet4 = new Tweet(4, "dert", "rivest talk @cacy in 30 minutes #hype", d4);
    private static final Tweet tweet5 = new Tweet(5, "eye", "rivest talk 1@eye1 in 30 minutes #hype", d5);
    private static final Tweet tweet6 = new Tweet(5, "fly", "rivest talk @fly @fly in 30 @fly minutes #hype", d6);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan3 = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan3.getStart());
        assertEquals("expected end", d2, timespan3.getEnd());
    }
    
    @Test
    public void testGetTimespanOneTweets() {
        Timespan timespan2 = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start", d1, timespan2.getStart());
        assertEquals("expected end", d1, timespan2.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersOneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        Iterator<String> it = mentionedUsers.iterator();
        
        assertEquals("expected same size", 1, mentionedUsers.size());
        assertEquals("expedted same name", tweet3.getAuthor(), it.next());   
    }

    @Test
    public void testGetMentionedUsersMisMatchMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4));
        
        assertEquals("expected no username-mention", 0, mentionedUsers.size());   
    }
    
    @Test
    public void testGetMentionedUsersWrongMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
        
        assertEquals("expected no username-mention", 0, mentionedUsers.size()); 
    }
    
    @Test
    public void testGetMentionedUsersOneMentionMultipleAppear() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
        Iterator<String> it = mentionedUsers.iterator();
        
        assertEquals("expected only one", 1, mentionedUsers.size());
        assertEquals("expedted same name", tweet6.getAuthor(), it.next());   
    }
    
    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
