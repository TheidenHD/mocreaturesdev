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
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
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
            EnumHand hand = attacker.getActiveHand() == null ? EnumHand.MAIN_HAND : attacker.getActiveHand();
            int timer = 8; // In seconds
            int fire_aspect = 4 * EnchantmentHelper.getFireAspectModifier(attacker); // Fire Aspect
            int poisonous = 4 * EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("mod_lavacow:poisonous"), attacker.getHeldItem(hand)); // Poisonous (Fish's Undead Rising)

            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    target.addPotionEffect(new PotionEffect(MobEffects.POISON, (timer * 20) + poisonous, 1));
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
