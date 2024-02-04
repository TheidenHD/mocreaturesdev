/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class MoCBlockLog extends BlockLog {

    private final MapColor mapColor;
    public boolean flammable;

    public MoCBlockLog(MapColor mapColor, boolean flammable) {
        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
        this.mapColor = mapColor;
        this.flammable = flammable;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LOG_AXIS);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(LOG_AXIS).ordinal();
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        for (EnumAxis axis : EnumAxis.values()) {
            if (axis.ordinal() == meta) {
                return getDefaultState().withProperty(LOG_AXIS, axis);
            }
        }

        return getDefaultState();
    }

    @Override
    public MapColor getMapColor(BlockState state, IBlockReader world, BlockPos pos) {
        return mapColor;
    }

    public boolean isFlammable() {
        return flammable;
    }

    @Override
    public int getFlammability(IBlockReader world, BlockPos pos, Direction face) {
        if (isFlammable()) {
            return Blocks.LOG.getFlammability(world, pos, face);
        } else {
            return 0;
        }
    }

    @Override
    public int getFireSpreadSpeed(IBlockReader world, BlockPos pos, Direction face) {
        if (isFlammable()) {
            return Blocks.LOG.getFireSpreadSpeed(world, pos, face);
        } else {
            return 0;
        }
    }
}
