package drzhark.mocreatures.compat.tinkers.traits.armor;

import java.util.List;

import com.google.common.collect.ImmutableList;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import net.minecraft.world.entity.EntityLivingBase;
import net.minecraft.world.entity.animal.EntityWaterMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitThresherArmor extends AbstractArmorTrait {
    private static final float MODIFIER = 0.4F;

    public TraitThresherArmor() {
        super(MoCConstants.MOD_ID + "." + "thresher", 0xB7B699);
    }

    private double calcAttack(ItemStack armor) {
        int durability = ToolHelper.getCurrentDurability(armor);
        int maxDurability = ToolHelper.getMaxDurability(armor);

        return Math.log((maxDurability - durability) / 72.0D + 1.0D) * 2;
    }

    @Override
    public float onHurt(ItemStack armor, Player player, DamageSource source, float damage, float newDamage, LivingHurtEvent event) {
        if (source.getImmediateSource() instanceof EntityLivingBase) {
            attackEntitySecondary(new EntityDamageSource("thresher", player).setIsThornsDamage(), (float) calcAttack(armor), source.getImmediateSource(), true, false, false);
        }

        return newDamage;
    }

    @Override
    public ArmorModifications getModifications(Player player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source.getImmediateSource() instanceof EntityWaterMob || source.getImmediateSource() instanceof MoCEntityAquatic) {
            return super.getModifications(player, mods, armor, source, damage, slot);
        }

        mods.addEffectiveness(MODIFIER);
        return super.getModifications(player, mods, armor, source, damage, slot);
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, CompoundTag modifierTag) {
        String damage = String.format(LOC_Extra + ".damage", getModifierIdentifier());
        String protection = String.format(LOC_Extra + ".protection", getModifierIdentifier());

        return ImmutableList.of(
                Util.translateFormatted(damage, Util.df.format(calcAttack(tool))),
                Util.translateFormatted(protection, Util.dfPercent.format(MODIFIER))
        );
    }
}
