package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("fixme", app.items[0].name);
    }
    
    @Nested
    class GlobalBehaviour {
    	@Test
    	@DisplayName("Test if quality is always positive")
    	void testQualityIsNotNegative() {}
    	
    }
    
    @Nested
    class GeneralItem {
    	@Test
    	@DisplayName("Test if sellIn and quality is downgraded 1 step")
    	void testDailyUpdate() {}
    	
    	@Test
    	@DisplayName("Test if quality is downgraded 2 steps when outdated")
    	void testDoubleQualityUpdate() {}
    	    
    	@Test
    	@DisplayName("Test if quality always is <= 50")
    	void testQualityIsNotOverFifty() {}
    }
    
    @Nested
    class AgedBrieItem {
    	@Test
    	@DisplayName("Test if sellIn is downgraded 1 step and quality is upgraded 1 step")
    	void testDailyUpdate() {}
    	
    	@Test
    	@DisplayName("Test if quality is upgraded 2 step when is outdated")
    	void testQualityUpdateWhenIsOutdated() {}
    	    
    	@Test
    	void testDoubleDowngrade() {}
    	    
    	@Test
    	void testQualityIsNotNegative() {}
    	    
    	@Test
    	void testQualityIsNotOverFifty() {}
    }
    

}
