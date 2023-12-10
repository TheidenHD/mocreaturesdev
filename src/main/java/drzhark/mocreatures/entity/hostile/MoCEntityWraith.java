/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import javax.annotation.Nullable;

public class MoCEntityWraith extends MoCEntityMob//MoCEntityFlyerMob
{

    public int attackCounter;

    public MoCEntityWraith(World world) {
        super(world);
        this.collidedVertically = false;
        setSize(0.6F, 2.0F);
        experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        this.goalSelector.addGoal(2, new EntityAIAttackMelee(this, 1.0D, true));
        this.goalSelector.addGoal(8, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.targetgoalSelector.addGoal(1, new EntityAINearestAttackableTarget<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes().createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0D).createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            int i = this.rand.nextInt(100);
            if (i < 5 && MoCreatures.proxy.easterEggs) {
                setTypeMoC(2);
                setCustomNameTag("Scratch");
            } else {
                setTypeMoC(1);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {
        if (getTypeMoC() == 2) {
            return MoCreatures.proxy.getModelTexture("wraith_scratch.png");
        } else {
            return MoCreatures.proxy.getModelTexture(MoCreatures.proxy.alphaWraithEyes ? "wraith_alpha.png" : "wraith.png");
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCreatures.proxy.legacyWraithSounds ? MoCSoundEvents.ENTITY_WRAITH_DEATH_LEGACY : MoCSoundEvents.ENTITY_WRAITH_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCreatures.proxy.legacyWraithSounds ? MoCSoundEvents.ENTITY_WRAITH_HURT_LEGACY : MoCSoundEvents.ENTITY_WRAITH_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCreatures.proxy.legacyWraithSounds ? MoCSoundEvents.ENTITY_WRAITH_AMBIENT_LEGACY : MoCSoundEvents.ENTITY_WRAITH_AMBIENT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.WRAITH;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity par1Entity) {
    }

    @Override
    public void fall(float f, float f1) {
    }

    protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
    }

    public int maxFlyingHeight() {
        return 10;
    }

    public int minFlyingHeight() {
        return 3;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        startArmSwingAttack();
        return super.attackEntityAsMob(entityIn);
    }

    /**
     * Starts attack counters and synchronizes animations with clients
     */
    private void startArmSwingAttack() {
        if (!this.world.isRemote) {
            this.attackCounter = 1;
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1), new TargetPoint(this.world.provider.getDimensionType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }
    }

    @Override
    public void livingTick() {
        if (this.attackCounter > 0) {
            this.attackCounter += 2;
            if (this.attackCounter > 10) this.attackCounter = 0;
        }

        super.livingTick();
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 1) {
            this.attackCounter = 1;
        }

    }

    @Override
    protected boolean isHarmedByDaylight() {
        return true;
    }

    public float getEyeHeight() {
        return this.getHeight() * 0.86F;
    }
}
