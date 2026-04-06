package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.item.EnumRarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class MoCItem extends Item {
    protected String tooltip = null;

    public Item(String name) {
        this(name, 0, false);
    }

    public Item(String name, int meta, boolean hasTooltip) {
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setRegistryName(MoCConstants.MOD_ID, name);
        this.setTranslationKey(name);

        if (hasTooltip) {
            this.tooltip = "info." + MoCConstants.MOD_ID + "." + name;
        }
    }

    public EnumRarity getRarity(ItemStack stack) {
        if (stack.getItem() == MoCItems.horsearmorcrystal) {
            return EnumRarity.RARE;
        }

        return EnumRarity.COMMON;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable Level worldIn, List<String> list, ITooltipFlag flag) {
        if (tooltip != null) {
            list.add(TextFormatting.WHITE + I18n.format(tooltip));
        }
    }
}
