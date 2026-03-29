package drzhark.mocreatures.compat.tinkers.traits.armor;

import c4.conarm.lib.traits.AbstractArmorTrait;
import drzhark.mocreatures.MoCConstants;
import net.minecraft.world.entity.player.Player;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.level.Level;

public class TraitSpeedDemonArmor extends AbstractArmorTrait {
    public TraitSpeedDemonArmor() {
        super(MoCConstants.MOD_ID + "." + "speed_demon", 0x8E8F93);
    }

    @Override
    public void onAbilityTick(int level, Level world, Player player) {
        if (player.isSprinting()) {
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 2, level - 1, true, false));
        }
    }
}
