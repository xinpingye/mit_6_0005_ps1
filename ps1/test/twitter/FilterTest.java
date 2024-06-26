/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    //test strategy for each operation of 
    //
    //writtenBy():
    //list len = 0, 1, n
    //username nums in input tweets = 0, 1, n
    //same order
    //
    //inTimespan():
    //result nums = 0, 1, 2
    //time begin > | < | =  == input time begin
    //time end   > | < | =  == input time end
    //same order
    //
    //RuntimeException():
    //len words = n
    //len tweets = n
    //results = 0, n
    //case-senstative 
    //same order
    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T08:00:00Z");
    private static final Instant d5 = Instant.parse("2016-02-17T13:00:00Z");
    private static final Instant d6 = Instant.parse("2016-02-17T13:00:00Z");
    private static final Instant d7 = Instant.parse("2016-02-17T13:00:00Z");
    private static final Instant d8 = Instant.parse("2016-02-17T13:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "rivest talk in 30 minutes #hype", d3);
    private static final Tweet tweet4 = new Tweet(4, "alyssa", "rivest talk in 30 minutes #hype", d4);
    private static final Tweet tweet5 = new Tweet(5, "alyssa", "rivest talk in 30 minutes #hype", d5);
    private static final Tweet tweet6 = new Tweet(6, "alyssa", "rivest  in 30 minutes #hype", d6);
    private static final Tweet tweet7 = new Tweet(7, "alyssa", "rivest  TALK in 30 minutes #hype", d7);
    private static final Tweet tweet8 = new Tweet(8, "alyssa", "yxp here , welcome you talk Talk", d8);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testWrittenByMultipleTweetsOneElementSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    @Test
    public void testWrittenByMultipleTweetsTwoElementSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    @Test
    public void testWrittenByMultipleTweetsNoResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "cacy");
        
        assertEquals("expected singleton list", 0, writtenBy.size());
    }
    
    @Test
    public void testWrittenByMultipleTweetsTwoResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "alyssa");
        
        assertEquals("expected singleton list", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet3));
    }
    
    @Test
    public void testWrittenByMultipleTweetsTwoResultSameOrder() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "alyssa");
        
        assertEquals("expected singleton list", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet3));
        assertEquals("expect same oder", writtenBy.get(0), tweet1);
        assertEquals("expect same oder", writtenBy.get(1), tweet3);
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    @Test
    public void testInTimespanMultipleTweetsOneResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet4, tweet5), new Timespan(testStart, testEnd));
        
        assertEquals("expected only one", 1, inTimespan.size());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1)));
    }
    
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }

    @Test
    public void testContaininCaseSentativeAndNoContainTweet() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet6, tweet7), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    @Test
    public void testContaininCaseSentativeAndNoContainTweetAndManyWords() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet8), Arrays.asList("talk", "yxp"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2, tweet8)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
        assertEquals("expected same order", 1, containing.indexOf(tweet2));
    }
    
    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
