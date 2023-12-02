package drzhark.mocreatures.util;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum MoCArmorMaterial implements IArmorMaterial {
    crocARMOR("croc", 15, new int[]{2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0, 0.0F, () -> {
        return Ingredient.fromItems(MoCItems.hideCroc);
    }),
    furARMOR("fur", 15, new int[]{2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0, 0.0F, () -> {
        return Ingredient.fromItems(MoCItems.fur);
    }),
    hideARMOR("hide", 15, new int[]{2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0, 0.0F, () -> {
        return Ingredient.fromItems(MoCItems.animalHide);
    }),
    scorpdARMOR("scorpd", 15, new int[]{2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0, 0.0F, () -> {
        return Ingredient.fromItems(MoCItems.chitin);
    }),
    scorpfARMOR("scorpf", 18, new int[]{2, 7, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0, 0.0F, () -> {
        return Ingredient.fromItems(MoCItems.chitinFrost);
    }),
    scorpnARMOR("scorpn", 20, new int[]{3, 7, 6, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0, 0.0F, () -> {
        return Ingredient.fromItems(MoCItems.chitinNether);
    }),
    scorpcARMOR("scorpc", 15, new int[]{2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0, 0.0F, () -> {
        return Ingredient.fromItems(MoCItems.chitinCave);
    }),
    silverARMOR("silver", 15, new int[]{2, 6, 5, 2}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0, 0.0F, () -> {
        return Ingredient.EMPTY;
    });
    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairMaterial;

    private MoCArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    public int getDurability(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return MoCConstants.MOD_ID + ":" + this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    /**
     * Gets the percentage of knockback resistance provided by armor of the material.
     */
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
