package com.leo.mobsuppressors;

import com.leo.mobsuppressors.blocks.ModBlocks;
import com.leo.mobsuppressors.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public enum EnumAltarRecipes {
	ENDER(ModBlocks.enderSuppressorItemBlock, new Item[] {Item.getItemFromBlock(Blocks.OBSIDIAN), ModItems.enderRod, Item.getItemFromBlock(Blocks.OBSIDIAN), ModItems.enderRod, Items.ENDER_EYE, ModItems.enderRod, Item.getItemFromBlock(Blocks.OBSIDIAN), ModItems.enderRod, Item.getItemFromBlock(Blocks.OBSIDIAN)}),
	CREEPER(ModBlocks.creeperSuppressorItemBlock, new Item[] {Item.getItemFromBlock(Blocks.STONE), ModItems.dynamite, Item.getItemFromBlock(Blocks.STONE), ModItems.dynamite, Items.SKULL, ModItems.dynamite, Item.getItemFromBlock(Blocks.STONE), ModItems.dynamite, Item.getItemFromBlock(Blocks.STONE)}),
	ZOMBIE(ModBlocks.zombieSuppressorItemBlock, new Item[] {Item.getItemFromBlock(ModBlocks.fleshBlock), ModItems.sausage, Item.getItemFromBlock(ModBlocks.fleshBlock), ModItems.sausage, Items.SKULL, ModItems.sausage, Item.getItemFromBlock(ModBlocks.fleshBlock), ModItems.sausage, Item.getItemFromBlock(ModBlocks.fleshBlock)});
	
	public Item output;
	public Item[] inputs;
	
	EnumAltarRecipes(Item output, Item[] inputs) {
		this.output = output;
		this.inputs = inputs;
	}
}
