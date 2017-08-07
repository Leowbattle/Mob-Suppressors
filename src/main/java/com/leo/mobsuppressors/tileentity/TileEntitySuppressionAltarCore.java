package com.leo.mobsuppressors.tileentity;

import com.leo.mobsuppressors.EnumAltarRecipes;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntitySuppressionAltarCore extends TileEntity implements ITickable {
	public TileEntitySuppressionAltarCore() {
		
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			attemptCraft();
		}
	}
	
	public boolean attemptCraft() {
		if (!(world.getTileEntity(new BlockPos(getPos().getX(), getPos().getY() + 1, getPos().getZ())) instanceof TileEntitySuppressionTower)) {
			return false;
		}
		
		TileEntitySuppressionTower tower;
		//Check structure
		BlockPos[] positions = {
			//Edges
			new BlockPos(getPos().getX() + 1, getPos().getY(), getPos().getZ()), 
			new BlockPos(getPos().getX(), getPos().getY(), getPos().getZ() + 1 ), 
			new BlockPos(getPos().getX() -1, getPos().getY(), getPos().getZ()), 
			new BlockPos(getPos().getX(), getPos().getY(), getPos().getZ() - 1), 
			//Corners
			new BlockPos(getPos().getX() + 1, getPos().getY(), getPos().getZ() + 1), 
			new BlockPos(getPos().getX() + 1, getPos().getY(), getPos().getZ() - 1), 
			new BlockPos(getPos().getX() - 1, getPos().getY(), getPos().getZ() - 1), 
			new BlockPos(getPos().getX() - 1, getPos().getY(), getPos().getZ() + 1)
		};
		
		tower = (TileEntitySuppressionTower)world.getTileEntity(new BlockPos(getPos().getX(), getPos().getY() + 1, getPos().getZ()));
		
		for (BlockPos position: positions) {
			if (!(world.getTileEntity(position) instanceof TileEntitySuppressionPedestal)) {
				return false;
			}
		}
		
		for (EnumAltarRecipes recipe: EnumAltarRecipes.values()) {
			if (tower.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).getStackInSlot(0).getItem() == recipe.inputs[4]) {
				//Check edges
				if (((TileEntitySuppressionPedestal)world.getTileEntity(positions[0])).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).getStackInSlot(0).getItem() == recipe.inputs[1] && ((TileEntitySuppressionPedestal)world.getTileEntity(positions[1])).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).getStackInSlot(0).getItem() == recipe.inputs[1] && ((TileEntitySuppressionPedestal)world.getTileEntity(positions[2])).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).getStackInSlot(0).getItem() == recipe.inputs[1] && ((TileEntitySuppressionPedestal)world.getTileEntity(positions[3])).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).getStackInSlot(0).getItem() == recipe.inputs[1]) {
					//Check corners
					if (((TileEntitySuppressionPedestal)world.getTileEntity(positions[4])).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).getStackInSlot(0).getItem() == recipe.inputs[0] && ((TileEntitySuppressionPedestal)world.getTileEntity(positions[5])).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).getStackInSlot(0).getItem() == recipe.inputs[0] && ((TileEntitySuppressionPedestal)world.getTileEntity(positions[6])).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).getStackInSlot(0).getItem() == recipe.inputs[0] && ((TileEntitySuppressionPedestal)world.getTileEntity(positions[7])).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).getStackInSlot(0).getItem() == recipe.inputs[0]) {
						if (tower.netherStarUsesLeft > 0) {
							tower.netherStarUsesLeft -= 1;
							
							tower.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).extractItem(0, 1, false);
							world.spawnEntity(new EntityItem(world, getPos().getX(), getPos().getY() + 1, getPos().getZ(), new ItemStack(recipe.output, 1)));
							
							//MobSuppressors.network.sendToAllAround(new PacketAltarCraft(tower, ItemStack.EMPTY), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
							//MobSuppressors.network.sendToAllAround(new PacketAltarCraft(tower, new ItemStack(recipe.output, 1)), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
						
							world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false));
						
							for (BlockPos position: positions) {
								((TileEntitySuppressionPedestal)world.getTileEntity(position)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).extractItem(0, 1, false);
							}
						}
					}
				}
			}
		}
		
		return true;
	}
}
