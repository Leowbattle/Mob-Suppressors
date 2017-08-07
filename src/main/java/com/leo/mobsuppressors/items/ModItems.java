package com.leo.mobsuppressors.items;

import com.leo.mobsuppressors.CreativeTab;
import com.leo.mobsuppressors.MobSuppressors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	public static Item enderRod;
	public static Item dynamite;
	public static ItemFood sausage;
	
	public static void init() {
		enderRod = new Item().setUnlocalizedName("enderrod").setRegistryName("enderrod").setCreativeTab(CreativeTab.mobSuppressorCreativeTab);
		dynamite = new Item().setUnlocalizedName("dynamite").setRegistryName("dynamite").setCreativeTab(CreativeTab.mobSuppressorCreativeTab);
		sausage = (ItemFood) new ItemFood(3, false).setUnlocalizedName("sausage").setRegistryName("sausage").setCreativeTab(CreativeTab.mobSuppressorCreativeTab);
		
		register();
		
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.enderRod), "..P", ".B.", "P..", 'P', Items.ENDER_PEARL, 'B', Items.BLAZE_ROD);
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.enderRod), "P..", ".B.", "..P", 'P', Items.ENDER_PEARL, 'B', Items.BLAZE_ROD);
		
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.dynamite), "..S", ".G.", "C..", 'S', Items.STRING, 'G', Items.GUNPOWDER, 'C', Items.CLAY_BALL);
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.dynamite), "S..", ".G.", "..C", 'S', Items.STRING, 'G', Items.GUNPOWDER, 'C', Items.CLAY_BALL);
		
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.sausage), "F..", ".F.", "..F", 'F', Items.ROTTEN_FLESH);
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.sausage), "..F", ".F.", "F..", 'F', Items.ROTTEN_FLESH);
	}
	
	public static void register() {
		GameRegistry.register(enderRod);
		GameRegistry.register(dynamite);
		GameRegistry.register(sausage);
		
		registerRenderer(enderRod);
		registerRenderer(dynamite);
		registerRenderer(sausage);
	}
	
	private static void registerRenderer(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(MobSuppressors.modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
