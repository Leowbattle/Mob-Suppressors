package com.leo.mobsuppressors.entities.ai;

import com.leo.mobsuppressors.EnumMobSuppressorType;
import com.leo.mobsuppressors.tileentity.TileEntityMobSuppressor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIDoorInteract;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumDifficulty;

public class EntityAIBreakDoorCheckMobSuppressor extends EntityAIDoorInteract
{
    private int breakingTime;
    private int previousBreakProgress = -1;

    public EntityAIBreakDoorCheckMobSuppressor(EntityLiving entityIn)
    {
        super(entityIn);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	for (TileEntity te: theEntity.world.loadedTileEntityList) {
    		if (te instanceof TileEntityMobSuppressor && ((TileEntityMobSuppressor) te).type == EnumMobSuppressorType.ZOMBIE && ((TileEntityMobSuppressor)te).getPowerTicksLeft() > 0) {
    			double distanceX = te.getPos().getX() - theEntity.getPosition().getX();
    			double distanceY = te.getPos().getY() - theEntity.getPosition().getY();
    			double distanceZ = te.getPos().getZ() - theEntity.getPosition().getZ();
    			
    			System.out.println(distanceX + "\n");
				System.out.println(distanceY + "\n");
				System.out.println(distanceZ + "\n\n");
    			
    			if (distanceX < 5 && distanceY < 5 && distanceZ < 5) {
    				System.out.println("Will not break door");
    				return false;
    			}
    		}
    	}
    	
        if (!super.shouldExecute())
        {
            return false;
        }
        else if (!this.theEntity.world.getGameRules().getBoolean("mobGriefing") || !this.theEntity.world.getBlockState(this.doorPosition).getBlock().canEntityDestroy(this.theEntity.world.getBlockState(this.doorPosition), this.theEntity.world, this.doorPosition, this.theEntity) || !net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this.theEntity, this.doorPosition, this.theEntity.world.getBlockState(this.doorPosition)))
        {
            return false;
        } 
        else
        {
            BlockDoor blockdoor = this.doorBlock;
            return !BlockDoor.isOpen(this.theEntity.world, this.doorPosition);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
        this.breakingTime = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        double d0 = this.theEntity.getDistanceSq(this.doorPosition);
        boolean flag;

        if (this.breakingTime <= 240)
        {
            BlockDoor blockdoor = this.doorBlock;

            if (!BlockDoor.isOpen(this.theEntity.world, this.doorPosition) && d0 < 4.0D)
            {
                flag = true;
                return flag;
            }
        }

        flag = false;
        return flag;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        super.resetTask();
        this.theEntity.world.sendBlockBreakProgress(this.theEntity.getEntityId(), this.doorPosition, -1);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        super.updateTask();

        if (this.theEntity.getRNG().nextInt(20) == 0)
        {
            this.theEntity.world.playEvent(1019, this.doorPosition, 0);
        }

        ++this.breakingTime;
        int i = (int)((float)this.breakingTime / 240.0F * 10.0F);

        if (i != this.previousBreakProgress)
        {
            this.theEntity.world.sendBlockBreakProgress(this.theEntity.getEntityId(), this.doorPosition, i);
            this.previousBreakProgress = i;
        }

        if (this.breakingTime == 240 && this.theEntity.world.getDifficulty() == EnumDifficulty.HARD)
        {
            this.theEntity.world.setBlockToAir(this.doorPosition);
            this.theEntity.world.playEvent(1021, this.doorPosition, 0);
            this.theEntity.world.playEvent(2001, this.doorPosition, Block.getIdFromBlock(this.doorBlock));
        }
    }
}