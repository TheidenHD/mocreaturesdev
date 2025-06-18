/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class MoCBlockGlass extends GlassBlock {

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
