/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.hunter.MoCEntityBigCat;
import drzhark.mocreatures.entity.neutral.MoCEntityKitty;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.entity.tameable.MoCPetData;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAppear;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class MoCItemPetAmulet extends MoCItem {

    private String name;
    private float health;
    private int age;
    private int creatureType;
    private String spawnClass;
    private String ownerName;
    private UUID ownerUniqueId;
    private int amuletType;
    private boolean adult;
    private int PetId;

    public MoCItemPetAmulet(String name) {
        super(name);
        this.maxStackSize = 1;
        setHasSubtypes(true);
    }

    public MoCItemPetAmulet(String name, int type) {
        this(name);
        this.amuletType = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        double dist = 1D;
        double newPosY = player.getPosY();
        double newPosX = player.getPosX() - (dist * Math.cos((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));
        double newPosZ = player.getPosZ() - (dist * Math.sin((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));

        ItemStack emptyAmulet = new ItemStack(MoCItems.fishnet, 1, 0);
        if (this.amuletType == 1) {
            emptyAmulet = new ItemStack(MoCItems.petamulet, 1, 0);
        }

        if (!world.isRemote) {
            initAndReadNBT(stack);
            if (this.spawnClass.isEmpty()) {
                return new ActionResult<>(ActionResultType.SUCCESS, stack);
            }
            try {
                this.spawnClass = this.spawnClass.replace(MoCConstants.MOD_PREFIX, "").toLowerCase();
                if (this.spawnClass.equalsIgnoreCase("MoCHorse")) {
                    this.spawnClass = "WildHorse";
                } else if (this.spawnClass.equalsIgnoreCase("PolarBear")) {
                    this.spawnClass = "WildPolarBear";
                }

                if (this.spawnClass.equalsIgnoreCase("Ray")) {
                    switch (this.creatureType) {
                        case 1:
                            this.spawnClass = "MantaRay";
                            break;
                        case 2:
                            this.spawnClass = "StingRay";
                            break;
                    }
                }
                LivingEntity tempLiving = (LivingEntity) EntityList.createEntityByIDFromName(new ResourceLocation(MoCConstants.MOD_PREFIX + this.spawnClass.toLowerCase()), world);
                if (tempLiving instanceof IMoCEntity) {
                    IMoCTameable storedCreature = (IMoCTameable) tempLiving;
                    ((LivingEntity) storedCreature).setPosition(newPosX, newPosY, newPosZ);
                    storedCreature.setTypeMoC(this.creatureType);
                    storedCreature.setTamed(true);
                    storedCreature.setPetName(this.name);
                    storedCreature.setOwnerPetId(this.PetId);
                    storedCreature.setOwnerId(player.getUniqueID());
                    this.ownerName = player.getName().getString();
                    ((LivingEntity) storedCreature).setHealth(this.health);
                    storedCreature.setAge(this.age);
                    storedCreature.setAdult(this.adult);
                    if (storedCreature instanceof MoCEntityBigCat) {
                        ((MoCEntityBigCat) storedCreature).setHasAmulet(true);
                    }
                    // special case for kitty
                    if (this.spawnClass.equalsIgnoreCase("Kitty")) {
                        ((MoCEntityKitty) storedCreature).setKittyState(3); // allows name to render
                    }

                    if (this.ownerUniqueId == null) {
                        this.ownerUniqueId = player.getUniqueID();
                        if (MoCreatures.instance.mapData != null) {
                            final MoCPetData newOwner = MoCreatures.instance.mapData.getPetData(player.getUniqueID());
                            int maxCount = MoCreatures.proxy.maxTamed;
                            if (MoCTools.isThisPlayerAnOP(player)) {
                                maxCount = MoCreatures.proxy.maxOPTamed;
                            }
                            if (newOwner == null) {
                                if (maxCount > 0 || !MoCreatures.proxy.enableOwnership) {
                                    // create new PetData for new owner
                                    MoCreatures.instance.mapData.updateOwnerPet(storedCreature);
                                }
                            } else // add pet to existing pet data
                            {
                                if (newOwner.getTamedList().size() < maxCount || !MoCreatures.proxy.enableOwnership) {
                                    MoCreatures.instance.mapData.updateOwnerPet(storedCreature);
                                }
                            }
                        }
                    } else {
                        //if the player using the amulet is different from the original owner
                        if (!(this.ownerUniqueId.equals(player.getUniqueID())) && MoCreatures.instance.mapData != null) {
                            MoCPetData oldOwner = MoCreatures.instance.mapData.getPetData(this.ownerUniqueId);
                            MoCPetData newOwner = MoCreatures.instance.mapData.getPetData(player.getUniqueID());
                            int maxCount = MoCreatures.proxy.maxTamed;
                            if (MoCTools.isThisPlayerAnOP(player)) {
                                maxCount = MoCreatures.proxy.maxOPTamed;
                            }
                            if ((newOwner != null && newOwner.getTamedList().size() < maxCount) || (newOwner == null && maxCount > 0) || !MoCreatures.proxy.enableOwnership) {
                                MoCreatures.instance.mapData.updateOwnerPet(storedCreature);
                            }
                            // remove pet entry from old owner
                            if (oldOwner != null) {
                                for (int j = 0; j < oldOwner.getTamedList().size(); j++) {
                                    CompoundNBT petEntry = oldOwner.getTamedList().getCompound(j);
                                    if (petEntry.getInt("PetId") == this.PetId) {
                                        // found match, remove
                                        oldOwner.getTamedList().remove(j);
                                    }
                                }
                            }
                        }
                    }

                    if (player.getEntityWorld().addEntity((LivingEntity) storedCreature)) {
                        MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAppear(((LivingEntity) storedCreature).getEntityId()), new TargetPoint(player.getEntityWorld().provider.getDimensionType().getId(), player.getPosX(), player.getPosY(), player.getPosZ(), 64));
                        if (this.ownerUniqueId == null || this.name.isEmpty()) {
                            MoCTools.tameWithName(player, storedCreature);
                        }

                        player.setHeldItem(hand, emptyAmulet);
                        MoCPetData petData = MoCreatures.instance.mapData.getPetData(storedCreature.getOwnerId());
                        if (petData != null) {
                            petData.setInAmulet(storedCreature.getOwnerPetId(), false);
                        }
                    }
                }
            } catch (Exception ex) {
                if (MoCreatures.proxy.debug) {
                    MoCreatures.LOGGER.warn("Error spawning creature from fishnet/amulet " + ex);
                }
            }
        }

        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    public void readFromNBT(CompoundNBT nbt) {
        this.PetId = nbt.getInt("PetId");
        this.creatureType = nbt.getInt("CreatureType");
        this.health = nbt.getFloat("Health");
        this.age = nbt.getInt("Edad");
        this.name = nbt.getString("Name");
        this.spawnClass = nbt.getString("SpawnClass");
        this.adult = nbt.getBoolean("Adult");
        this.ownerName = nbt.getString("OwnerName");
        if (nbt.hasUniqueId("OwnerUUID")) {
            this.ownerUniqueId = nbt.getUniqueId("OwnerUUID");
        }
    }

    public void writeToNBT(CompoundNBT nbt) {
        nbt.putInt("PetID", this.PetId);
        nbt.putInt("CreatureType", this.creatureType);
        nbt.putFloat("Health", this.health);
        nbt.putInt("Edad", this.age);
        nbt.putString("Name", this.name);
        nbt.putString("SpawnClass", this.spawnClass);
        nbt.putBoolean("Adult", this.adult);
        nbt.putString("OwnerName", this.ownerName);
        if (this.ownerUniqueId != null) {
            nbt.putUniqueId("OwnerUUID", ownerUniqueId);
        }
    }

    @OnlyIn(Dist.CLIENT)
    /*
     * allows items to add custom lines of information to the mouseover description
     */
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        initAndReadNBT(stack);
        if (!this.spawnClass.equals("")) {
            tooltip.add(TextFormatting.AQUA + this.spawnClass);
        }
        if (!this.name.equals("")) {
            tooltip.add(TextFormatting.BLUE + this.name);
        }
        if (!this.ownerName.equals("")) {
            tooltip.add(TextFormatting.DARK_BLUE + "Owned by " + this.ownerName);
        }
    }

    private void initAndReadNBT(ItemStack itemstack) {
        if (itemstack.getTag() == null) {
            itemstack.put(new CompoundNBT());
        }
        CompoundNBT nbtcompound = itemstack.getTag();
        readFromNBT(nbtcompound);
    }
}
