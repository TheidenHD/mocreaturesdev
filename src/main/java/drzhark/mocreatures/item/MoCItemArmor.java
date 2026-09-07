package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class MoCItemArmor extends ArmorItem {

    public MoCItemArmor(Item.Properties properties, ArmorMaterial materialIn, Type type) {
        super(materialIn, type, properties);
    }

    @SuppressWarnings("removal")
    @Override
    public void onArmorTick(ItemStack itemStack, Level world, Player player) {
        if (!MoCreatures.proxy.armorSetEffects) return;

        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem(); // Boots
        Item legs = player.getItemBySlot(EquipmentSlot.LEGS).getItem(); // Leggings
        Item plate = player.getItemBySlot(EquipmentSlot.CHEST).getItem(); // Chestplate
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem(); // Helmet

        // Dark Scorpion Armor Set Effect - Night Vision
        if (boots == MoCItems.BOOTS_SCORP_C.get() && legs == MoCItems.LEGS_SCORP_C.get() && plate == MoCItems.PLATE_SCORP_C.get() && helmet == MoCItems.HELMET_SCORP_C.get()) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 2, 0, true, false));
            return;
        }

        // Fire Scorpion Armor Set Effect - Fire Resistance
        if (boots == MoCItems.BOOTS_SCORP_N.get() && legs == MoCItems.LEGS_SCORP_N.get() && plate == MoCItems.PLATE_SCORP_N.get() && helmet == MoCItems.HELMET_SCORP_N.get()) {
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2, 0, true, false));
            return;
        }

        // Frost Scorpion Armor Set Effect - Resistance
        if (boots == MoCItems.BOOTS_SCORP_F.get() && legs == MoCItems.LEGS_SCORP_F.get() && plate == MoCItems.PLATE_SCORP_F.get() && helmet == MoCItems.HELMET_SCORP_F.get()) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2, 0, true, false));
            return;
        }

        // Earth Scorpion Armor Set Effect - Speed II
        if (boots == MoCItems.BOOTS_SCORP_D.get() && legs == MoCItems.LEGS_SCORP_D.get() && plate == MoCItems.PLATE_SCORP_D.get() && helmet == MoCItems.HELMET_SCORP_D.get()) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2, 1, true, false));
        }

        // Undead Scorpion Armor Set Effect - Strength
        if (boots == MoCItems.BOOTS_SCORP_U.get() && legs == MoCItems.LEGS_SCORP_U.get() && plate == MoCItems.PLATE_SCORP_U.get() && helmet == MoCItems.HELMET_SCORP_U.get()) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2, 0, true, false));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        if ((this == MoCItems.HELMET_SCORP_D.get()) || (this == MoCItems.PLATE_SCORP_D.get())
                || (this == MoCItems.LEGS_SCORP_D.get()) || (this == MoCItems.BOOTS_SCORP_D.get())) {
            tooltip.add(Component.translatable("info.mocreatures.setbonus").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("info.mocreatures.setbonusscorp1").withStyle(ChatFormatting.BLUE));
        }

        if ((this == MoCItems.HELMET_SCORP_F.get()) || (this == MoCItems.PLATE_SCORP_F.get())
                || (this == MoCItems.LEGS_SCORP_F.get()) || (this == MoCItems.BOOTS_SCORP_F.get())) {
            tooltip.add(Component.translatable("info.mocreatures.setbonus").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("info.mocreatures.setbonusscorp2").withStyle(ChatFormatting.BLUE));
        }

        if ((this == MoCItems.HELMET_SCORP_N.get()) || (this == MoCItems.PLATE_SCORP_N.get())
                || (this == MoCItems.LEGS_SCORP_N.get()) || (this == MoCItems.BOOTS_SCORP_N.get())) {
            tooltip.add(Component.translatable("info.mocreatures.setbonus").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("info.mocreatures.setbonusscorp3").withStyle(ChatFormatting.BLUE));
        }

        if ((this == MoCItems.HELMET_SCORP_C.get()) || (this == MoCItems.PLATE_SCORP_C.get())
                || (this == MoCItems.LEGS_SCORP_C.get()) || (this == MoCItems.BOOTS_SCORP_C.get())) {
            tooltip.add(Component.translatable("info.mocreatures.setbonus").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("info.mocreatures.setbonusscorp4").withStyle(ChatFormatting.BLUE));
        }

        if ((this == MoCItems.HELMET_SCORP_U.get()) || (this == MoCItems.PLATE_SCORP_U.get())
                || (this == MoCItems.LEGS_SCORP_U.get()) || (this == MoCItems.BOOTS_SCORP_U.get())) {
            tooltip.add(Component.translatable("info.mocreatures.setbonus").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("info.mocreatures.setbonusscorp5").withStyle(ChatFormatting.BLUE));
        }
    }
}
