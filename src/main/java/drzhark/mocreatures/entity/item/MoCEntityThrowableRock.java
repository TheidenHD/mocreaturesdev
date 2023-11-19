/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.hostile.MoCEntityGolem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityThrowableRock extends Entity {

    private static final DataParameter<Integer> ROCK_STATE = EntityDataManager.createKey(MoCEntityThrowableRock.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> MASTERS_ID = EntityDataManager.createKey(MoCEntityThrowableRock.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> BEHAVIOUR_TYPE = EntityDataManager.createKey(MoCEntityThrowableRock.class, DataSerializers.VARINT);
    public int acceleration = 100;
    private int rockTimer;
    private double oPosX;
    private double oPosY;
    private double oPosZ;

    public MoCEntityThrowableRock(World par1World) {
        super(par1World);
        this.preventEntitySpawning = true;
        this.setSize(1F, 1F);
        //this.yOffset = this.height / 2.0F;
    }

    public MoCEntityThrowableRock(World par1World, Entity entitythrower, double par2, double par4, double par6) {
        this(par1World);
        this.setPosition(par2, par4, par6);
        this.rockTimer = 250;
        this.prevPosX = this.oPosX = par2;
        this.prevPosY = this.oPosY = par4;
        this.prevPosZ = this.oPosZ = par6;
        this.setMasterID(entitythrower.getEntityId());
    }

    public BlockState getState() {
        return Block.getStateById(this.dataManager.get(ROCK_STATE) & 65535);
    }

    public void setState(BlockState state) {
        this.dataManager.set(ROCK_STATE, (Block.getStateId(state) & 65535));
    }

    public int getMasterID() {
        return this.dataManager.get(MASTERS_ID);
    }

    public void setMasterID(int i) {
        this.dataManager.set(MASTERS_ID, i);
    }

    public int getBehavior() {
        return this.dataManager.get(BEHAVIOUR_TYPE);
    }

    public void setBehavior(int i) {
        this.dataManager.set(BEHAVIOUR_TYPE, i);
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(BEHAVIOUR_TYPE, 0);
        this.dataManager.register(ROCK_STATE, 0);
        this.dataManager.register(MASTERS_ID, 0);
    }

    @Override
    public void writeEntityToNBT(CompoundNBT nbttagcompound) {
        BlockState iblockstate = this.getState();
        nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.setInteger("Behavior", getBehavior());
        nbttagcompound.setInteger("MasterID", getMasterID());
        nbttagcompound.setShort("BlockID", (short) Block.getIdFromBlock(iblockstate.getBlock()));
        nbttagcompound.setShort("BlockMetadata", (short) iblockstate.getBlock().getMetaFromState(iblockstate));
    }

    @Override
    public void readEntityFromNBT(CompoundNBT nbttagcompound) {
        nbttagcompound = MoCTools.getEntityData(this);
        setBehavior(nbttagcompound.getInteger("Behavior"));
        setMasterID(nbttagcompound.getInteger("MasterID"));
        BlockState iblockstate;
        iblockstate = Block.getBlockById(nbttagcompound.getShort("BlockID")).getStateFromMeta(nbttagcompound.getShort("BlockMetadata") & 65535);
        this.setState(iblockstate);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void onEntityUpdate() {
        Entity master = getMaster();
        if (this.rockTimer-- <= -50 && getBehavior() == 0 || master == null) transformToItem();

        // held TRocks don't need to adjust its position
        if (getBehavior() == 1) return;

        // rock damage code (for all rock behaviors)
        if (!this.onGround) {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().contract(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            for (Entity entity : list) {
                if (master != null && entity.getEntityId() == master.getEntityId()) continue;
                if (entity instanceof MoCEntityGolem) continue;
                if (entity != null && !(entity instanceof EntityLivingBase)) continue;
                if (master != null) entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase) master), 4);
                else if (entity != null) entity.attackEntityFrom(DamageSource.GENERIC, 4);
            }
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (getBehavior() == 2) {
            if (master == null) {
                setBehavior(0);
                this.rockTimer = -50;
                return;
            }

            // moves towards the master entity the bigger the number, the slower
            --this.acceleration;
            if (this.acceleration < 10) {
                this.acceleration = 10;
            }

            float tX = (float) this.posX - (float) master.posX;
            float tZ = (float) this.posZ - (float) master.posZ;
            float distXZToMaster = tX * tX + tZ * tZ;

            if (distXZToMaster < 1.5F && master instanceof MoCEntityGolem) {
                ((MoCEntityGolem) master).receiveRock(this.getState());
                this.setBehavior(0);
                this.setDead();
            }

            double summonedSpeed = this.acceleration;
            this.motionX = ((master.posX - this.posX) / summonedSpeed);
            this.motionY = ((master.posY - this.posY) / 20D + 0.15D);
            this.motionZ = ((master.posZ - this.posZ) / summonedSpeed);
            if (!this.world.isRemote) this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            return;
        }

        // imploding / exploding rock
        if (getBehavior() == 4) {
            if (master == null) {
                if (!this.world.isRemote) setBehavior(5);
                return;
            }

            // moves towards the master entity
            // the bigger the number, the slower
            this.acceleration = 10;

            float tX = (float) this.posX - (float) master.posX;
            float tZ = (float) this.posZ - (float) master.posZ;
            float distXZToMaster = tX * tX + tZ * tZ;

            double summonedSpeed = this.acceleration;
            this.motionX = ((master.posX - this.posX) / summonedSpeed);
            this.motionY = ((master.posY - this.posY) / 20D + 0.15D);
            this.motionZ = ((master.posZ - this.posZ) / summonedSpeed);

            if (distXZToMaster < 2.5F && master instanceof MoCEntityGolem) {
                this.motionX = 0D;
                this.motionY = 0D;
                this.motionZ = 0D;
            }

            if (!this.world.isRemote) {
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            }

            return;
        }

        // exploding rock
        if (getBehavior() == 5) {
            this.acceleration = 5;
            double summonedSpeed = this.acceleration;
            this.motionX = ((this.oPosX - this.posX) / summonedSpeed);
            this.motionY = ((this.oPosY - this.posY) / 20D + 0.15D);
            this.motionZ = ((this.oPosZ - this.posZ) / summonedSpeed);
            if (!this.world.isRemote) this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            setBehavior(0);
            return;
        }

        this.motionY -= 0.04D;
        if (!this.world.isRemote) this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.98D;
        this.motionY *= 0.98D;
        this.motionZ *= 0.98D;

        if (this.onGround) {
            this.motionX *= 0.699D;
            this.motionZ *= 0.699D;
            this.motionY *= -0.5D;
        }
    }

    public void transformToItem() {
        // don't drop rocks if mob griefing is set to false, prevents duping
        if (!this.world.isRemote && MoCTools.mobGriefing(this.world) && MoCreatures.proxy.golemDestroyBlocks) {
            ItemEntity entityitem = new ItemEntity(this.world, this.posX, this.posY, this.posZ, new ItemStack(Item.getItemFromBlock(this.getState().getBlock())));
            entityitem.setDefaultPickupDelay();
            entityitem.setAgeToCreativeDespawnTime();
            this.world.spawnEntity(entityitem);
        }
        this.setDead();
    }

    private Entity getMaster() {
        List<Entity> entityList = this.world.loadedEntityList;
        for (Entity entity : entityList) {
            if (entity.getEntityId() == getMasterID()) return entity;
        }
        return null;
    }
}
