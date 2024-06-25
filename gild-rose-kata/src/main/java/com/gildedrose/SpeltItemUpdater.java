package com.gildedrose;

public class SpeltItemUpdater extends GeneralItemUpdater implements Updatable{
	private final int MIN_QUALITY= 0;
	
	@Override
	public void update(Item item) {
		item.sellIn--;
		if (item.quality > MIN_QUALITY)
			item.quality -= 2;
		
		if (item.sellIn <= 0 && item.quality > MIN_QUALITY)
			item.quality -= 2;
		
		if (item.quality < MIN_QUALITY)
			item.quality = MIN_QUALITY;
	}
}
