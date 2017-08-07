package com.leo.mobsuppressors.proxies;

import com.leo.mobsuppressors.MobSuppressors;
import com.leo.mobsuppressors.blocks.ModBlocks;
import com.leo.mobsuppressors.gui.ModGUIHandler;
import com.leo.mobsuppressors.tileentity.TESRSuppressionPedestal;
import com.leo.mobsuppressors.tileentity.TESRSuppressionTower;
import com.leo.mobsuppressors.tileentity.TileEntitySuppressionPedestal;
import com.leo.mobsuppressors.tileentity.TileEntitySuppressionTower;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy implements CommonProxy {
	@Override
	public void init() {
		ModBlocks.registerRenderers();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(MobSuppressors.instance, new ModGUIHandler());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySuppressionPedestal.class, new TESRSuppressionPedestal());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySuppressionTower.class, new TESRSuppressionTower());
	}	
}
