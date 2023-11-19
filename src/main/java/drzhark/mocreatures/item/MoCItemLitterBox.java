/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class MoCItemLitterBox extends MoCItem {

    public MoCItemLitterBox(String name) {
        super(name);
        this.maxStackSize = 16;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, EnumHand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            MoCEntityLitterBox litterBox = new MoCEntityLitterBox(world);
            litterBox.setPosition(player.posX, player.posY, player.posZ);
            world.spawnEntity(litterBox);
            MoCTools.playCustomSound(litterBox, SoundEvents.BLOCK_WOOD_PLACE);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
