package drzhark.mocreatures.item;

import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class MoCItemEgg extends MoCItem {

    public MoCItemEgg(Item.Properties properties) {
        super(properties.stacksTo(16));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!player.abilities.isCreativeMode) stack.shrink(1);
        if (!world.isRemote && player.isOnGround()) {
            int i = 0;
            if (i == 30) {
                i = 31; // For ostrich eggs. Placed eggs become stolen eggs.
            }
            MoCEntityEgg entityEgg = MoCEntities.EGG.create(world);
            assert entityEgg != null;
            entityEgg.setEggType(eggType);
            entityEgg.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
            player.world.addEntity(entityEgg);

            entityEgg.setMotion(entityEgg.getMotion().add((world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F, world.rand.nextFloat() * 0.05F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F));

            System.out.println("[DEBUG] Placing egg with type: " + eggType);
        }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!this.isInCreativeTab(tab)) {
            return;
        }

        for (int i = 0; i < 11; i++) { // Fishies
            items.add(new ItemStack(this, 1, i));
        }        

        items.add(new ItemStack(this, 1, 11)); // Shark
        
        for (int i = 21; i < 29; i++) { // Snakes
            items.add(new ItemStack(this, 1, i));
        }
        
        items.add(new ItemStack(this, 1, 30)); // Ostrich
        items.add(new ItemStack(this, 1, 31)); // Ostrich (Stolen)
        items.add(new ItemStack(this, 1, 33)); // Komodo Dragon
        
        for (int i = 41; i < 46; i++) { // Scorpions
            items.add(new ItemStack(this, 1, i));
        }
        
        for (int i = 50; i < 62; i++) { // Wyverns
            items.add(new ItemStack(this, 1, i));
        }
        
        for (int i = 62; i < 67; i++) { // Manticores
            items.add(new ItemStack(this, 1, i));
        }
        
        for (int i = 70; i < 73; i++) { // Medium Fish
            items.add(new ItemStack(this, 1, i));
        }
        
        for (int i = 80; i < 87; i++) { // Small Fish
            items.add(new ItemStack(this, 1, i));
        }
        
        items.add(new ItemStack(this, 1, 90)); // Piranha
    }

    @Override
    public String getTranslationKey(ItemStack itemstack) {
        return getTranslationKey() + "." + itemstack.getItemDamage();
    }
}
