package com.leo.mobsuppressors.blocks;

import com.leo.mobsuppressors.CreativeTab;
import com.leo.mobsuppressors.tileentity.TileEntitySuppressionPedestal;
import com.leo.mobsuppressors.tileentity.TileEntitySuppressionTower;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockSuppressionPedestal extends Block implements ITileEntityProvider {
	public BlockSuppressionPedestal(String unlocalizedName, String registryName) {
		super(Material.ROCK);
		this.setHardness(2);
		
		this.setCreativeTab(CreativeTab.mobSuppressorCreativeTab);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
	}
	
	public BlockSuppressionPedestal(Material materialIn) {
		super(materialIn);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullyOpaque(IBlockState state) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySuppressionPedestal();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		super.breakBlock(world, pos, state);
		world.removeTileEntity(pos);
		TileEntitySuppressionPedestal tile = (TileEntitySuppressionPedestal)world.getTileEntity(pos);
		IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		ItemStack stack = itemHandler.getStackInSlot(0);
		if (!stack.isEmpty()) {
			EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			world.spawnEntity(item);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntitySuppressionPedestal TESP = (TileEntitySuppressionPedestal)world.getTileEntity(pos);
			IItemHandler itemHandler = TESP.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
			
			if (player.getHeldItem(hand).isEmpty()) {
				player.setHeldItem(hand, itemHandler.extractItem(0, 64, false));
				TESP.markDirty();
			} else {
				player.setHeldItem(hand, itemHandler.insertItem(0, player.getHeldItem(hand), false));
				TESP.markDirty();
			}
		}
		
		return true;
	}
}
