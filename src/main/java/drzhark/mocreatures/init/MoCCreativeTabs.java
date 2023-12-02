/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoCCreativeTabs extends ItemGroup {

    public MoCCreativeTabs(int length, String name) {
        super(length, name);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack createIcon() {
        return new ItemStack(MoCItems.amuletfairyfull, 1);
    }

    /**
     * only shows items which have tabToDisplayOn == this
     */
    @OnlyIn(Dist.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> items) {
        for (Item item : Item.REGISTRY) {
            if (item == MoCItems.mocegg) {
                continue;
            }
            item.getSubItems(this, items);
        }
        // show eggs last
        MoCItems.mocegg.getSubItems(this, items);
    }
}
