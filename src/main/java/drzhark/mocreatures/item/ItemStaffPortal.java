/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemStaffPortal extends MoCItem {

    private int portalPosX;
    private int portalPosY;
    private int portalPosZ;
    private RegistryKey<World> portalDimension;

    public ItemStaffPortal(Item.Properties properties, String name) {
        super(properties.maxStackSize(1).maxDamage(3), name);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        final ItemStack stack = context.getPlayer().getHeldItem(context.getHand());
        if (context.getWorld().isRemote) {
            return ActionResultType.FAIL;
        }
        final boolean hasMending = EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING, stack) > 0;
        final boolean hasUnbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack) > 0;
        if (hasMending || hasUnbreaking) {
            String enchantments = "unbreaking";
            if (hasMending && hasUnbreaking) {
                enchantments = "mending, unbreaking";
            } else if (hasMending) {
                enchantments = "mending";
            }
            context.getPlayer().sendMessage(new TranslationTextComponent(MoCreatures.MOC_LOGO + TextFormatting.RED + " Detected illegal enchantment(s) '" + TextFormatting.GREEN + enchantments + TextFormatting.RED + "' on Staff Portal!\nThe item has been removed from your inventory."), context.getPlayer().getUniqueID());
            context.getPlayer().inventory.deleteStack(stack);
            return ActionResultType.SUCCESS;
        }

        if (stack.getTag() == null) {
            stack.setTag(new CompoundNBT());
        }

        CompoundNBT nbtcompound = stack.getTag();

        ServerPlayerEntity playerMP = (ServerPlayerEntity) context.getPlayer();
        if (context.getPlayer().getRidingEntity() != null || context.getPlayer().isBeingRidden()) {
            return ActionResultType.FAIL;
        } else {
            if (context.getPlayer().getEntityWorld().getDimensionKey() != MoCreatures.wyvernSkylandsDimensionID) {
                this.portalDimension = context.getPlayer().getEntityWorld().getDimensionKey();
                this.portalPosX = (int) context.getPlayer().getPosX();
                this.portalPosY = (int) context.getPlayer().getPosY();
                this.portalPosZ = (int) context.getPlayer().getPosZ();
                writeToNBT(nbtcompound);

                BlockPos var2 = playerMP.getServer().getWorld(MoCreatures.wyvernSkylandsDimensionID).END_SPAWN_AREA; //TODO TheidenHD

                if (var2 != null) {
                    playerMP.connection.setPlayerLocation(var2.getX(), var2.getY(), var2.getZ(), 0.0F, 0.0F);
                }
                // TODO TheidenHD
                //playerMP.getServer().getPlayerList().transferPlayerToDimension(playerMP, MoCreatures.wyvernSkylandsDimensionID, new MoCDirectTeleporter(playerMP.getServer().getWorld(MoCreatures.wyvernSkylandsDimensionID)));
                stack.damageItem(1, context.getPlayer(), (player) -> {
                    player.sendBreakAnimation(context.getHand());
                });
            } else {
                //on the WyvernLair!
                readFromNBT(nbtcompound);

                boolean foundSpawn = false;
                if (this.portalPosX == 0 && this.portalPosY == 0 && this.portalPosZ == 0) //dummy staff
                {
                    BlockPos var2 = playerMP.getServer().getWorld(World.OVERWORLD).getSpawnPoint();

                    for (int i1 = 0; i1 < 60; i1++) {
                        BlockState blockstate = playerMP.getServer().getWorld(World.OVERWORLD).getBlockState(context.getPos().add(0, i1, 0));
                        BlockState blockstate1 = playerMP.getServer().getWorld(World.OVERWORLD).getBlockState(context.getPos().add(0, i1 + 1, 0));

                        if (blockstate.getBlock() == Blocks.AIR && blockstate1.getBlock() == Blocks.AIR) {
                            playerMP.connection.setPlayerLocation(var2.getX(), (double) var2.getY() + i1 + 1, var2.getZ(), 0.0F, 0.0F);
                            if (MoCreatures.proxy.debug) {
                                System.out.println("MoC Staff teleporter found location at spawn");
                            }
                            foundSpawn = true;
                            break;
                        }
                    }

                    if (!foundSpawn) {
                        if (MoCreatures.proxy.debug) {
                            System.out.println("MoC Staff teleporter couldn't find an adequate teleport location at spawn");
                        }
                        return ActionResultType.FAIL;
                    }
                } else {
                    playerMP.connection.setPlayerLocation(this.portalPosX, (this.portalPosY) + 1D, this.portalPosZ, 0.0F, 0.0F);
                }

                stack.damageItem(1, context.getPlayer(), (player) -> {
                    player.sendBreakAnimation(context.getHand());
                });
                //TODO TheidenHD
                //playerMP.getServer().getPlayerList().transferPlayerToDimension(playerMP, this.portalDimension, new MoCDirectTeleporter(playerMP.getServer().getWorld(0)));
            }
            return ActionResultType.SUCCESS;
        }
    }

    /**
     * returns the action that specifies what animation to play when the items
     * are being used
     */
    @Override
    public UseAction getUseAction(ItemStack par1ItemStack) {
        return UseAction.BLOCK;
    }

    public void readFromNBT(CompoundNBT nbt) {
        this.portalPosX = nbt.getInt("portalPosX");
        this.portalPosY = nbt.getInt("portalPosY");
        this.portalPosZ = nbt.getInt("portalPosZ");
        this.portalDimension = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(nbt.getString("portalDimension")));
    }

    public void writeToNBT(CompoundNBT nbt) {
        nbt.putInt("portalPosX", this.portalPosX);
        nbt.putInt("portalPosY", this.portalPosY);
        nbt.putInt("portalPosZ", this.portalPosZ);
        nbt.putString("portalDimension", this.portalDimension.getLocation().toString());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}
