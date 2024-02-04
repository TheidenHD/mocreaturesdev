/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.SoundType;
import net.minecraftforge.common.ToolType;


public class MoCBlockGlass extends AbstractGlassBlock {

    public MoCBlockGlass(AbstractBlock.Properties properties) {
        super(properties.sound(SoundType.GLASS).harvestLevel(0).harvestTool(ToolType.PICKAXE));
    }
}
