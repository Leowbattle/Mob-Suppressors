package com.leo.mobsuppressors.blocks;

import com.leo.mobsuppressors.CreativeTab;
import com.leo.mobsuppressors.EnumMobSuppressorType;
import com.leo.mobsuppressors.MobSuppressors;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Block enderSuppressor;
	public static Block creeperSuppressor;
	public static Block zombieSuppressor;
	
	public static Item enderSuppressorItemBlock;
	public static Item creeperSuppressorItemBlock;
	public static Item zombieSuppressorItemBlock;
	
	public static Block suppressionPedestal;
	public static Block suppressionTower;
	public static Block suppressionAltarCore;
	
	public static Block fleshBlock;
	public static Block enderPearlBlock;
	
	public static void createBlocks() {
		enderSuppressor = new BlockMobSuppressor("endersuppressor", "endersuppressor", EnumMobSuppressorType.ENDER);
		creeperSuppressor = new BlockMobSuppressor("creepersuppressor", "creepersuppressor", EnumMobSuppressorType.CREEPER);
		zombieSuppressor = new BlockMobSuppressor("zombiesuppressor", "zombiesuppressor", EnumMobSuppressorType.ZOMBIE);
		
		enderSuppressorItemBlock = new ItemBlock(enderSuppressor).setRegistryName("endersuppressor");
		creeperSuppressorItemBlock = new ItemBlock(creeperSuppressor).setRegistryName("creepersuppressor");
		zombieSuppressorItemBlock = new ItemBlock(zombieSuppressor).setRegistryName("zombiesuppressor");
		
		suppressionPedestal = new BlockSuppressionPedestal("suppressionpedestal", "suppressionpedestal");
		suppressionTower = new BlockSuppressionTower("suppressiontower", "suppressiontower");
		suppressionAltarCore = new BlockSuppressionAltarCore("suppressionaltarcore", "suppressionaltarcore");
		
		fleshBlock = new Block(Material.CACTUS).setUnlocalizedName("fleshblock").setRegistryName("fleshblock").setCreativeTab(CreativeTab.mobSuppressorCreativeTab);
		enderPearlBlock = new Block(Material.ROCK).setUnlocalizedName("enderpearlblock").setRegistryName("enderpearlblock").setCreativeTab(CreativeTab.mobSuppressorCreativeTab);
	}
	
	public static void init() {
		createBlocks();
		GameRegistry.register(enderSuppressor);
		GameRegistry.register(enderSuppressorItemBlock);
		
		GameRegistry.register(creeperSuppressor);
		GameRegistry.register(creeperSuppressorItemBlock);
		
		GameRegistry.register(zombieSuppressor);
		GameRegistry.register(zombieSuppressorItemBlock);
		
		GameRegistry.register(suppressionPedestal);
		GameRegistry.register(new ItemBlock(suppressionPedestal).setRegistryName("suppressionpedestal"));
		
		GameRegistry.register(suppressionTower);
		GameRegistry.register(new ItemBlock(suppressionTower).setRegistryName("suppressiontower"));
		
		GameRegistry.register(suppressionAltarCore);
		GameRegistry.register(new ItemBlock(suppressionAltarCore).setRegistryName("suppressionaltarcore"));
		
		GameRegistry.register(fleshBlock);
		GameRegistry.register(new ItemBlock(fleshBlock).setRegistryName("fleshblock"));
		
		GameRegistry.register(enderPearlBlock);
		GameRegistry.register(new ItemBlock(enderPearlBlock).setRegistryName("enderpearlblock"));
		
		GameRegistry.addShapedRecipe(new ItemStack(suppressionPedestal), ".S.", ".S.", "SSS", 'S', Blocks.STONE);
		GameRegistry.addShapedRecipe(new ItemStack(suppressionTower), ".S.", "SPS", ".S.", 'S', Blocks.STONE, 'P', suppressionPedestal);
		GameRegistry.addShapedRecipe(new ItemStack(suppressionAltarCore, 1),  "P.P", "PPP", "PPP", 'P', suppressionPedestal);
		
		GameRegistry.addShapedRecipe(new ItemStack(fleshBlock, 1), "FFF", "FFF", "FFF", 'F', Items.ROTTEN_FLESH);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.ROTTEN_FLESH, 9), new ItemStack(fleshBlock, 1));
		
		GameRegistry.addShapedRecipe(new ItemStack(enderPearlBlock, 1), "PP.", "PP.", "...", 'P', Items.ENDER_PEARL);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.ENDER_PEARL, 4), new ItemStack(enderPearlBlock, 1));
	}
	
	public static void registerRenderers() {
		registerRenderer(enderSuppressor);
		registerRenderer(creeperSuppressor);
		registerRenderer(zombieSuppressor);
		
		registerRenderer(suppressionPedestal);
		registerRenderer(suppressionTower);
		registerRenderer(suppressionAltarCore);
		
		registerRenderer(fleshBlock);
		registerRenderer(enderPearlBlock);
	}
	
	public static void registerRenderer(Block block) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(MobSuppressors.modid + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
}
