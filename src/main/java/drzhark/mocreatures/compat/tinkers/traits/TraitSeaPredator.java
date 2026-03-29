package drzhark.mocreatures.compat.tinkers.traits;

import java.util.List;

import com.google.common.collect.ImmutableList;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import net.minecraft.world.entity.EntityLivingBase;
import net.minecraft.world.entity.animal.EntityWaterMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitSeaPredator extends AbstractTrait {
    protected final float bonus;

    public TraitSeaPredator(float bonus) {
        super(MoCConstants.MOD_ID + "." + "sea_predator", 0xB7B699);

        this.bonus = bonus;
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        // Bonus damage to sea creatures or while under rain or water
        if (target instanceof EntityWaterMob || target instanceof MoCEntityAquatic || player.isWet()) {
            newDamage += damage * bonus;
        }

        return newDamage;
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, CompoundTag modifierTag) {
        String loc = String.format(LOC_Extra, getModifierIdentifier());

        return ImmutableList.of(Util.translateFormatted(loc, Util.dfPercent.format(bonus)));
    }
}
