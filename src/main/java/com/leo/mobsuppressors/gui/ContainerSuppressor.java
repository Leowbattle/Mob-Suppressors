package com.leo.mobsuppressors.gui;

import com.leo.mobsuppressors.tileentity.TileEntityMobSuppressor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSuppressor extends Container {
	protected static final Class<ItemEnderPearl> ENDER_FUEL = ItemEnderPearl.class;
	
	public TileEntityMobSuppressor TE;
	
	public ContainerSuppressor(IInventory playerInventory, TileEntityMobSuppressor TE) {
		this.TE = TE;
		
		addSlots(playerInventory);
	}
	
	public void addSlots(IInventory playerInventory) {
		// Player Inventory, Slot 9-35, Slot IDs 9-35
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
	        }
	    }

	    // Player Inventory, Slot 0-8, Slot IDs 36-44
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 142));
	    }
        
        IItemHandler itemHandler = this.TE.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 9;
        int y = 6;

        addSlotToContainer(new SlotItemHandler(itemHandler, 0, 80, 35));
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
