/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.hunter.MoCEntityBear;
import drzhark.mocreatures.entity.hunter.MoCEntityBigCat;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;
import java.util.List;

public class MoCEntityWWolf extends MoCEntityMob {

    public int mouthCounter;
    public int tailCounter;

    public MoCEntityWWolf(World world) {
        super(world);
        setSize(0.8F, 1.1F);
        setAdult(true);
        experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        this.goalSelector.addGoal(0, new EntityAISwimming(this));
        this.goalSelector.addGoal(2, new MoCEntityWWolf.AIWolfAttack(this));
        this.goalSelector.addGoal(8, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.targetgoalSelector.addGoal(1, new EntityAIHurtByTarget(this, false));
        this.targetgoalSelector.addGoal(2, new MoCEntityWWolf.AIWolfTarget<>(this, PlayerEntity.class));
        this.targetgoalSelector.addGoal(3, new MoCEntityWWolf.AIWolfTarget<>(this, EntityIronGolem.class));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(Attributes.MAX_HEALTH).setBaseValue(15.0D);
        this.getEntityAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.5D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(this.rand.nextInt(4) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 1:
                return MoCreatures.proxy.getModelTexture("wild_wolf_black.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("wild_wolf_timber.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("wild_wolf_dark.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("wild_wolf_bright.png");
            default:
                return MoCreatures.proxy.getModelTexture("wild_wolf_classic.png");
        }
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.rand.nextInt(200) == 0) {
            moveTail();
        }

        if (this.mouthCounter > 0 && ++this.mouthCounter > 15) {
            this.mouthCounter = 0;
        }

        if (this.tailCounter > 0 && ++this.tailCounter > 8) {
            this.tailCounter = 0;
        }
    }

    @Override
    public boolean checkSpawningBiome() {
        int i = MathHelper.floor(this.getPosX());
        int j = MathHelper.floor(getBoundingBox().minY);
        int k = MathHelper.floor(this.getPosZ());

        Biome biome = MoCTools.biomeKind(this.world, new BlockPos(i, j, k));
        if (BiomeDictionary.hasType(biome, Type.SNOWY)) {
            setTypeMoC(3);
        }
        selectType();
        return true;
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.world.canSeeSky(new BlockPos(this));
    }

    //TODO move this
    public LivingEntity getClosestTarget(Entity entity, double d) {
        double d1 = -1D;
        LivingEntity entityliving = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(d));
        for (Entity entity1 : list) {
            if (!(entity1 instanceof LivingEntity) || (entity1 == entity) || (entity1 == entity.getRidingEntity())
                    || (entity1 == entity.getRidingEntity()) || (entity1 instanceof PlayerEntity) || (entity1 instanceof MonsterEntity)
                    || (entity1 instanceof MoCEntityBigCat) || (entity1 instanceof MoCEntityBear) || (entity1 instanceof EntityCow)
                    || ((entity1 instanceof EntityWolf) && !(MoCreatures.proxy.attackWolves))
                    || ((entity1 instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses))) {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((LivingEntity) entity1).canEntityBeSeen(entity)) {
                d1 = d2;
                entityliving = (LivingEntity) entity1;
            }
        }

        return entityliving;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_WOLF_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth();
        return MoCSoundEvents.ENTITY_WOLF_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_WOLF_AMBIENT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.WILD_WOLF;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double dist = (0.1D);
        double newPosX = this.getPosX() + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.getPosZ() - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        passenger.setPosition(newPosX, this.getPosY() + getMountedYOffset() + passenger.getYOffset(), newPosZ);
        passenger.rotationYaw = this.rotationYaw;
    }

    @Override
    public double getMountedYOffset() {
        return (this.getHeight() * 0.75D) - 0.1D;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote && !this.isBeingRidden() && this.rand.nextInt(100) == 0) {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(4D, 2D, 4D));
            for (Entity entity : list) {
                if (!(entity instanceof MonsterEntity)) {
                    continue;
                }
                MonsterEntity entitymob = (MonsterEntity) entity;
                if (entitymob.getRidingEntity() == null
                        && (entitymob instanceof EntitySkeleton || entitymob instanceof EntityZombie || entitymob instanceof MoCEntitySilverSkeleton)) {
                    entitymob.startRiding(this);
                    break;
                }
            }
        }
    }

    public float getEyeHeight() {
        return this.getHeight() * 0.945F;
    }

    static class AIWolfAttack extends EntityAIAttackMelee {
        public AIWolfAttack(MoCEntityWWolf wolf) {
            super(wolf, 1.0D, true);
        }

        @Override
        public boolean shouldContinueExecuting() {
            float f = this.attacker.getBrightness();

            if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget(null);
                return false;
            } else {
                return super.shouldContinueExecuting();
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 4.0F + attackTarget.getWidth();
        }
    }

    static class AIWolfTarget<T extends LivingEntity> extends EntityAINearestAttackableTarget<T> {
        public AIWolfTarget(MoCEntityWWolf wolf, Class<T> classTarget) {
            super(wolf, classTarget, true);
        }

        @Override
        public boolean shouldExecute() {
            float f = this.taskOwner.getBrightness();
            return f < 0.5F && super.shouldExecute();
        }
    }
}
