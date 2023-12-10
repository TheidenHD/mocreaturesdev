/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.neutral.MoCEntityKitty;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class MoCEntityKittyBed extends MobEntity {

    private static final DataParameter<Boolean> HAS_MILK = EntityDataManager.createKey(MoCEntityKittyBed.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_FOOD = EntityDataManager.createKey(MoCEntityKittyBed.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> PICKED_UP = EntityDataManager.createKey(MoCEntityKittyBed.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SHEET_COLOR = EntityDataManager.createKey(MoCEntityKittyBed.class, DataSerializers.VARINT);
    public float milkLevel;

    public MoCEntityKittyBed(World world) {
        super(world);
        setSize(1.0F, 0.15F);
        setNoAI(true);
        this.milkLevel = 0.0F;
    }

    public MoCEntityKittyBed(World world, double d, double d1, double d2) {
        super(world);
        setSize(1.0F, 0.15F);
        setNoAI(true);
        this.milkLevel = 0.0F;
    }

    public MoCEntityKittyBed(World world, int i) {
        this(world);
        setSheetColor(i);
    }

    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("kitty_bed.png");
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(HAS_MILK, Boolean.FALSE);
        this.dataManager.register(HAS_FOOD, Boolean.FALSE);
        this.dataManager.register(PICKED_UP, Boolean.FALSE);
        this.dataManager.register(SHEET_COLOR, 0);
    }

    public boolean getHasFood() {
        return this.dataManager.get(HAS_FOOD);
    }

    public void setHasFood(boolean flag) {
        this.dataManager.set(HAS_FOOD, flag);
    }

    public boolean getHasMilk() {
        return this.dataManager.get(HAS_MILK);
    }

    public void setHasMilk(boolean flag) {
        this.dataManager.set(HAS_MILK, flag);
    }

    public boolean getPickedUp() {
        return this.dataManager.get(PICKED_UP);
    }

    public void setPickedUp(boolean flag) {
        this.dataManager.set(PICKED_UP, flag);
    }

    public int getSheetColor() {
        return this.dataManager.get(SHEET_COLOR);
    }

    public void setSheetColor(int i) {
        this.dataManager.set(SHEET_COLOR, i);
    }

    @Override
    public boolean canBePushed() {
        return !this.isDead;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public boolean canEntityBeSeen(Entity entity) {
        return this.world.rayTraceBlocks(new Vector3d(this.getPosX(), this.getPosY() + getEyeHeight(), this.getPosZ()), new Vector3d(entity.getPosX(), entity.getPosY() + entity.getEyeHeight(), entity.getPosZ())) == null;
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    protected float getSoundVolume() {
        return 0.0F;
    }

    @Override
    public double getMountedYOffset() {
        return 0.0D;
    }

    @Override
    public void handleStatusUpdate(byte byte0) {
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && !getHasFood() && !getHasMilk()) {
            if (stack.getItem() == MoCItems.petfood) {
                if (!player.capabilities.isCreativeMode) stack.shrink(1);
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_KITTYBED_POURINGFOOD);
                setHasMilk(false);
                setHasFood(true);
            } else if (stack.getItem() == Items.MILK_BUCKET) {
                player.setHeldItem(hand, new ItemStack(Items.BUCKET, 1));
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_KITTYBED_POURINGMILK);
                setHasMilk(true);
                setHasFood(false);
            }
            return true;
        }
        if (this.getRidingEntity() == null) {
            if (player.isSneaking()) {
                final int color = getSheetColor();
                player.inventory.addItemStackToInventory(new ItemStack(MoCItems.kittybed[color], 1));
                if (getHasFood()) player.inventory.addItemStackToInventory(new ItemStack(MoCItems.petfood, 1));
                else if (getHasMilk()) player.inventory.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET, 1));
                MoCTools.playCustomSound(this, SoundEvents.ENTITY_ITEM_PICKUP, 0.2F);
                setDead();
            } else {
                setRotationYawHead((float) MoCTools.roundToNearest90Degrees(this.rotationYawHead) + 90.0F);
                MoCTools.playCustomSound(this, SoundEvents.ENTITY_ITEMFRAME_ROTATE_ITEM);
            }
            return true;
        }
        return true;
    }

    @Override
    public void move(MoverType type, double d, double d1, double d2) {
        if (!this.world.isRemote && (this.getRidingEntity() != null || !this.onGround || !MoCreatures.proxy.staticBed)) {
            super.move(type, d, d1, d2);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.onGround) {
            setPickedUp(false);
        }
        if (!this.world.isRemote && (getHasMilk() || getHasFood()) && this.isBeingRidden() && getPassengers().get(0) instanceof MoCEntityKitty) {
            MoCEntityKitty kitty = (MoCEntityKitty) getPassengers().get(0);
            if (kitty.getKittyState() != 12) {
                this.milkLevel += 0.003F;
                if (this.milkLevel > 2.0F) {
                    this.milkLevel = 0.0F;
                    setHasMilk(false);
                    setHasFood(false);
                }
            }
        }
        if (this.isRiding()) MoCTools.dismountSneakingPlayer(this);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        setHasMilk(compound.getBoolean("HasMilk"));
        setSheetColor(compound.getInt("SheetColour"));
        setHasFood(compound.getBoolean("HasFood"));
        this.milkLevel = compound.getFloat("MilkLevel");
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putBoolean("HasMilk", getHasMilk());
        compound.putInt("SheetColour", getSheetColor());
        compound.putBoolean("HasFood", getHasFood());
        compound.putFloat("MilkLevel", this.milkLevel);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        return false;
    }
}
