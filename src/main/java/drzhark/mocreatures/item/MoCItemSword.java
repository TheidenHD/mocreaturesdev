/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Effects;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class MoCItemSword extends SwordItem {

    private int specialWeaponType = 0;

    public MoCItemSword(String name, IItemTier material) {
        this(name, 0, material);
    }

    public MoCItemSword(String name, int meta, IItemTier material) {
        super(material);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setRegistryName(MoCConstants.MOD_ID, name);
        this.setTranslationKey(name);
    }

    public MoCItemSword(String name, IItemTier material, int damageType) {
        this(name, material);
        this.specialWeaponType = damageType;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (MoCreatures.proxy.weaponEffects) {
            int timer = 10; // In seconds
            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    target.addPotionEffect(new EffectInstance(Effects.POISON, timer * 20, 1));
                    break;
                case 2: // Slowness
                    target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, timer * 20, 0));
                    break;
                case 3: // Fire
                    target.setFire(timer);
                    break;
                case 4: // Weakness (Nausea for players)
                    target.addPotionEffect(new EffectInstance(target instanceof PlayerEntity ? Effects.NAUSEA : Effects.WEAKNESS, timer * 20, 0));
                    break;
                case 5: // Wither (Blindness for players)
                    target.addPotionEffect(new EffectInstance(target instanceof PlayerEntity ? Effects.BLINDNESS : Effects.WITHER, timer * 20, 0));
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
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (MoCreatures.proxy.weaponEffects) {
            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    tooltip.add(new TranslationTextComponent("info.mocreatures.stingdefault1").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
                    break;
                case 2: // Slowness
                    tooltip.add(new TranslationTextComponent("info.mocreatures.stingdefault2").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
                    break;
                case 3: // Fire
                    tooltip.add(new TranslationTextComponent("info.mocreatures.stingdefault3").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
                    break;
                case 4: // Weakness (Nausea for players)
                    tooltip.add(new TranslationTextComponent("info.mocreatures.stingdefault4").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
                    break;
                case 5: // Wither (Blindness for players)
                    tooltip.add(new TranslationTextComponent("info.mocreatures.stingdefault5").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
                    break;
                default:
                    break;
            }
        }
    }
}
