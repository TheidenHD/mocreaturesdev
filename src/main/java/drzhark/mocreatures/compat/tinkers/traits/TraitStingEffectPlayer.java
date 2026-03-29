package drzhark.mocreatures.compat.tinkers.traits;

import net.minecraft.world.entity.EntityLivingBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class TraitStingEffectPlayer extends AbstractTrait {
    protected final float damage;
    protected final Potion effect;
    protected final Potion playerEffect;

    public TraitStingEffectPlayer(String identifier, int color, float damage, Potion effect, Potion playerEffect) {
        super(identifier, color);

        this.damage = damage;
        this.effect = effect;
        this.playerEffect = playerEffect;
    }

    @Override
    public void applyEffect(CompoundTag rootCompound, CompoundTag modifierTag) {
        // Apply bonus damage if it hasn't been applied yet
        if (!TinkerUtil.hasTrait(rootCompound, identifier)) {
            ToolNBT data = TagUtil.getToolStats(rootCompound);
            data.attack += damage;
            TagUtil.setToolTag(rootCompound, data.get());
        }

        super.applyEffect(rootCompound, modifierTag);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit && target.isEntityAlive()) {
            // Don't set the normal effect on players
            if (!(target instanceof Player)) {
                target.addPotionEffect(new PotionEffect(effect, 20 * 5));
            }

            // Set our alternative effect for players
            if (target instanceof Player) {
                target.addPotionEffect(new PotionEffect(playerEffect, 20 * 5));
            }
        }
    }
}