package com.gildedrose;

public class SulfuraUpdater implements Updatable {

	@Override
	public void update(Item item) {
		item.quality = 80;
	}
}
