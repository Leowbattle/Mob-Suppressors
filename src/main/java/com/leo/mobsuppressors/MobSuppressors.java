package com.leo.mobsuppressors;

import com.leo.mobsuppressors.blocks.ModBlocks;
import com.leo.mobsuppressors.eventhooks.EventHooks;
import com.leo.mobsuppressors.items.ModItems;
import com.leo.mobsuppressors.network.PacketAltarCraft;
import com.leo.mobsuppressors.network.PacketRequestUpdatePedestal;
import com.leo.mobsuppressors.network.PacketRequestUpdateTower;
import com.leo.mobsuppressors.network.PacketUpdatePedestal;
import com.leo.mobsuppressors.network.PacketUpdateTower;
import com.leo.mobsuppressors.proxies.ClientProxy;
import com.leo.mobsuppressors.tileentity.ModTileEntities;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = MobSuppressors.modid, name = MobSuppressors.name, version = MobSuppressors.version, acceptedMinecraftVersions = MobSuppressors.acceptedMinecraftVersions)
public class MobSuppressors {
	public static final String modid = "mobsuppressors";
	public static final String name = "Mob Suppressors";
	public static final String version = "1.0";
	public static final String acceptedMinecraftVersions = "[1.11.2]";
	
	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel("mobsuppressors");
	
	@SidedProxy(serverSide = "com.leo.mobsuppressors.proxies.ServerProxy", clientSide = "com.leo.mobsuppressors.proxies.ClientProxy")
	public static ClientProxy proxy;
	
	@Mod.Instance(MobSuppressors.modid)
	public static MobSuppressors instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new EventHooks());
		
		network.registerMessage(new PacketUpdatePedestal.Handler(), PacketUpdatePedestal.class, 0, Side.CLIENT);
		network.registerMessage(new PacketRequestUpdatePedestal.Handler(), PacketRequestUpdatePedestal.class, 1, Side.SERVER);
		network.registerMessage(new PacketUpdateTower.Handler(), PacketUpdateTower.class, 2, Side.CLIENT);
		network.registerMessage(new PacketRequestUpdateTower.Handler(), PacketRequestUpdateTower.class, 3, Side.SERVER);
		network.registerMessage(new PacketAltarCraft.Handler(), PacketAltarCraft.class, 5, Side.SERVER);
		network.registerMessage(new PacketAltarCraft.Handler(), PacketAltarCraft.class, 6, Side.CLIENT);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		ModBlocks.init();
		ModTileEntities.init();
		ModItems.init();
		
		proxy.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
