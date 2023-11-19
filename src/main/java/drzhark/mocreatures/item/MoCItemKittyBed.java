/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class MoCItemKittyBed extends MoCItem {

    private int sheetType;

    public MoCItemKittyBed(String name) {
        super(name);
    }

    public MoCItemKittyBed(String name, int type) {
        this(name);
        this.sheetType = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, EnumHand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            MoCEntityKittyBed kittyBed = new MoCEntityKittyBed(world, this.sheetType);
            kittyBed.setPosition(player.posX, player.posY, player.posZ);
            world.spawnEntity(kittyBed);
            MoCTools.playCustomSound(kittyBed, SoundEvents.BLOCK_WOOD_PLACE);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
