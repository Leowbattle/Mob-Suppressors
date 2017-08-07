package com.leo.mobsuppressors.tileentity;

import java.util.Random;

import com.leo.mobsuppressors.EnumMobSuppressorType;
import com.leo.mobsuppressors.EnumSuppressorFuel;
import com.leo.mobsuppressors.blocks.BlockMobSuppressor;

import jline.internal.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMobSuppressor extends TileEntity implements ITickable {
	public static final int maxTicks = 900;
	
	public BlockMobSuppressor block;
	public boolean isPublic;
	public int powerTicksLeft = 0;
	public Random RNG = new Random();
	
	public final EnumMobSuppressorType type;
	public EnumSuppressorFuel fuel;
	
	public ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
		@Override
		protected void onContentsChanged(int slot) {
			markDirty();
		}
	};
	
	public TileEntityMobSuppressor(BlockMobSuppressor block, EnumMobSuppressorType type) {
		this.block = block;
		this.isPublic = true;
		this.type = type;
		
		switch (type) {
		case ENDER:
			fuel = EnumSuppressorFuel.ENDER_PEARLS;
			break;
			
		case CREEPER:
			fuel = EnumSuppressorFuel.GUNPOWDER;
			break;
			
		case ZOMBIE:
			fuel = EnumSuppressorFuel.FLESH;
			break;
		default:
			fuel = EnumSuppressorFuel.ENDER_PEARLS;
			break;
		}
	}
	
	@Override
	public void update() {
		if (!(world.getBlockState(getPos()).getBlock() instanceof BlockMobSuppressor)) {
			return;
		}
	
		EnumParticleTypes particleType;
		
		switch (type) {
		case ENDER:
			particleType = EnumParticleTypes.PORTAL;
			break;
		case CREEPER:
			particleType = EnumParticleTypes.EXPLOSION_NORMAL;
			break;
		case ZOMBIE:
			particleType = EnumParticleTypes.CRIT_MAGIC;
			break;
		default:
			particleType = EnumParticleTypes.CRIT;
			break;
		}
		
		world.spawnParticle(particleType, RNG.nextFloat() + pos.getX(), RNG.nextFloat() + pos.getY(), RNG.nextFloat() + pos.getZ(), 0.1, 0.1, 0.1, 0);
		
		if (!world.isRemote) {
			updateInventory();
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState == newState;
	}
	
	private void updateInventory() {
		if (powerTicksLeft > 0) {
			powerTicksLeft--;
		} else {
			if (itemStackHandler.getStackInSlot(0).getCount() > 0 && itemStackHandler.getStackInSlot(0).getItem() == fuel.getItemType()) {
				itemStackHandler.getStackInSlot(0).shrink(1);
				setToFullPowerTicks();
			}
		}
		
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.setBlockState(getPos(), block.getDefaultState().withProperty(block.is_powered, powerTicksLeft > 0));
		
		markDirty();
	}
	
	public void sendUpdate(boolean powered) {
		if (!world.isRemote) {
			world.setBlockState(getPos(), world.getBlockState(getPos()).withProperty(block.is_powered, powered));
			world.notifyBlockUpdate(pos, world.getBlockState(getPos()), world.getBlockState(getPos()).withProperty(block.is_powered, powered), 3);
			world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
			markDirty();
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setTag("items", itemStackHandler.serializeNBT());
		
		compound.setBoolean("isPublic", isPublic);
		compound.setInteger("powerTicksLeft", powerTicksLeft);
		
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		if (compound.hasKey("items")) {
			itemStackHandler.deserializeNBT((NBTTagCompound)compound.getTag("items"));
		}
		
		isPublic = compound.getBoolean("isPublic");
		powerTicksLeft = compound.getInteger("powerTicksLeft");
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}

	public void setToFullPowerTicks() {
		this.powerTicksLeft = maxTicks;
	}

	public int getPowerTicksLeft() {
		return powerTicksLeft;
	}
	
	@Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) itemStackHandler;
        }
        return super.getCapability(capability, facing);
    }
	
	public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }
}