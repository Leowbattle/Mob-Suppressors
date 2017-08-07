package com.leo.mobsuppressors;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

public enum EnumSuppressorFuel {
	ENDER_PEARLS(Items.ENDER_PEARL),
	GUNPOWDER(Items.GUNPOWDER),
	FLESH(Items.ROTTEN_FLESH);
	
	Item itemType;
	
	EnumSuppressorFuel(Item item) {
		this.itemType = item;
	}
	
	public Item getItemType() {
		return itemType;
	}
}
