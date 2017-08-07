package com.leo.mobsuppressors.blocks;

import com.leo.mobsuppressors.CreativeTab;
import com.leo.mobsuppressors.EnumMobSuppressorType;
import com.leo.mobsuppressors.MobSuppressors;
import com.leo.mobsuppressors.gui.ModGUIHandler;
import com.leo.mobsuppressors.tileentity.TileEntityMobSuppressor;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockMobSuppressor extends Block implements ITileEntityProvider {
	public static final PropertyBool is_powered = PropertyBool.create("is_powered");
	
	public EnumMobSuppressorType type;
	
	protected BlockMobSuppressor(String unlocalizedName, String registryName, EnumMobSuppressorType type) {
		super(Material.IRON);
		this.setCreativeTab(CreativeTab.mobSuppressorCreativeTab);
		
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
		
		this.setHardness(2);
		this.setResistance(6000f);
		this.setHarvestLevel("pickaxe", 3);
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(is_powered, false));
		
		this.type = type;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, is_powered);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(is_powered, meta == 0);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(is_powered) ? 1: 0;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMobSuppressor(this, type);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		super.breakBlock(world, pos, state);
		world.removeTileEntity(pos);
		TileEntityMobSuppressor tile = (TileEntityMobSuppressor)world.getTileEntity(pos);
		IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		ItemStack stack = itemHandler.getStackInSlot(0);
		if (!stack.isEmpty()) {
			EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			world.spawnEntity(item);
		}
	}

	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.openGui(MobSuppressors.instance, ModGUIHandler.enderSuppressorGUIID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}	
}
