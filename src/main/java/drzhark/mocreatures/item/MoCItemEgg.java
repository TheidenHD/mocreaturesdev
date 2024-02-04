/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MoCItemEgg extends MoCItem {

    public MoCItemEgg(Item.Properties properties, String name) {
        super(properties.maxStackSize(16), name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!player.abilities.isCreativeMode) stack.shrink(1);
        if (!world.isRemote && player.isOnGround()) {
            int i = 0;
            if (i == 30) {
                i = 31; //for ostrich eggs. placed eggs become stolen eggs.
            }
            MoCEntityEgg entityegg = MoCEntities.EGG.create(world);
            entityegg.setEggType(i);
            entityegg.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
            player.world.addEntity(entityegg);
            entityegg.setMotion(entityegg.getMotion().add((world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F, world.rand.nextFloat() * 0.05F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F));
        }
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

//    @Override //TODO TheidenHD
//    @OnlyIn(Dist.CLIENT)
//    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
//        if (!this.isInCreativeTab(tab)) {
//            return;
//        }
//
//        // Fish eggs
//        int length = MoCEntityFishy.fishNames.length;
//        for (int i = 0; i < length; i++) { // fishy
//            items.add(new ItemStack(this, 1, i));
//        }
//        length = 80 + MoCEntitySmallFish.fishNames.length;
//        for (int i = 80; i < length; i++) { // small fish
//            items.add(new ItemStack(this, 1, i));
//        }
//        length = 70 + MoCEntityMediumFish.fishNames.length;
//        for (int i = 70; i < length; i++) { // medium fish
//            items.add(new ItemStack(this, 1, i));
//        }
//        items.add(new ItemStack(this, 1, 90)); // piranha
//        items.add(new ItemStack(this, 1, 11)); // shark
//        for (int i = 21; i < 28; i++) { // snakes
//            items.add(new ItemStack(this, 1, i));
//        }
//        items.add(new ItemStack(this, 1, 30)); // ostrich
//        items.add(new ItemStack(this, 1, 31)); // stolen ostrich egg
//        items.add(new ItemStack(this, 1, 33)); // komodo
//        for (int i = 41; i < 46; i++) { // scorpions
//            items.add(new ItemStack(this, 1, i));
//        }
//        for (int i = 50; i < 67; i++) { // wyverns, manticores
//            items.add(new ItemStack(this, 1, i));
//        }
//    }

    @Override
    public String getTranslationKey(ItemStack itemstack) {
        return getTranslationKey() + "." ; //TODO TheidenHD
    }
}
