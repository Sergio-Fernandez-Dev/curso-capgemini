package com.gildedrose;

import java.lang.invoke.VarHandle;
import java.util.HashMap;
import java.util.Map;

class GildedRose {
    Item[] items;
    Map<String, Updatable> updaterMap;

    public GildedRose(Item[] items) {
        this.items = items;
        this.updaterMap = new HashMap<String, Updatable>();
        this.updaterMap.put("Aged Brie", new AgedBrieUpdater());
        this.updaterMap.put("Backstage passes to a TAFKAL80ETC concert", new BackstageEntryUpdater());
        this.updaterMap.put("Sulfuras, Hand of Ragnaros", new SulfuraUpdater());
        this.updaterMap.put("Spelt", new SpeltItemUpdater());
    }

    public void updateQuality() {
    	for (Item item : items) {
    		Updatable updatableItem = updaterMap.getOrDefault(item.name, new GeneralItemUpdater());
    		updatableItem.update(item);
    	}
    }
    
}
