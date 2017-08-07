package com.leo.mobsuppressors.network;

import com.leo.mobsuppressors.tileentity.TileEntitySuppressionPedestal;
import com.leo.mobsuppressors.tileentity.TileEntitySuppressionTower;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateTower implements IMessage {
	public static class Handler implements IMessageHandler<PacketUpdateTower, IMessage> {
		@Override
		public IMessage onMessage(PacketUpdateTower message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntitySuppressionTower te = (TileEntitySuppressionTower)Minecraft.getMinecraft().world.getTileEntity(message.pos);
				te.itemStackHandler.setStackInSlot(0, message.stack);
				System.out.println(te.itemStackHandler.getStackInSlot(0));
				te.lastChangeTime = message.lastChangeTime;
			});
			return null;
		}
	
	}

	private BlockPos pos;
	private ItemStack stack;
	private long lastChangeTime;
	
	public PacketUpdateTower(BlockPos pos, ItemStack stack, long lastChangeTime) {
		this.pos = pos;
		this.stack = stack;
		this.lastChangeTime = lastChangeTime;
	}
	
	public PacketUpdateTower(TileEntitySuppressionTower te) {
		this(te.getPos(), te.itemStackHandler.getStackInSlot(0), te.lastChangeTime);
	}
	
	public PacketUpdateTower() {
	
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		ByteBufUtils.writeItemStack(buf, stack);
		buf.writeLong(lastChangeTime);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		stack = ByteBufUtils.readItemStack(buf);
		lastChangeTime = buf.readLong();
	}

}
