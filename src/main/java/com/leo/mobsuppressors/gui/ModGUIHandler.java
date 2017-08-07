package com.leo.mobsuppressors.gui;

import com.leo.mobsuppressors.tileentity.TileEntityMobSuppressor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGUIHandler implements IGuiHandler {
	private static int maxID = 0;
	
	public static final int enderSuppressorGUIID = maxID++;
	public static final int creepySuppressorGUIID = maxID++;
	public static final int rottenSuppressorGUIID = maxID++;
	public static final int slimySuppressorGUIID = maxID++;
	public static final int witheredSuppressorGUIID = maxID++;
	public static final int skeletalSuppressorGUIID = maxID++;
	public static final int magicalSuppressorGUIID = maxID++;
	public static final int stringySuppressorGUIID = maxID++;
	public static final int ghastlySuppressorGUIID = maxID++;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID > maxID) {
			return null;
		}
		
		return new ContainerSuppressor(player.inventory, (TileEntityMobSuppressor) world.getTileEntity(new BlockPos(x, y, z)));
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID > maxID) {
			return null;
		}

		return new GUISuppressor(player.inventory, (TileEntityMobSuppressor)world.getTileEntity(new BlockPos(x, y, z)), ID);
	}

}
