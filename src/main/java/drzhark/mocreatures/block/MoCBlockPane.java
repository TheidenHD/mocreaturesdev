package drzhark.mocreatures.block;

import java.util.Random;

import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.world.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoCBlockPane extends BlockPane {
    private final boolean canDrop;
    private final boolean isTranslucent;

    public MoCBlockPane(Material material, SoundType soundType, boolean canDrop, boolean isTranslucent) {
        super(material, canDrop);
        this.setSoundType(soundType);
        this.setHarvestLevel("pickaxe", 0);
        this.canDrop = canDrop;
        this.isTranslucent = isTranslucent;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return !this.canDrop ? Items.AIR : super.getItemDropped(state, rand, fortune);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return this.isTranslucent ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT_MIPPED;
    }
}