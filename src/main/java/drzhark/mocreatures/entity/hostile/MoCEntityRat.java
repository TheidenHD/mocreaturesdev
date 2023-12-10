/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;
import java.util.List;

public class MoCEntityRat extends MoCEntityMob {

    private static final DataParameter<Boolean> CLIMBING = EntityDataManager.createKey(MoCEntityRat.class, DataSerializers.BOOLEAN);

    public MoCEntityRat(World world) {
        super(world);
        setSize(0.58F, 0.455F);
        experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        this.goalSelector.addGoal(0, new EntityAISwimming(this));
        this.goalSelector.addGoal(2, new MoCEntityRat.AIRatAttack(this));
        this.goalSelector.addGoal(8, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.targetgoalSelector.addGoal(1, new EntityAIHurtByTarget(this, false));
        this.targetgoalSelector.addGoal(2, new MoCEntityRat.AIRatTarget<>(this, PlayerEntity.class));
        this.targetgoalSelector.addGoal(3, new MoCEntityRat.AIRatTarget<>(this, EntityIronGolem.class));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(Attributes.MAX_HEALTH).setBaseValue(16.0D);
        this.getEntityAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new PathNavigateClimber(this, worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(CLIMBING, Boolean.FALSE);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getTypeMoC() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 65) {
                setTypeMoC(1);
            } else if (i <= 98) {
                setTypeMoC(2);
            } else {
                setTypeMoC(3);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("rat_black.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("rat_white.png");
            default:
                return MoCreatures.proxy.getModelTexture("rat_brown.png");
        }
    }

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(getBoundingBox().minY), this.getPosZ());
        Biome currentbiome = MoCTools.biomeKind(this.world, pos);

        try {
            if (BiomeDictionary.hasType(currentbiome, BiomeDictionary.Type.MESA)) {
                setTypeMoC(1); // only brown rats
            }

            if (BiomeDictionary.hasType(currentbiome, BiomeDictionary.Type.SNOWY)) {
                setTypeMoC(3); // only white rats
            }
        } catch (Exception ignored) {
        }
        return true;
    }

    @Override
    protected SoundEvent getFallSound(int heightIn) {
        return null;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getTrueSource();
        if (entity instanceof LivingEntity) {
            setAttackTarget((LivingEntity) entity);
            if (!this.world.isRemote) {
                List<MoCEntityRat> list = this.world.getEntitiesWithinAABB(MoCEntityRat.class, new AxisAlignedBB(this.getPosX(), this.getPosY(), this.getPosZ(), this.getPosX() + 1.0D, this.getPosY() + 1.0D, this.getPosZ() + 1.0D).grow(16D, 4D, 16D));
                for (MoCEntityRat entityrat : list) {
                    if ((entityrat != null) && (entityrat.getAttackTarget() == null)) {
                        entityrat.setAttackTarget((LivingEntity) entity);
                    }
                }
            }
        }
        return super.attackEntityFrom(damagesource, i);
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if ((this.rand.nextInt(100) == 0) && (this.getBrightness() > 0.5F)) {
            setAttackTarget(null);
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCreatures.proxy.legacyRatDeathSound ? MoCSoundEvents.ENTITY_RAT_DEATH_LEGACY : MoCSoundEvents.ENTITY_RAT_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_RAT_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_RAT_AMBIENT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.RAT;
    }

    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    public boolean isBesideClimbableBlock() {
        return this.dataManager.get(CLIMBING);
    }

    public void setBesideClimbableBlock(boolean climbing) {
        this.dataManager.set(CLIMBING, climbing);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.world.isRemote) {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }

    public float getEyeHeight() {
        return this.getHeight() * 0.5F;
    }

    static class AIRatAttack extends EntityAIAttackMelee {
        public AIRatAttack(MoCEntityRat rat) {
            super(rat, 1.0D, true);
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

    static class AIRatTarget<T extends LivingEntity> extends EntityAINearestAttackableTarget<T> {
        public AIRatTarget(MoCEntityRat rat, Class<T> classTarget) {
            super(rat, classTarget, true);
        }

        @Override
        public boolean shouldExecute() {
            float f = this.taskOwner.getBrightness();
            return f < 0.5F && super.shouldExecute();
        }
    }
}
