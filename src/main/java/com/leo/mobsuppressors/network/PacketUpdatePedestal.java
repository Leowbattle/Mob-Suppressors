package com.leo.mobsuppressors.network;

import com.leo.mobsuppressors.tileentity.TileEntitySuppressionPedestal;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdatePedestal implements IMessage {
	public static class Handler implements IMessageHandler<PacketUpdatePedestal, IMessage> {
		@Override
		public IMessage onMessage(PacketUpdatePedestal message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntitySuppressionPedestal te = (TileEntitySuppressionPedestal)Minecraft.getMinecraft().world.getTileEntity(message.pos);
				te.itemStackHandler.setStackInSlot(0, message.stack);
				te.lastChangeTime = message.lastChangeTime;
			});
			return null;
		}
	
	}

	
	private BlockPos pos;
	private ItemStack stack;
	private long lastChangeTime;
	
	public PacketUpdatePedestal(BlockPos pos, ItemStack stack, long lastChangeTime) {
		this.pos = pos;
		this.stack = stack;
		this.lastChangeTime = lastChangeTime;
	}
	
	public PacketUpdatePedestal(TileEntitySuppressionPedestal te) {
		this(te.getPos(), te.itemStackHandler.getStackInSlot(0), te.lastChangeTime);
	}
	
	public PacketUpdatePedestal() {
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
