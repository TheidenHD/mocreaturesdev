/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class MoCItemAxe extends ItemAxe {

    private int specialWeaponType = 0;

    public MoCItemAxe(String name, Item.ToolMaterial material, float damage, float speed) {
        this(name, 0, material, damage, speed);
    }

    public MoCItemAxe(String name, int meta, Item.ToolMaterial material, float damage, float speed) {
        super(material, damage - 1.0F, speed - 4.0F);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setRegistryName(MoCConstants.MOD_ID, name);
        this.setTranslationKey(name);
    }

    public MoCItemAxe(String name, Item.ToolMaterial material, float damage, float speed, int damageType) {
        this(name, material, damage, speed);
        this.specialWeaponType = damageType;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (MoCreatures.proxy.weaponEffects) {
            int timer = 15; // In seconds
            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    target.addPotionEffect(new PotionEffect(MobEffects.POISON, timer * 20, 1));
                    break;
                case 2: // Slowness
                    target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, timer * 20, 0));
                    break;
                case 3: // Fire
                    target.setFire(timer);
                    break;
                case 4: // Weakness (Nausea for players)
                    target.addPotionEffect(new PotionEffect(target instanceof EntityPlayer ? MobEffects.NAUSEA : MobEffects.WEAKNESS, timer * 20, 0));
                    break;
                case 5: // Wither (Blindness for players)
                    target.addPotionEffect(new PotionEffect(target instanceof EntityPlayer ? MobEffects.BLINDNESS : MobEffects.WITHER, timer * 20, 0));
                    break;
                default:
                    break;
            }
        }

        stack.damageItem(2, attacker);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (MoCreatures.proxy.weaponEffects) {
            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    tooltip.add(new TextComponentTranslation("info.mocreatures.stingaxe1").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
                    break;
                case 2: // Slowness
                    tooltip.add(new TextComponentTranslation("info.mocreatures.stingaxe2").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
                    break;
                case 3: // Fire
                    tooltip.add(new TextComponentTranslation("info.mocreatures.stingaxe3").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
                    break;
                case 4: // Weakness (Nausea for players)
                    tooltip.add(new TextComponentTranslation("info.mocreatures.stingaxe4").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
                    break;
                case 5: // Wither (Blindness for players)
                    tooltip.add(new TextComponentTranslation("info.mocreatures.stingaxe5").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
                    break;
                default:
                    break;
            }
        }
    }
}
