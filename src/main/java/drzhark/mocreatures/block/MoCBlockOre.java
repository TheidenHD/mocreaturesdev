/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import drzhark.mocreatures.init.MoCBlocks;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class MoCBlockOre extends Block {

    public MoCBlockOre(AbstractBlock.Properties properties) {
        super(properties.sound(SoundType.STONE));
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }
    public int getExperience(Random rand) {
        if (this == MoCBlocks.ancientOre) {
            return MathHelper.nextInt(rand, 2, 5);
        } else if (this == MoCBlocks.wyvernDiamondOre) {
            return MathHelper.nextInt(rand, 4, 8);
        } else if (this == MoCBlocks.wyvernEmeraldOre) {
            return MathHelper.nextInt(rand, 4, 8);
        } else if (this == MoCBlocks.wyvernLapisOre) {
            return MathHelper.nextInt(rand, 3, 6);
        } else {
            return 0;
        }
    }

    @Override
    public int quantityDropped(Random random) {
        if (this == MoCBlocks.ancientOre) {
            return 1 + random.nextInt(2);
        }
        if (this == MoCBlocks.wyvernLapisOre) {
            return 4 + random.nextInt(5);
        } else {
            return 1;
        }
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getBlockState().getValidStates().iterator().next(), random, fortune)) {
            int i = random.nextInt(fortune + 2) - 1;

            if (i < 0) {
                i = 0;
            }

            return this.quantityDropped(random) * (i + 1);
        } else {
            return this.quantityDropped(random);
        }
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : new Random();

        if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this)) {
            int i = 0;

            if (this == MoCBlocks.ancientOre) {
                i = MathHelper.getInt(rand, 2, 5);
            } else if (this == MoCBlocks.wyvernDiamondOre) {
                i = MathHelper.getInt(rand, 4, 8);
            } else if (this == MoCBlocks.wyvernEmeraldOre) {
                i = MathHelper.getInt(rand, 4, 8);
            } else if (this == MoCBlocks.wyvernLapisOre) {
                i = MathHelper.getInt(rand, 3, 6);
            }

            return i;
        }

        return 0;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return this == MoCBlocks.wyvernLapisOre ? EnumDyeColor.BLUE.getDyeDamage() : 0;
    }
}
}
