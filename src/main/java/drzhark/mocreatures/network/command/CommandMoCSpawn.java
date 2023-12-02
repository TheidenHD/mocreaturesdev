/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.command;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.hunter.MoCEntityManticorePet;
import drzhark.mocreatures.entity.neutral.MoCEntityWyvern;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAppear;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandMoCSpawn extends CommandBase {

    private static final List<String> commands = new ArrayList<>();
    private static final List<String> aliases = new ArrayList<>();

    static {
        commands.add("/mocspawn <horse|manticore|wyvern|wyvernghost> <int>");
        aliases.add("mocspawn");
        //tabCompletionStrings.add("moctp");
    }

    @Override
    public String getName() {
        return "mocspawn";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    /**
     * Return the required permission level for this command.
     */
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getUsage(ICommandSender par1ICommandSender) {
        return "commands.mocspawn.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length == 2) {
            String entityType = args[0];
            int type = 0;
            try {
                type = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(new TranslationTextComponent(TextFormatting.RED + "ERROR:" + TextFormatting.WHITE
                        + "The spawn type " + type + " for " + entityType + " is not a valid type."));
                return;
            }

            String playername = sender.getName();
            ServerPlayerEntity player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(playername);
            if (player == null) return;
            MoCEntityTameableAnimal specialEntity;
            if (entityType.equalsIgnoreCase("horse")) {
                specialEntity = new MoCEntityHorse(player.world);
                specialEntity.setAdult(true);
            } else if (entityType.equalsIgnoreCase("manticore")) {
                specialEntity = new MoCEntityManticorePet(player.world);
                specialEntity.setAdult(true);
            } else if (entityType.equalsIgnoreCase("wyvern")) {
                specialEntity = new MoCEntityWyvern(player.world);
                specialEntity.setAdult(false);
            } else if (entityType.equalsIgnoreCase("wyvernghost")) {
                specialEntity = new MoCEntityWyvern(player.world);
                specialEntity.setAdult(false);
                ((MoCEntityWyvern) specialEntity).setIsGhost(true);
            } else {
                sender.sendMessage(new TranslationTextComponent(TextFormatting.RED + "ERROR:" + TextFormatting.WHITE
                        + "The entity spawn type " + entityType + " is not a valid type."));
                return;
            }
            double dist = 3D;
            double newPosY = player.getPosY();
            double newPosX = player.getPosX() - (dist * Math.cos((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));
            double newPosZ = player.getPosZ() - (dist * Math.sin((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));
            specialEntity.setPosition(newPosX, newPosY, newPosZ);
            specialEntity.setTamed(true);
            specialEntity.setOwnerId(null);
            specialEntity.setPetName("Rename_Me");
            specialEntity.setTypeMoC(type);

            if ((entityType.equalsIgnoreCase("horse") && (type < 1 || type > 67))
                    || (entityType.equalsIgnoreCase("wyvern") && (type < 1 || type > 12))
                    || (entityType.equalsIgnoreCase("manticore") && (type < 1 || type > 4))) {
                sender.sendMessage(new TranslationTextComponent(TextFormatting.RED + "ERROR:" + TextFormatting.WHITE
                        + "The spawn type " + type + " is not a valid type."));
                return;
            }
            player.world.addEntity(specialEntity);
            if (!player.world.isRemote) {
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAppear(specialEntity.getEntityId()),
                        new TargetPoint(player.world.provider.getDimensionType().getId(), player.getPosX(), player.getPosY(), player.getPosZ(), 64));
            }
            MoCTools.playCustomSound(specialEntity, MoCSoundEvents.ENTITY_GENERIC_MAGIC_APPEAR);
        } else {
            sender.sendMessage(new TranslationTextComponent(TextFormatting.RED + "ERROR:" + TextFormatting.WHITE
                    + "Invalid spawn parameters entered."));
        }
    }

    /**
     * Returns a sorted list of all possible commands for the given
     * ICommandSender.
     */
    protected List<String> getSortedPossibleCommands(ICommandSender par1ICommandSender) {
        Collections.sort(CommandMoCSpawn.commands);
        return CommandMoCSpawn.commands;
    }

    public void sendCommandHelp(ICommandSender sender) {
        sender.sendMessage(new TranslationTextComponent("§2Listing MoCreatures commands"));
        for (String command : commands) {
            sender.sendMessage(new TranslationTextComponent(command));
        }
    }
}
