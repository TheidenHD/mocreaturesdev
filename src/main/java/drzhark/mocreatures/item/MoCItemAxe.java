package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class MoCItemAxe extends AxeItem {

    private final int specialWeaponType;

    public MoCItemAxe(Item.Properties properties, Tier material, float damage, float speed) {
        super(material, damage - 1.0F, speed - 4.0F, properties);
        this.specialWeaponType = 0;
    }

    public MoCItemAxe(Item.Properties properties, Tier material, float damage, float speed, int damageType) {
        super(material, damage - 1.0F, speed - 4.0F, properties);
        this.specialWeaponType = damageType;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (MoCreatures.proxy.weaponEffects) {
            EnumHand hand = attacker.getActiveHand() == null ? EnumHand.MAIN_HAND : attacker.getActiveHand();
            int timer = 10; // In seconds
            int fire_aspect = 5 * EnchantmentHelper.getFireAspectModifier(attacker); // Fire Aspect
            int poisonous = 5 * EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("mod_lavacow:poisonous"), attacker.getHeldItem(hand)); // Poisonous (Fish's Undead Rising)

            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    target.addPotionEffect(new PotionEffect(MobEffects.POISON, (timer * 20) + poisonous, 1));
                    break;
                case 2: // Slowness
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, timer * 20, 0));
                    break;
                case 3: // Fire
                    target.setFire(timer + fire_aspect);
                    break;
                case 4: // Weakness or Nausea
                    if (target instanceof Player) {
                        target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, timer * 20, 0));
                    } else {
                        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, timer * 20, 0));
                    }
                    break;
                case 5: // Wither or Blindness
                    if (target instanceof Player) {
                        target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, timer * 20, 0));
                    } else {
                        target.addEffect(new MobEffectInstance(MobEffects.WITHER, timer * 20, 0));
                    }
                    break;
                default:
                    break;
            }
        }

        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
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
