package wehavecookies56.kk.server.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import wehavecookies56.kk.entities.ExtendedPlayer;
import wehavecookies56.kk.util.TextHelper;

public class CommandLevelUp implements ICommand {

	private List<String> aliases;

	public CommandLevelUp () {
		this.aliases = new ArrayList<String>();
		this.aliases.add("levelup");
		this.aliases.add("kklevelup");
	}

	@Override
	public int compareTo (ICommand arg0) {
		return 0;
	}

	@Override
	public String getCommandName () {
		return "levelup";
	}

	public int getRequiredPermissionLevel () {
		return 2;
	}

	@Override
	public String getCommandUsage (ICommandSender sender) {
		return "/levelup <level> [player] (level has to be between 1-100)";
	}

	@Override
	public List<String> getCommandAliases () {
		return this.aliases;
	}

	public static boolean isInteger (String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isUsernameIndex (String[] args, int index) {
		return false;
	}

	public static EntityPlayerMP getCommandSenderAsPlayer (ICommandSender sender) throws PlayerNotFoundException {
		if (sender instanceof EntityPlayerMP)
			return (EntityPlayerMP) sender;
		else
			throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (sender.getCommandSenderEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
			if (args.length == 0 || args.length > 2)
				TextHelper.sendFormattedChatMessage("Invalid arguments, usage: " + getCommandUsage(sender), TextFormatting.RED, (EntityPlayer) sender.getCommandSenderEntity());
			else if (args.length == 1) {
				int level = 1;
				try {
					if (Integer.parseInt(args[0]) < 1 || Integer.parseInt(args[0]) > 100) {
						TextHelper.sendFormattedChatMessage("Invalid level, it must be a number between 1 - 100", TextFormatting.RED, (EntityPlayer) sender.getCommandSenderEntity());
						return;
					} else
						level = Integer.parseInt(args[0]);
				} catch (Exception e) {
					TextHelper.sendFormattedChatMessage("Invalid level, it must be a number between 1 - 100", TextFormatting.RED, (EntityPlayer) sender.getCommandSenderEntity());
					return;
				}
				ExtendedPlayer.get(player).level = 1;
				ExtendedPlayer.get(player).experience = 0;
				ExtendedPlayer.get(player).setStrength(1);
				ExtendedPlayer.get(player).setDefense(1);
				ExtendedPlayer.get(player).setMagic(1);
				ExtendedPlayer.get(player).setHP(20);

				while (ExtendedPlayer.get(player).getLevel() < level)
					ExtendedPlayer.get(player).addXP(20);
				player.heal(ExtendedPlayer.get(player).getHP());
				TextHelper.sendFormattedChatMessage("Your level is now " + args[0], TextFormatting.YELLOW, (EntityPlayer) sender.getCommandSenderEntity());

			} else if (args.length == 2) {
				int level = 1;
				try {
					if (Integer.parseInt(args[0]) < 1 || Integer.parseInt(args[0]) > 100) {
						TextHelper.sendFormattedChatMessage("Invalid level, it must be a number between 1 - 100", TextFormatting.RED, (EntityPlayer) sender.getCommandSenderEntity());
						return;
					} else
						level = Integer.parseInt(args[0]);
				} catch (Exception e) {
					TextHelper.sendFormattedChatMessage("Invalid level, it must be a number between 1 - 100", TextFormatting.RED, (EntityPlayer) sender.getCommandSenderEntity());
					return;
				}
				EntityPlayerMP entityplayermp = args.length == 2 ?  server.getPlayerList().getPlayerByUUID(UUID.fromString(args[1])) : getCommandSenderAsPlayer(sender);
				ExtendedPlayer.get(entityplayermp).level = 1;
				ExtendedPlayer.get(entityplayermp).experience = 0;
				ExtendedPlayer.get(entityplayermp).setStrength(1);
				ExtendedPlayer.get(entityplayermp).setDefense(1);
				ExtendedPlayer.get(entityplayermp).setMagic(1);
				ExtendedPlayer.get(entityplayermp).setHP(20);

				while (ExtendedPlayer.get(entityplayermp).getLevel() < level)
					ExtendedPlayer.get(entityplayermp).addXP(20);
				entityplayermp.heal(ExtendedPlayer.get(entityplayermp).getHP());
				TextHelper.sendFormattedChatMessage(args[1] + "'s level is now " + args[0], TextFormatting.YELLOW, (EntityPlayer) sender.getCommandSenderEntity());

			} else
				TextHelper.sendFormattedChatMessage("Invalid arguments, usage: " + getCommandUsage(sender), TextFormatting.RED, (EntityPlayer) sender.getCommandSenderEntity());
		}
		
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canCommandSenderUseCommand(getRequiredPermissionLevel(), getCommandName());
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,
			net.minecraft.util.math.BlockPos pos) {
		return null;
	}
}
