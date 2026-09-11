/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.InteractionHand;
import net.minecraft.util.InteractionResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityList;
import net.minecraft.world.entity.EntityLivingBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class MoCItemPetAmuletNew extends Item {

    public MoCItemPetAmuletNew(String name) {
        super(name);
        this.maxStackSize = 1;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide() && stack.hasTagCompound()) {
            Entity entity = EntityList.createEntityFromNBT(stack.getTagCompound(), world);
            if (entity != null) {
                double dist = 1D;
                double newPosX = player.getX() - (dist * Math.cos((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));
                double newPosY = player.getY();
                double newPosZ = player.getZ() - (dist * Math.sin((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));
                entity.moveTo(newPosX, newPosY, newPosZ, player.rotationYaw, 0.0F);
                world.spawnEntity(entity);
                stack.setTagCompound(null);
                return new ActionResult<>(InteractionResult.SUCCESS, stack);
            }
        }
        return new ActionResult<>(InteractionResult.PASS, stack);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, Player player, EntityLivingBase entity, InteractionHand hand) {
        if (!entity.world.isClientSide() && !stack.hasTagCompound()/* && entity.hasCustomName()*/) {
            CompoundTag entityNBT = new CompoundTag();
            entity.writeToNBT(entityNBT);
            entityNBT.setString("id", EntityList.getKey(entity.getClass()).toString());
            entityNBT.setString("name", entity.getName());
            stack.setTagCompound(entityNBT);
            entity.setDead();
            return true;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable Level worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("name")) {
            tooltip.add(TextFormatting.BLUE + stack.getTagCompound().getString("name"));
        }
    }
}
