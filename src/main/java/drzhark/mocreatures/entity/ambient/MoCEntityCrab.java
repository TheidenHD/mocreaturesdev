/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityCrab extends MoCEntityTameableAnimal {

    public MoCEntityCrab(World world) {
        super(world);
        setSize(0.45F, 0.3F);
        setAge(50 + this.rand.nextInt(50));
    }

    @Override
    protected void initEntityAI() {
        this.goalSelector.addGoal(1, new EntityAIFollowOwnerPlayer(this, 0.8D, 6F, 5F));
        this.goalSelector.addGoal(4, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.goalSelector.addGoal(6, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new EntityAILookIdle(this));
        this.targetgoalSelector.addGoal(1, new EntityAIHurtByTarget(this, false));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(Attributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getEntityAttribute(Attributes.ARMOR).setBaseValue(2.0D);
        this.getEntityAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getAttributeMap().registerAttribute(Attributes.ATTACK_DAMAGE);
        this.getEntityAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.5D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(this.rand.nextInt(5) + 1);
        }

    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("crab_blue.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("crab_spotted.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("crab_green.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("crab_russet.png");
            default:
                return MoCreatures.proxy.getModelTexture("crab_red.png");
        }
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(3);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.CRAB;
    }

    @Override
    public boolean isCreatureType(EntityClassification type, boolean forSpawnCount) {
        return type == EntityClassification.AMBIENT;
    }

    @Override
    public boolean isOnLadder() {
        return this.collidedHorizontally;
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    @Override
    public void jump() {
    }

    @Override
    protected void collideWithEntity(Entity entity) {
        if (entity instanceof PlayerEntity && this.getAttackTarget() == null && !(entity.world.getDifficulty() == Difficulty.PEACEFUL)) {
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1.5F);
        }

        super.collideWithEntity(entity);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        this.playSound(MoCSoundEvents.ENTITY_GOAT_SMACK, 1.0F, 2.0F);
        return super.attackEntityAsMob(entity);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getSizeFactor() {
        return 0.7F * getAge() * 0.01F;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFleeing() {
        return MoCTools.getMyMovementSpeed(this) > 0.09F;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public int nameYOffset() {
        return -20;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }
}
