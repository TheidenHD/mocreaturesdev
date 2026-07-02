//package drzhark.mocreatures.compat.tinkers.traits;
//
//import net.minecraft.world.entity.EntityLivingBase;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.nbt.CompoundTag;
//import slimeknights.tconstruct.library.tools.ToolNBT;
//import slimeknights.tconstruct.library.traits.AbstractTrait;
//import slimeknights.tconstruct.library.utils.TagUtil;
//import slimeknights.tconstruct.library.utils.TinkerUtil;
//
//public class TraitStingFire extends AbstractTrait {
//    protected final float damage;
//
//    public TraitStingFire(String identifier, int color, float damage) {
//        super(identifier, color);
//
//        this.damage = damage;
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
//            target.setFire(5);
//        }
//    }
//}
