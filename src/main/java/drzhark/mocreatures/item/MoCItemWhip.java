package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.hunter.MoCEntityBigCat;
import drzhark.mocreatures.entity.hunter.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.neutral.MoCEntityElephant;
import drzhark.mocreatures.entity.neutral.MoCEntityKitty;
import drzhark.mocreatures.entity.neutral.MoCEntityOstrich;
import drzhark.mocreatures.entity.neutral.MoCEntityWyvern;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import net.minecraft.world.level.Level;


import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MoCItemWhip extends MoCItemSword {
    private float AttackSpeed;

    public MoCItemWhip(String name, Item.ToolMaterial material, float attackSpeedIn) {
        super(name, 0, material);
        this.maxStackSize = 1;
        this.AttackSpeed = attackSpeedIn;
    }

    @Override
    public EnumActionResult onItemUse(Player player, Level worldIn, BlockPos pos, InteractionHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        final ItemStack stack = player.getItemInHand(hand);
        Block block = worldIn.getBlockState(pos).getBlock();
        Block block1 = worldIn.getBlockState(pos.up()).getBlock();
        if (side != EnumFacing.DOWN && (block1 == Blocks.AIR) && (block != Blocks.AIR) && (block != Blocks.STANDING_SIGN)) {
            whipFX(worldIn, pos);
            worldIn.playSound(player, pos, MoCSoundEvents.ENTITY_GENERIC_WHIP, SoundCategory.PLAYERS, 0.5F, 0.4F / ((itemRand.nextFloat() * 0.4F) + 0.8F));
            player.getCooldownTracker().setCooldown(this, 20);
            stack.damageItem(2, player);
            List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(12D));
            for (Entity entity : list) {
                if (entity instanceof MoCEntityAnimal) {
                    MoCEntityAnimal animal = (MoCEntityAnimal) entity;
                    if (MoCreatures.proxy.enableOwnership && animal.getOwnerId() != null && !player.getUUID().equals(animal.getOwnerId()) && !MoCTools.isThisPlayerAnOP(player)) {
                        continue;
                    }
                }

                if (entity instanceof MoCEntityBigCat) {
                    MoCEntityBigCat entitybigcat = (MoCEntityBigCat) entity;
                    if (entitybigcat.getIsTamed()) {
                        entitybigcat.setSitting(!entitybigcat.getIsSitting());
                        entitybigcat.setIsJumping(false);
                        entitybigcat.getNavigator().clearPath();
                        entitybigcat.setAttackTarget(null);
                    } else if ((worldIn.getDifficulty().getId() > 0) && entitybigcat.getIsAdult()) {
                        entitybigcat.setAttackTarget(player);
                    }
                }

                if (entity instanceof MoCEntityHorse) {
                    MoCEntityHorse entityhorse = (MoCEntityHorse) entity;
                    if (entityhorse.getIsTamed()) {
                        if (entityhorse.getRidingEntity() == null) {
                            entityhorse.setSitting(!entityhorse.getIsSitting());
                            entityhorse.setIsJumping(false);
                            entityhorse.getNavigator().clearPath();
                            entityhorse.setAttackTarget(null);
                        } else if (entityhorse.isNightmare()) {
                            entityhorse.setNightmareInt(100);
                        } else if (entityhorse.sprintCounter == 0) {
                            entityhorse.sprintCounter = 1;
                        }
                    }
                }

                if ((entity instanceof MoCEntityKitty)) {
                    MoCEntityKitty entitykitty = (MoCEntityKitty) entity;
                    if ((entitykitty.getKittyState() > 2) && entitykitty.whipable()) {
                        entitykitty.setSitting(!entitykitty.getIsSitting());
                        entitykitty.setIsJumping(false);
                        entitykitty.getNavigator().clearPath();
                        entitykitty.setAttackTarget(null);
                    }
                }

                if ((entity instanceof MoCEntityWyvern)) {
                    MoCEntityWyvern entitywyvern = (MoCEntityWyvern) entity;
                    if (entitywyvern.getIsTamed() && entitywyvern.getRidingEntity() == null && !entitywyvern.isOnAir()) {
                        entitywyvern.setSitting(!entitywyvern.getIsSitting());
                        entitywyvern.setIsJumping(false);
                        entitywyvern.getNavigator().clearPath();
                        entitywyvern.setAttackTarget(null);
                    }
                }

                if ((entity instanceof MoCEntityPetScorpion)) {
                    MoCEntityPetScorpion petscorpion = (MoCEntityPetScorpion) entity;
                    if (petscorpion.getIsTamed() && petscorpion.getRidingEntity() == null) {
                        petscorpion.setSitting(!petscorpion.getIsSitting());
                        petscorpion.setIsJumping(false);
                        petscorpion.getNavigator().clearPath();
                        petscorpion.setAttackTarget(null);
                    }
                }

                if (entity instanceof MoCEntityOstrich) {
                    MoCEntityOstrich ostrich = (MoCEntityOstrich) entity;
                    if (ostrich.isBeingRidden() && ostrich.sprintCounter == 0) {
                        ostrich.sprintCounter = 1;
                    }

                    //toggles hiding of tamed ostriches
                    if (entityostrich.getIsTamed() && entityostrich.getRidingEntity() == null) {
                        entityostrich.setHiding(!entityostrich.getHiding());
                        entityostrich.setIsJumping(false);
                        entityostrich.getNavigator().clearPath();
                        entityostrich.setAttackTarget(null);
                    }
                }

                if (entity instanceof MoCEntityElephant) {
                    MoCEntityElephant entityelephant = (MoCEntityElephant) entity;

                if (entity instanceof MoCEntityElephant) {
                    MoCEntityElephant elephant = (MoCEntityElephant) entity;
                    if (elephant.isBeingRidden() && elephant.sprintCounter == 0) {
                        elephant.sprintCounter = 1;
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        target.playSound(MoCSoundEvents.ENTITY_GENERIC_WHIP, 0.5F, 2.0F / ((itemRand.nextFloat() * 0.4F) + 0.8F));
        return super.hitEntity(stack, target, attacker);
    }

    public void whipFX(Level world, BlockPos pos) {
        double d = pos.getX() + 0.5F;
        double d1 = pos.getY() + 1.0F;
        double d2 = pos.getZ() + 0.5F;
        double spread = 0.27D;
        double rise = 0.22D;

        world.addParticle(ParticleTypes.FLAME, d - spread, d1 + rise, d2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d + spread, d1 + rise, d2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d, d1 + rise, d2 - spread, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d, d1 + rise, d2 + spread, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d, d1, d2, 0.0D, 0.0D, 0.0D);

        world.addParticle(ParticleTypes.SMOKE, d - spread, d1 + rise, d2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d + spread, d1 + rise, d2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d, d1 + rise, d2 - spread, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d, d1 + rise, d2 + spread, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Damage modifier", (double) this.getAttackDamage(), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Speed modifier", (double) this.AttackSpeed - 4.0D, 0));
        }

        return multimap;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable Level worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".whip"));
    }
}
