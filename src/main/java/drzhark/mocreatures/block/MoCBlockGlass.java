/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraftforge.common.ToolType;


public class MoCBlockGlass extends BlockBreakable {
    private final boolean isTranslucent;

    public MoCBlockGlass(boolean isTranslucent) {
        super(Material.GLASS, false);
        this.setSoundType(SoundType.GLASS);
        this.setHarvestLevel("pickaxe", 0);
        this.isTranslucent = isTranslucent;
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return this.isTranslucent ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
}
