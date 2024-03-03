/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class MoCBlockTallGrass extends BushBlock {

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public MoCBlockTallGrass(AbstractBlock.Properties properties) {
        super(properties.sound(SoundType.PLANT).doesNotBlockMovement());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XYZ;
    }

    @Override
    public boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block soil = worldIn.getBlockState(pos.down()).getBlock();
        return soil instanceof MoCBlockGrass || soil instanceof MoCBlockDirt || soil instanceof GrassBlock || net.minecraftforge.common.Tags.Blocks.DIRT.contains(soil) || soil instanceof FarmlandBlock;
    }
}
