package com.gildedrose;

public class AgedBrieUpdater implements Updatable{
	private final int MAX_QUALITY= 50;
	
	@Override
	public void update(Item item) {
		item.sellIn--;
		if (item.quality < MAX_QUALITY)
			item.quality++;

		if (item.sellIn <= 0 && item.quality < MAX_QUALITY)
			item.quality++;
	}

	
	
	
}
