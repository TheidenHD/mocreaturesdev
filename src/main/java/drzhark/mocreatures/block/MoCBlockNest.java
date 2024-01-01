/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MoCBlockNest extends Block {

    public MoCBlockNest() {
        super(Material.GRASS, MapColor.YELLOW);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.onLivingFall(fallDistance, 0.2F);
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, Direction face) {
        return Blocks.HAY_BLOCK.getFlammability(world, pos, face);
    }

    public MoCBlockNest(Material material, MapColor mapColor) {
        super(material, mapColor);
    }
}
