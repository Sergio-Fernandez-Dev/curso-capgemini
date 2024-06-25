package com.gildedrose;

public class GeneralItemUploader implements Updatable{
	private final int MIN_QUALITY= 0;

	@Override
	public void update(Item item) {
		item.sellIn--;
		if (item.quality > MIN_QUALITY)
			item.quality--;
		
		if (item.sellIn <= 0 && item.quality > MIN_QUALITY)
			item.quality--;
	}
}
