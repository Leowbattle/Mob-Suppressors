package com.leo.mobsuppressors.tileentity;

import java.util.ArrayList;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {
	public static void init() {
		GameRegistry.registerTileEntity(TileEntityMobSuppressor.class, "mobsuppressor_tileentity");
		GameRegistry.registerTileEntity(TileEntitySuppressionPedestal.class, "altar_suppressionpedestal");
		GameRegistry.registerTileEntity(TileEntitySuppressionTower.class, "altar_suppressiontower");
		GameRegistry.registerTileEntity(TileEntitySuppressionAltarCore.class, "altar_core");
	}
}
