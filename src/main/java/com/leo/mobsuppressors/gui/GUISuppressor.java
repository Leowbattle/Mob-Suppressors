package com.leo.mobsuppressors.gui;

import com.leo.mobsuppressors.tileentity.TileEntityMobSuppressor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GUISuppressor extends GuiContainer {
	TileEntityMobSuppressor TE;
	
	public GUISuppressor(IInventory playerInventory, TileEntityMobSuppressor TE, int ID) {
		super(new ContainerSuppressor(playerInventory, TE));
		this.xSize = 176;
		this.ySize = 166;
		
		this.TE = TE;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("mobsuppressors:textures/gui/container/suppressor_gui.png"));
		int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		String powerTicksLeftDisplay = String.valueOf(TE.getPowerTicksLeft());
	}
	
}
