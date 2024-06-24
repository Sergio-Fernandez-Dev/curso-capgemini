package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

class GildedRoseTest {

    
    @Nested
    class GlobalBehaviour {
    	@ParameterizedTest
    	@DisplayName("Test if quality is always positive")
		@CsvSource(value = {"General, -3, 0", "Aged Brie, 1, 0", "Backstage passes to a TAFKAL80ETC concert, 0, 0", "'Sulfuras, Hand of Ragnaros', 5,0"})
    	void testQualityIsNotNegative(String name, int sellIn, int quality) {
    		Item[] items = new Item[] { new Item(name, sellIn, quality) };
    		GildedRose app = new GildedRose(items);
    		
    		app.updateQuality();
    		
    		assertTrue(items[0].quality >= 0);		
    	}
    	
    	@ParameterizedTest
    	@DisplayName("Test if quality always is <= 50")
    	@CsvSource(value = {"Aged Brie, -1, 50", "Backstage passes to a TAFKAL80ETC concert, 4, 50"})
    	void testQualityIsNotOverFifty(String name, int sellIn, int quality) {
    		Item[] items = new Item[] { new Item(name, sellIn, quality) };
    		GildedRose app = new GildedRose(items);
    		
    		app.updateQuality();
    		
    		assertTrue(items[0].quality <= 50);		
    		}
    }
    
    @Nested
    class GeneralItem {
    	
    	@ParameterizedTest
    	@DisplayName("Test if sellIn and quality is correctly updated")
    	@CsvSource(value = {"7,5,6,4", "1,0,0,0", "-1,7,-2,5", "0,1,-1,0"})
    	void testDailyUpdate(int sellIn, int qualityIn, int sellOut, int qualityOut) {
    		Item[] items = new Item[] { new Item("General", sellIn, qualityIn) };
    		GildedRose app = new GildedRose(items);
    		
    		app.updateQuality();
    		
    		assertAll("Single downgrade", () -> assertEquals(sellOut, items[0].sellIn), 
    				() -> assertEquals(qualityOut, items[0].quality));
    	}  	
    }
    
    @Nested
    class AgedBrieItem {
    	@ParameterizedTest
    	@DisplayName("Test if sellIn and quality is correctly updated")
    	@CsvSource(value = {
    			"7,5,6,6", 
    			"1,50,0,50", 
    			"-1,48,-2,50", 
    			"-1,49,-2,50", 
    			"0,50,-1,50"
    	})
    	void testDailyUpdate(int sellIn, int qualityIn, int sellOut, int qualityOut) {
    		Item[] items = new Item[] { new Item("Aged Brie", sellIn, qualityIn) };
    		GildedRose app = new GildedRose(items);
    		
    		app.updateQuality();
    		
    		assertAll("Single downgrade", () -> assertEquals(sellOut, items[0].sellIn), 
    				() -> assertEquals(qualityOut, items[0].quality));
    	} 
    }
    
    @Nested
    class SulfurasItem {
    	@ParameterizedTest
    	@DisplayName("Test if sellIn and quality remains unchanged")
    	@CsvSource(value = {"7,80,7,80"})
    	void testDailyUpdate(int sellIn, int qualityIn, int sellOut, int qualityOut) {
    		Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", sellIn, qualityIn) };
    		GildedRose app = new GildedRose(items);
    		
    		app.updateQuality();
    		
    		assertAll("Sulfuras unchanged", () -> assertEquals(sellOut, items[0].sellIn), 
    				() -> assertEquals(qualityOut, items[0].quality));
    	} 
    }
    
    @Nested
    class BackstageEntry {
    	@ParameterizedTest
    	@DisplayName("Test if sellIn and quality is correctly updated")
    	@CsvSource(value = {
    			"3,47,2,50", 
    			"1,50,0,50", 
    			"-1,48,-2,0",
    			"0,50,-1,0",
    			"10,49,9,50",
    			"1,48,0,50",
    			"1,48,0,50"
    	})
    	void testDailyUpdate(int sellIn, int qualityIn, int sellOut, int qualityOut) {
    		Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, qualityIn) };
    		GildedRose app = new GildedRose(items);
    		
    		app.updateQuality();
    		
    		assertAll("Daily update", () -> assertEquals(sellOut, items[0].sellIn), 
    				() -> assertEquals(qualityOut, items[0].quality));
    	} 
    }
    
    @Nested
    class SpeltItem {
    	@ParameterizedTest
    	@DisplayName("Test if sellIn and quality is correctly updated")
    	@CsvSource(value = {"7,5,6,3", "1,0,0,0", "-1,7,-2,3", "0,1,-1,0"})
    	void testDailyUpdate(int sellIn, int qualityIn, int sellOut, int qualityOut) {
    		Item[] items = new Item[] { new Item("Spelt", sellIn, qualityIn) };
    		GildedRose app = new GildedRose(items);
    		
    		app.updateQuality();
    		
    		assertAll("Spelt update", () -> assertEquals(sellOut, items[0].sellIn), 
    				() -> assertEquals(qualityOut, items[0].quality));
    	}
    }
}
