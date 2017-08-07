package com.leo.mobsuppressors.blocks;

import com.leo.mobsuppressors.CreativeTab;
import com.leo.mobsuppressors.tileentity.TileEntitySuppressionAltarCore;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSuppressionAltarCore extends Block implements ITileEntityProvider {

	public BlockSuppressionAltarCore(String unlocalisedName, String registryName) {
		super(Material.ROCK);
		this.setHardness(2);
		
		this.setUnlocalizedName(unlocalisedName);
		this.setRegistryName(registryName);
		
		this.setCreativeTab(CreativeTab.mobSuppressorCreativeTab);
	}
	
	public BlockSuppressionAltarCore(Material materialIn) {
		super(materialIn);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySuppressionAltarCore();
	}

}
