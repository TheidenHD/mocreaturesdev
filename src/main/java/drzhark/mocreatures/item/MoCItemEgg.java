/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import drzhark.mocreatures.entity.aquatic.MoCEntityMediumFish;
import drzhark.mocreatures.entity.aquatic.MoCEntitySmallFish;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoCItemEgg extends MoCItem {

    public MoCItemEgg(String name) {
        super(name);
        this.maxStackSize = 16;
        setHasSubtypes(true);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!player.capabilities.isCreativeMode) stack.shrink(1);
        if (!world.isRemote && player.onGround) {
            int i = stack.getItemDamage();
            if (i == 30) {
                i = 31; //for ostrich eggs. placed eggs become stolen eggs.
            }
            MoCEntityEgg entityegg = new MoCEntityEgg(world, i);
            entityegg.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
            player.world.addEntity(entityegg);
            entityegg.getMotion().getY() += world.rand.nextFloat() * 0.05F;
            entityegg.getMotion().getX() += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F;
            entityegg.getMotion().getZ() += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F;
        }
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!this.isInCreativeTab(tab)) {
            return;
        }

        // Fish eggs
        int length = MoCEntityFishy.fishNames.length;
        for (int i = 0; i < length; i++) { // fishy
            items.add(new ItemStack(this, 1, i));
        }
        length = 80 + MoCEntitySmallFish.fishNames.length;
        for (int i = 80; i < length; i++) { // small fish
            items.add(new ItemStack(this, 1, i));
        }
        length = 70 + MoCEntityMediumFish.fishNames.length;
        for (int i = 70; i < length; i++) { // medium fish
            items.add(new ItemStack(this, 1, i));
        }
        items.add(new ItemStack(this, 1, 90)); // piranha
        items.add(new ItemStack(this, 1, 11)); // shark
        for (int i = 21; i < 28; i++) { // snakes
            items.add(new ItemStack(this, 1, i));
        }
        items.add(new ItemStack(this, 1, 30)); // ostrich
        items.add(new ItemStack(this, 1, 31)); // stolen ostrich egg
        items.add(new ItemStack(this, 1, 33)); // komodo
        for (int i = 41; i < 46; i++) { // scorpions
            items.add(new ItemStack(this, 1, i));
        }
        for (int i = 50; i < 67; i++) { // wyverns, manticores
            items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public String getTranslationKey(ItemStack itemstack) {
        return getTranslationKey() + "." + itemstack.getItemDamage();
    }
}
