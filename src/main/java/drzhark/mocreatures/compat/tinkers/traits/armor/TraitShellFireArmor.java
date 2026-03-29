package drzhark.mocreatures.compat.tinkers.traits.armor;

import c4.conarm.lib.traits.AbstractArmorTrait;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

public class TraitShellFireArmor extends AbstractArmorTrait {
    protected final float chance;
    protected final Potion playerEffect;

    public TraitShellFireArmor(String identifier, int color, float chance, Potion playerEffect) {
        super(identifier, color);

        this.chance = chance;
        this.playerEffect = playerEffect;
    }

    @Override
    public float onDamaged(ItemStack armor, Player player, DamageSource source, float damage, float newDamage, LivingDamageEvent event) {
        if (random.nextFloat() <= chance) {
            // Completely cancel out the damage
            event.setCanceled(true);
            player.world.playSound(null, player.getPosition(), MoCSoundEvents.ENTITY_GENERIC_CLANG, SoundCategory.PLAYERS, 1.0F, 0.5F / (player.world.rand.nextFloat() * 0.4F + 1.2F));

            // Inflict fire on the target (15 seconds) and inflict positive effect on the wielder (30 seconds)
            if (!player.world.isRemote) {
                source.getTrueSource().setFire(15);

                if (player instanceof Player) {
                    player.addPotionEffect(new PotionEffect(playerEffect, 30 * 20, 0));
                    TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ARMOR, player, (int) damage);
                }
            }
        }

        return newDamage;
    }
}
