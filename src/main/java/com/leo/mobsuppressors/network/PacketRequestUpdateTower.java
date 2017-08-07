package com.leo.mobsuppressors.network;

import com.leo.mobsuppressors.tileentity.TileEntitySuppressionTower;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateTower implements IMessage {
	public static class Handler implements IMessageHandler<PacketRequestUpdateTower, PacketUpdateTower> {
		@Override
		public PacketUpdateTower onMessage(PacketRequestUpdateTower message, MessageContext ctx) {
			World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(message.dimension);
			TileEntitySuppressionTower te = (TileEntitySuppressionTower)world.getTileEntity(message.pos);
			if (te != null) {
				return new PacketUpdateTower(te);
			} else {
				return null;
			}
		}
	
	}
	
	private BlockPos pos;
	private int dimension;
	
	public PacketRequestUpdateTower(BlockPos pos, int dimension) {
		this.pos = pos;
		this.dimension = dimension;
	}
	
	public PacketRequestUpdateTower(TileEntitySuppressionTower te) {
		this(te.getPos(), te.getWorld().provider.getDimension());
	}
	
	public PacketRequestUpdateTower() {
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