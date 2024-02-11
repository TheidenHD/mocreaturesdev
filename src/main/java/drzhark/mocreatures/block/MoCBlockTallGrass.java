/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class MoCBlockTallGrass extends TallGrassBlock {

    public MoCBlockTallGrass(AbstractBlock.Properties properties) {
        super(properties.sound(SoundType.PLANT));
    }

    @Override
    public boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block soil = worldIn.getBlockState(pos.down()).getBlock();
        return soil instanceof MoCBlockGrass || soil instanceof MoCBlockDirt || soil instanceof GrassBlock || net.minecraftforge.common.Tags.Blocks.DIRT.contains(soil) || soil instanceof FarmlandBlock;
    }
}
