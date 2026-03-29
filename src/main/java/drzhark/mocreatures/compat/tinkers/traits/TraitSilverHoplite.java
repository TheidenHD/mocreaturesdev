package drzhark.mocreatures.compat.tinkers.traits;

import java.util.List;

import com.google.common.collect.ImmutableList;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.world.entity.EntityLivingBase;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitSilverHoplite extends AbstractTrait {
    protected final float bonus;

    public TraitSilverHoplite(float bonus) {
        super(MoCConstants.MOD_ID + "." + "silver_hoplite", 0x8E8F93);

        this.bonus = bonus;
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        // Bonus damage while running
        if (player.isSprinting()) {
            newDamage += bonus;
        }

        return newDamage;
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, CompoundTag modifierTag) {
        String loc = String.format(LOC_Extra, getIdentifier());

        return ImmutableList.of(Util.translateFormatted(loc, Util.df.format(bonus)));
    }
}
