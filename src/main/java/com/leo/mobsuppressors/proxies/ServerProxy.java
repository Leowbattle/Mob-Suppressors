package com.leo.mobsuppressors.proxies;

import com.leo.mobsuppressors.MobSuppressors;
import com.leo.mobsuppressors.gui.ModGUIHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ServerProxy implements CommonProxy {
	public void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(MobSuppressors.instance, new ModGUIHandler());
	}
}
