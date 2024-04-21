/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {
    //partition strategy for class SocialNetWork
    //
    //guessFollowsGraph():
    //main member
    //Tweet.author
    //Tweet.text
    //relation = Tweet.author in Tweet.text  || Tweet.author not in Tweet.text
    //A in B.text and B in A.text
    //A in B.text and B not in A.text
    //A in B.text and B.text do not have @-mention
    //A in B.text and B not in list
    //not case sensitive
    //appear at most once as a key in the map or in any given map[A] set.
    //different tweet same author
    
    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "het", "@cacy @dert @cacy @GEAR @bob 1@eee.com", d1);
    private static final Tweet tweet2 = new Tweet(2, "cacy", "@het @dert @GEAR @gear @bob 1@eee.com", d1);
    private static final Tweet tweet3 = new Tweet(3, "dert", "@dert @GEAR @bob 1@eee.com", d1);
    private static final Tweet tweet4 = new Tweet(3, "gear", "aabbcc", d1);
    private static final Tweet tweet5 = new Tweet(3, "het", "@yxp 1@ccc", d1);
    
    private boolean judge_exist(Map<String, Set<String>> followsGraph, String key, String to_find)
    {
        Set<String> strs = followsGraph.get(key);
        
        if(strs == null || strs.size() == 0)
            return false;
        
        for(String str : strs)
        {
            if(str.equalsIgnoreCase(to_find))
                return true;
        }
        
        return false;
    }
    
    /*
     * @requires key is in map and map[key] is not empty
     */
    private int judge_exist_appear_nums(Map<String, Set<String>> followsGraph, String key, String to_find)
    {
        Set<String> strs = followsGraph.get(key);
        
        int nums = 0;
        
        for(String str : strs)
        {
            if(str.equalsIgnoreCase(to_find))
                nums++;
        }
        
        return nums;
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testGuessFollowsGraph1() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));
        
        assertFalse("expected empty graph", followsGraph.isEmpty());
        assertTrue("expected het followed cacy", judge_exist(followsGraph, "het", "cacy"));
        assertTrue("expected het followed dert", judge_exist(followsGraph, "het", "dert"));
        assertTrue("expected het followed GEAR", judge_exist(followsGraph, "het", "GEAR"));
        assertTrue("expected het followed bob", judge_exist(followsGraph,  "het", "bob"));
        assertTrue("expected het followed yxp", judge_exist(followsGraph,  "het", "yxp"));
        
        assertTrue("expected cacy followed het", judge_exist(followsGraph,  "cacy", "het"));
        assertTrue("expected cacy followed dert", judge_exist(followsGraph, "cacy", "dert"));
        assertTrue("expected cacy followed GEAR", judge_exist(followsGraph, "cacy", "GEAR"));
        assertTrue("expected cacy followed bob", judge_exist(followsGraph,  "cacy", "bob"));
        
        assertFalse("expected dert do not followed dert", judge_exist(followsGraph, "dert", "dert"));
        assertTrue("expected dert followed GEAR", judge_exist(followsGraph, "dert", "GEAR"));
        assertTrue("expected dert followed bob", judge_exist(followsGraph, "dert", "bob"));
        
        assertEquals("expected cacy appear one in het", 1, judge_exist_appear_nums(followsGraph, "het", "cacy"));
        assertEquals("expected GEAR appear one in cacy", 1, judge_exist_appear_nums(followsGraph, "cacy", "GEAR"));
        
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
