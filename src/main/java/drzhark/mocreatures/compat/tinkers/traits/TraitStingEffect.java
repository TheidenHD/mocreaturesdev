//package drzhark.mocreatures.compat.tinkers.traits;
//
//import net.minecraft.world.entity.EntityLivingBase;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.potion.Potion;
//import net.minecraft.potion.MobEffectInstance;
//import slimeknights.tconstruct.library.tools.ToolNBT;
//import slimeknights.tconstruct.library.traits.AbstractTrait;
//import slimeknights.tconstruct.library.utils.TagUtil;
//import slimeknights.tconstruct.library.utils.TinkerUtil;
//
//public class TraitStingEffect extends AbstractTrait {
//    protected final float damage;
//    protected final Potion effect;
//    protected final int amplifier;
//
//    public TraitStingEffect(String identifier, int color, float damage, Potion effect, int amplifier) {
//        super(identifier, color);
//
//        this.damage = damage;
//        this.effect = effect;
//        this.amplifier = amplifier;
//    }
//
//    @Override
//    public void applyEffect(CompoundTag rootCompound, CompoundTag modifierTag) {
//        // Apply bonus damage if it hasn't been applied yet
//        if (!TinkerUtil.hasTrait(rootCompound, identifier)) {
//            ToolNBT data = TagUtil.getToolStats(rootCompound);
//            data.attack += damage;
//            TagUtil.setToolTag(rootCompound, data.get());
//        }
//        super.applyEffect(rootCompound, modifierTag);
//    }
//
//    @Override
//    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
//        if (wasHit && target.isEntityAlive()) {
//            target.addPotionEffect(new MobEffectInstance(effect, 20 * 5, amplifier));
//        }
//    }
//}
