package com.leo.mobsuppressors;

import com.leo.mobsuppressors.blocks.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTab {
	public static final CreativeTabs mobSuppressorCreativeTab = new CreativeTabs("mobsuppressors") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModBlocks.enderSuppressor);
		}
	};
}
