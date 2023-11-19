/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

import java.util.Random;

public class MoCBlockGlass extends BlockBreakable {

    public MoCBlockGlass() {
        super(Material.GLASS, false);
        this.setSoundType(SoundType.GLASS);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
}
