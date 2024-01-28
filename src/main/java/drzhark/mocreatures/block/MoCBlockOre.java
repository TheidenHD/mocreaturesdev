/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;

public class MoCBlockOre extends Block {

    public MoCBlockOre(AbstractBlock.Properties properties) {
        super(properties.sound(SoundType.STONE));
    }
//    @Override //TODO ThiedenHD
//    public Item getItemDropped(BlockState state, Random rand, int fortune) {
//        if (this == MoCBlocks.ancientOre) {
//            if (rand.nextDouble() <= 0.3D) {
//                return Items.QUARTZ;
//            }
//            if (rand.nextDouble() <= 0.2D) {
//                return Items.COAL;
//            }
//            if (rand.nextDouble() <= 0.2D) {
//                return MoCItems.ancientSilverScrap;
//            }
//            if (rand.nextDouble() <= 0.02D) {
//                return Items.SKULL;
//            } else {
//                return Items.BONE;
//            }
//        }
//        if (this == MoCBlocks.wyvernDiamondOre) {
//            return Items.DIAMOND;
//        } else if (this == MoCBlocks.wyvernEmeraldOre) {
//            return Items.EMERALD;
//        } else if (this == MoCBlocks.wyvernLapisOre) {
//            return Items.DYE;
//        } else {
//            return Item.getItemFromBlock(this);
//        }
//    }
//
//    @Override
//    public int quantityDropped(Random random) {
//        if (this == MoCBlocks.ancientOre) {
//            return 1 + random.nextInt(2);
//        }
//        if (this == MoCBlocks.wyvernLapisOre) {
//            return 4 + random.nextInt(5);
//        } else {
//            return 1;
//        }
//    }
//
//    @Override
//    public int quantityDroppedWithBonus(int fortune, Random random) {
//        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getBlockState().getValidStates().iterator().next(), random, fortune)) {
//            int i = random.nextInt(fortune + 2) - 1;
//
//            if (i < 0) {
//                i = 0;
//            }
//
//            return this.quantityDropped(random) * (i + 1);
//        } else {
//            return this.quantityDropped(random);
//        }
//    }
//
//    @Override
//    public int getExpDrop(BlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
//        Random rand = world instanceof World ? ((World) world).rand : new Random();
//
//        if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this)) {
//            int i = 0;
//
//            if (this == MoCBlocks.ancientOre) {
//                i = MathHelper.getInt(rand, 2, 5);
//            } else if (this == MoCBlocks.wyvernDiamondOre) {
//                i = MathHelper.getInt(rand, 4, 8);
//            } else if (this == MoCBlocks.wyvernEmeraldOre) {
//                i = MathHelper.getInt(rand, 4, 8);
//            } else if (this == MoCBlocks.wyvernLapisOre) {
//                i = MathHelper.getInt(rand, 3, 6);
//            }
//
//            return i;
//        }
//
//        return 0;
//    }
}
