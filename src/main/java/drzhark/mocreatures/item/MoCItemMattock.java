/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import com.google.common.collect.Sets;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.MixinEnvironment;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class MoCItemMattock extends ItemPickaxe {
    private int specialWeaponType = 0;

    public MoCItemMattock(String name, Item.ToolMaterial material, float damage, float speed) {
        this(name, 0, material, damage, speed);
    }

    public MoCItemMattock(String name, int meta, Item.ToolMaterial material, float damage, float speed) {
        super(material);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setRegistryName(MoCConstants.MOD_ID, name);
        this.setTranslationKey(name);
        this.attackSpeed = -4.0F + speed;
    }

    public MoCItemMattock(String name, Item.ToolMaterial material, float damage, float speed, int damageType) {
        this(name, material, damage, speed);
        this.specialWeaponType = damageType;
    }

    public InteractionResult onItemUse(Player player, Level worldIn, BlockPos pos, InteractionHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getItemInHand(hand);

        BlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (facing != EnumFacing.DOWN && worldIn.isAirBlock(pos.up())) {
            if (!player.isCrouching() && block == Blocks.FARMLAND || player.isCrouching() && block == Blocks.GRASS_PATH) {
                this.setBlock(itemstack, player, worldIn, pos, Blocks.DIRT.defaultBlockState());
                return InteractionResult.SUCCESS;
            }
        }

        if (!player.isCrouching())
            return Items.IRON_SHOVEL.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        return Items.IRON_HOE.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    protected void setBlock(ItemStack stack, Player player, Level worldIn, BlockPos pos, BlockState state) {
        worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if (!worldIn.isClientSide()) {
            worldIn.setBlockState(pos, state, 11);
            stack.damageItem(1, player);
        }
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return Sets.newHashSet("pickaxe", "shovel");
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (MoCreatures.proxy.weaponEffects) {
            InteractionHand hand = attacker.getUsedItemHand() == null ? InteractionHand.MAIN_HAND : attacker.getUsedItemHand();
            int timer = 10; // In seconds
            int fire_aspect = 5 * EnchantmentHelper.getFireAspectModifier(attacker); // Fire Aspect
            int poisonous = 5 * EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("mod_lavacow:poisonous"), attacker.getItemInHand(hand)); // Poisonous (Fish's Undead Rising)

            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    target.addPotionEffect(new MobEffectInstance(MobEffects.POISON, (timer * 20) + poisonous, 1));
                    break;
                case 2: // Slowness
                    target.addPotionEffect(new MobEffectInstance(MobEffects.SLOWNESS, timer * 20, 0));
                    break;
                case 3: // Fire
                    target.setFire(timer + fire_aspect);
                    break;
                case 4: // Weakness (Nausea for players)
                    target.addPotionEffect(new MobEffectInstance(target instanceof Player ? MobEffects.NAUSEA : MobEffects.WEAKNESS, timer * 20, 0));
                    break;
                case 5: // Wither (Blindness for players)
                    target.addPotionEffect(new MobEffectInstance(target instanceof Player ? MobEffects.BLINDNESS : MobEffects.WITHER, timer * 20, 0));
                    break;
                default:
                    break;
            }
        }

        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    @SideOnly(MixinEnvironment.Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable Level worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (MoCreatures.proxy.weaponEffects) {
            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_dirt", 10));
                    break;
                case 2: // Slowness
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_frost", 10));
                    break;
                case 3: // Fire
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_fire", 10));
                    break;
                case 4: // Weakness (Nausea for players)
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_cave", 10));
                    break;
                case 5: // Wither (Blindness for players)
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_undead", 10));
                    break;
                default:
                    break;
            }
        }
    }
}
