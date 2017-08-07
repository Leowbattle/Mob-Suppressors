package com.leo.mobsuppressors.eventhooks;

import com.leo.mobsuppressors.EnumMobSuppressorType;
import com.leo.mobsuppressors.blocks.BlockMobSuppressor;
import com.leo.mobsuppressors.entities.ai.EntityAIBreakDoorCheckMobSuppressor;
import com.leo.mobsuppressors.tileentity.TileEntityMobSuppressor;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHooks {
	@SubscribeEvent
	public void onEnderTeleport(EnderTeleportEvent event) {
		World world = Minecraft.getMinecraft().world;
		
		for (TileEntity TE: world.loadedTileEntityList) {
			
			if (TE instanceof TileEntityMobSuppressor && ((TileEntityMobSuppressor) TE).type == EnumMobSuppressorType.ENDER && world.getBlockState(TE.getPos()).getBlock() instanceof BlockMobSuppressor) {
				System.out.println(world.getBlockState(TE.getPos()).getBlock().getClass());
				
				BlockMobSuppressor enderSuppressor = (BlockMobSuppressor)world.getBlockState(TE.getPos()).getBlock();
				if (world.getBlockState(TE.getPos()).getValue(enderSuppressor.is_powered)) {
					
					double distanceX = TE.getPos().getX() - event.getEntity().posX;
					double distanceY = TE.getPos().getY() - event.getEntity().posY;
					double distanceZ = TE.getPos().getZ() - event.getEntity().posZ;
					
					if (distanceX < 5 && distanceY < 5 && distanceZ < 10) {
						
						if (event.getEntity() instanceof EntityPlayer != true) {
						
							event.setCanceled(true);
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onCreeperExplode(ExplosionEvent event) {
		World world = Minecraft.getMinecraft().world;
		
		if (!(event.getExplosion().getExplosivePlacedBy() instanceof EntityCreeper)) {
			return;
		}
		
		for (TileEntity TE: world.loadedTileEntityList) {
			
			if (TE instanceof TileEntityMobSuppressor && ((TileEntityMobSuppressor) TE).type == EnumMobSuppressorType.CREEPER && world.getBlockState(TE.getPos()).getBlock() instanceof BlockMobSuppressor) {
				System.out.println(world.getBlockState(TE.getPos()).getBlock().getClass());
				
				BlockMobSuppressor creeperSuppressor = (BlockMobSuppressor)world.getBlockState(TE.getPos()).getBlock();
				if (world.getBlockState(TE.getPos()).getValue(creeperSuppressor.is_powered)) {
					
					double distanceX = TE.getPos().getX() - event.getExplosion().getExplosivePlacedBy().posX;
					double distanceY = TE.getPos().getY() - event.getExplosion().getExplosivePlacedBy().posY;
					double distanceZ = TE.getPos().getZ() - event.getExplosion().getExplosivePlacedBy().posZ;
					
					if (distanceX < 5 && distanceY < 5 && distanceZ < 10) {
						event.setCanceled(true);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntitySpawn(EntityJoinWorldEvent e) {
		if (e.getEntity() instanceof EntityZombie) {
			for (Object task: ((EntityLiving)e.getEntity()).tasks.taskEntries.toArray()) {
				if (task instanceof EntityAIBreakDoor) {
					((EntityZombie)e.getEntity()).tasks.removeTask((EntityAIBase)task);
				}
			}
			
			((EntityZombie)e.getEntity()).tasks.addTask(8, new EntityAIBreakDoorCheckMobSuppressor((EntityLiving)e.getEntity()));
		}
	}
}
