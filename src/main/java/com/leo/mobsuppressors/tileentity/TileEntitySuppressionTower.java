package com.leo.mobsuppressors.tileentity;

import javax.annotation.Nullable;

import com.leo.mobsuppressors.MobSuppressors;
import com.leo.mobsuppressors.network.PacketRequestUpdateTower;
import com.leo.mobsuppressors.network.PacketUpdateTower;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntitySuppressionTower extends TileEntity {
	public ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
		@Override
		protected void onContentsChanged(int slot) {
			if (!world.isRemote) {
				lastChangeTime = world.getTotalWorldTime();
				MobSuppressors.network.sendToAllAround(new PacketUpdateTower(TileEntitySuppressionTower.this), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
			}
		}
	};
	
	public int netherStarUsesLeft;
	
	public long lastChangeTime;
	
	public TileEntitySuppressionTower() {
		this.netherStarUsesLeft = 0;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", itemStackHandler.serializeNBT());
		compound.setLong("lastChangeTime", lastChangeTime);
		compound.setInteger("netherStarUsesLeft", netherStarUsesLeft);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		itemStackHandler.deserializeNBT(compound.getCompoundTag("inventory"));
		lastChangeTime = compound.getLong("lastChangeTime");
		netherStarUsesLeft = compound.getInteger("netherStarUsesLeft");
		super.readFromNBT(compound);
	}
	
	@Override
	public void onLoad() {
		if (world.isRemote) {
			MobSuppressors.network.sendToServer(new PacketRequestUpdateTower(this));
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)itemStackHandler : super.getCapability(capability, facing);
	}
}