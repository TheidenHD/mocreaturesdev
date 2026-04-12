package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class MoCItemSword extends SwordItem {

    private final int specialWeaponType;

    public MoCItemSword(Item.Properties properties, Tier material) {
        super(material, 3, -2.4F, properties);
        this.specialWeaponType = 0;
    }

    public MoCItemSword(Item.Properties properties, Tier material, int damageType) {
        super(material, 3, -2.4F, properties);
        this.specialWeaponType = damageType;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (MoCreatures.proxy.weaponEffects) {
            InteractionHand hand = attacker.getUsedItemHand() == null ? InteractionHand.MAIN_HAND : attacker.getUsedItemHand();
            int timer = 8; // In seconds
            int fire_aspect = 4 * EnchantmentHelper.getFireAspect(attacker); // Fire Aspect
            int poisonous = 4 * EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("mod_lavacow:poisonous"), attacker.getItemInHand(hand)); // Poisonous (Fish's Undead Rising)

            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, (timer * 20) + poisonous, 1));
                    break;
                case 2: // Slowness
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, timer, 0));
                    break;
                case 3: // Fire
                    target.setFire(timer + fire_aspect);
                    break;
                case 4: // Weakness (Nausea for players)
                    target.addEffect(new MobEffectInstance(
                            target instanceof Player ? MobEffects.CONFUSION : MobEffects.WEAKNESS, timer, 0));
                    break;
                case 5: // Wither (Blindness for players)
                    target.addEffect(new MobEffectInstance(
                            target instanceof Player ? MobEffects.BLINDNESS : MobEffects.WITHER, timer, 0));
                    break;
            }
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (MoCreatures.proxy.weaponEffects) {
            String translationKey = null;
            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_dirt", 8));
                    break;
                case 2: // Slowness
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_frost", 8));
                    break;
                case 3: // Fire
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_fire", 8));
                    break;
                case 4: // Weakness (Nausea for players)
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_cave", 8));
                    break;
                case 5: // Wither (Blindness for players)
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_undead", 8));
                    break;
                default:
                    break;
            }
            if (translationKey != null) {
                tooltip.add(Component.translatable(translationKey).withStyle(ChatFormatting.BLUE));
            }
        }
    }
}
