package com.gildedrose;

public class SulfuraUploader implements Updatable {

	@Override
	public void update(Item item) {
		item.quality = 80;
	}
}
