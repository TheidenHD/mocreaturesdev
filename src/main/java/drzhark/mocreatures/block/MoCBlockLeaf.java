/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;


import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;

public class MoCBlockLeaf extends LeavesBlock {

    public MoCBlockLeaf(AbstractBlock.Properties properties) {
        super(properties.sound(SoundType.PLANT).notSolid().setAllowsSpawn(Blocks::allowsSpawnOnLeaves).setSuffocates(Blocks::isntSolid).setBlocksVision(Blocks::isntSolid));
    }
}
