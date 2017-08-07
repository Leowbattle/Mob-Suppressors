package com.leo.mobsuppressors.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

public class TESRSuppressionTower extends TileEntitySpecialRenderer<TileEntitySuppressionTower> {
	@Override
	public void renderTileEntityAt(TileEntitySuppressionTower te, double x, double y, double z, float partialTicks, int destroyStage) {
		GlStateManager.enableRescaleNormal();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
		GlStateManager.enableBlend();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		
		ItemStack stack = te.itemStackHandler.getStackInSlot(0);
		if (!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.15, z + 0.5);
			GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks) * 4, 0, 1, 0);
	
			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
			model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);
	
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
			GlStateManager.popMatrix();
		}

		if (te.netherStarUsesLeft > 0) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.45, y + 0.5, z + 0.55);
			GlStateManager.scale(0.75, 0.75, 0.75);
			GlStateManager.rotate(90, 0, 0, 0);
			IBakedModel netherStarModel = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(new ItemStack(Items.NETHER_STAR),  te.getWorld(), null);
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(Items.NETHER_STAR, 1), netherStarModel);
			GlStateManager.popMatrix();
		}
		
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableBlend();
	}
}
