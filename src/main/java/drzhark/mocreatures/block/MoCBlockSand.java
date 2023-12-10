/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;

public class MoCBlockSand extends BlockFalling {

    private final MapColor mapColor;

    public MoCBlockSand(MapColor mapColor) {
        super(Material.GROUND);
        this.mapColor = mapColor;
        this.setSoundType(SoundType.SAND);
        this.setHarvestLevel("shovel", 0);
    }

    @Override
    public MapColor getMapColor(BlockState state, IBlockAccess world, BlockPos pos) {
        return mapColor;
    }

    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState state) {
        return 12107978;
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockAccess world, BlockPos pos, Direction direction, IPlantable plantable) {
        BlockState plant = plantable.getPlant(world, pos.offset(direction));

        if (plant.getBlock() == Blocks.CACTUS || plant.getBlock() == Blocks.DEADBUSH) {
            return this == MoCBlocks.silverSand;
        }

        return super.canSustainPlant(state, world, pos, direction, plantable);
    }
}
