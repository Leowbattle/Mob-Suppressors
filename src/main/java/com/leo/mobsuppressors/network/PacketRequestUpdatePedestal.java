package com.leo.mobsuppressors.network;

import com.leo.mobsuppressors.tileentity.TileEntitySuppressionPedestal;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdatePedestal implements IMessage {
	public static class Handler implements IMessageHandler<PacketRequestUpdatePedestal, PacketUpdatePedestal> {
		
		@Override
		public PacketUpdatePedestal onMessage(PacketRequestUpdatePedestal message, MessageContext ctx) {
			World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(message.dimension);
			TileEntitySuppressionPedestal te = (TileEntitySuppressionPedestal)world.getTileEntity(message.pos);
			if (te != null) {
				return new PacketUpdatePedestal(te);
			} else {
				return null;
			}
		}
	
	}
	
	private BlockPos pos;
	private int dimension;
	
	public PacketRequestUpdatePedestal(BlockPos pos, int dimension) {
		this.pos = pos;
		this.dimension = dimension;
	}
	
	public PacketRequestUpdatePedestal(TileEntitySuppressionPedestal te) {
		this(te.getPos(), te.getWorld().provider.getDimension());
	}
	
	public PacketRequestUpdatePedestal() {
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(dimension);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		dimension = buf.readInt();
	}

}