/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.*;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;

public class MoCEntityFox extends MoCEntityTameableAnimal {

    public MoCEntityFox(World world) {
        super(world);
        setSize(0.7F, 0.85F);
        setAge(this.rand.nextInt(15) + 50);
        setAdult(this.rand.nextInt(3) != 0);
    }

    @Override
    protected void initEntityAI() {
        this.goalSelector.addGoal(1, new EntityAISwimming(this));
        this.goalSelector.addGoal(2, new EntityAIPanicMoC(this, 1.0D));
        this.goalSelector.addGoal(3, new EntityAIFleeFromPlayer(this, 1.0D, 4D));
        this.goalSelector.addGoal(3, new EntityAIFollowOwnerPlayer(this, 0.8D, 2F, 10F));
        this.goalSelector.addGoal(4, new EntityAIFollowAdult(this, 1.0D));
        this.goalSelector.addGoal(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.goalSelector.addGoal(6, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(7, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        //this.targetgoalSelector.addGoal(1, new EntityAIHunt<>(this, AnimalEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D);
        this.getAttributeMap().registerAttribute(Attributes.ATTACK_DAMAGE).createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getTypeMoC() == 0) {
            setTypeMoC(1);
        }
    }

    @Override
    public ResourceLocation getTexture() {

        if (!getIsAdult()) {
            if (getTypeMoC() == 2) {
                return MoCreatures.proxy.getModelTexture("fox_snow.png");
            }
            return MoCreatures.proxy.getModelTexture("fox_cub.png");
        }
        if (getTypeMoC() == 2) {
            return MoCreatures.proxy.getModelTexture("fox_snow.png");
        }
        return MoCreatures.proxy.getModelTexture("fox.png");
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if (entity != null && this.isRidingOrBeingRiddenBy(entity)) {
                return true;
            }
            if (entity != this && this.isNotScared() && entity instanceof LivingEntity && super.shouldAttackPlayers()) {
                setAttackTarget((LivingEntity) entity);
                setRevengeTarget((LivingEntity) entity);
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && ((stack.getItem() == MoCItems.rawTurkey))) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);

            if (!this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }
            this.setHealth(getMaxHealth());

            if (!this.world.isRemote && !getIsAdult() && (getAge() < 100)) {
                setAge(getAge() + 1);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult();
    }

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos =
                new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(getBoundingBox().minY),
                        MathHelper.floor(this.getPosZ()));
        Biome currentbiome = MoCTools.biomeKind(this.world, pos);
        try {
            if (BiomeDictionary.hasType(currentbiome, Type.SNOWY)) {
                setTypeMoC(2);
            }
        } catch (Exception ignored) {
        }
        return true;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_FOX_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_FOX_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_FOX_AMBIENT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.FOX;
    }

    @Override
    protected float getSoundVolume() {
        return 0.3F;
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == MoCItems.ratRaw;
    }

    @Override
    public int nameYOffset() {
        return -50;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return !(entity instanceof MoCEntityFox) && entity.getHeight() <= 0.7D && entity.getWidth() <= 0.7D;
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }

    @Override
    public float getSizeFactor() {
        if (getIsAdult()) {
            return 0.9F;
        }
        return 0.9F * getAge() * 0.01F;
    }

    public float getEyeHeight() {
        return this.getHeight() * 0.86F;
    }
}
