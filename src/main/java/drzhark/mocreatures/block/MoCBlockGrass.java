/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class MoCBlockGrass extends Block implements IGrowable {

    public MoCBlockGrass(MapColor mapColor) {
        super(Material.GRASS, mapColor);
        setTickRandomly(true);
        this.setSoundType(SoundType.PLANT);
        this.setHarvestLevel("shovel", 0);
    }

    public MoCBlockGrass(Material material, MapColor mapColor) {
        super(material, mapColor);
    }

    public void updateTick(World worldIn, BlockPos pos, BlockState state, Random rand) {
        if (!worldIn.isRemote) {
            if (!worldIn.isAreaLoaded(pos, 3)) return;
            if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2) {
                if (this == MoCBlocks.wyvgrass) worldIn.setBlockState(pos, MoCBlocks.wyvdirt.getDefaultState());
            } else {
                if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
                    for (int i = 0; i < 4; ++i) {
                        BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                        if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
                            return;
                        }

                        BlockState iblockstate = worldIn.getBlockState(blockpos.up());
                        BlockState iblockstate1 = worldIn.getBlockState(blockpos);

                        if (iblockstate1.getBlock() == MoCBlocks.wyvdirt && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2) {
                            worldIn.setBlockState(blockpos, MoCBlocks.wyvgrass.getDefaultState());
                        }
                    }
                }
            }
        }
    }

    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        if (this == MoCBlocks.wyvgrass) {
            return MoCBlocks.wyvdirt.getItemDropped(MoCBlocks.wyvdirt.getDefaultState(), rand, fortune);
        } else {
            return Item.getItemFromBlock(this);
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.up();

        if (this == MoCBlocks.wyvgrass) {
            for (int i = 0; i < 128; ++i) {
                BlockPos blockpos1 = blockpos;
                int j = 0;

                while (true) {
                    if (j >= i / 16) {
                        if (worldIn.isAirBlock(blockpos1)) {
                            if (rand.nextInt(8) == 0) {
                                worldIn.getBiome(blockpos1).plantFlower(worldIn, rand, blockpos1);
                            } else {
                                BlockState iblockstate1 = MoCBlocks.tallWyvgrass.getDefaultState();

                                if (((MoCBlockTallGrass) MoCBlocks.tallWyvgrass).canBlockStay(worldIn, blockpos1, iblockstate1)) {
                                    worldIn.setBlockState(blockpos1, iblockstate1, 3);
                                }
                            }
                        }

                        break;
                    }

                    blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

                    if (worldIn.getBlockState(blockpos1.down()).getBlock() != MoCBlocks.wyvgrass || worldIn.getBlockState(blockpos1).isNormalCube()) {
                        break;
                    }

                    ++j;
                }
            }
        }
    }
}
