/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class MoCItemLitterBox extends MoCItem {

    public MoCItemLitterBox(String name) {
        super(name);
        this.maxStackSize = 16;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCEntityLitterBox litterBox = new MoCEntityLitterBox(world);
            litterBox.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
            world.addEntity(litterBox);
            MoCTools.playCustomSound(litterBox, SoundEvents.BLOCK_WOOD_PLACE);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}
