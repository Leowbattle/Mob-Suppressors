package com.leo.mobsuppressors.network;

import com.leo.mobsuppressors.tileentity.TileEntitySuppressionAltarCore;
import com.leo.mobsuppressors.tileentity.TileEntitySuppressionPedestal;
import com.leo.mobsuppressors.tileentity.TileEntitySuppressionTower;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketAltarCraft implements IMessage {
	public static class Handler implements IMessageHandler<PacketAltarCraft, IMessage> {
		@Override
		public PacketUpdatePedestal onMessage(PacketAltarCraft message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntitySuppressionTower te = (TileEntitySuppressionTower)Minecraft.getMinecraft().world.getTileEntity(message.pos);
				te.itemStackHandler.setStackInSlot(0, message.stack);
				System.out.println(te.itemStackHandler.getStackInSlot(0));
				te.lastChangeTime = message.lastChangeTime;
				System.out.println(message.stack + "handler");
			});
			return null;
		}
	}
	
	private BlockPos pos;
	private ItemStack stack;
	private long lastChangeTime;
	
	public PacketAltarCraft(BlockPos pos, ItemStack itemStack, long lastChangeTime) {
		this.pos = pos;
		this.stack = itemStack;
		this.lastChangeTime = lastChangeTime;
	}
	
	public PacketAltarCraft(TileEntitySuppressionTower te, ItemStack stack) {
		this(te.getPos(), stack, te.lastChangeTime);
		System.out.println(stack + "\n\n\n\n\n\n\n");
	}
	
	public PacketAltarCraft() {
		
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
