package com.gildedrose;

public class BackstageEntryUpdater implements Updatable{
	private final int MIN_QUALITY= 0;
	private final int MAX_QUALITY= 50;
	
	@Override
	public void update(Item item) {
		item.sellIn--;
		
		if (item.sellIn >= 0 && item.quality < MAX_QUALITY)
			item.quality++;
		
		if (item.sellIn <= 10 && item.quality < MAX_QUALITY)
			item.quality++;
	
		if (item.sellIn <= 5 && item.quality < MAX_QUALITY)
			item.quality++;
		
		if (item.sellIn < 0 )
			item.quality = MIN_QUALITY;
	}
}
