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
import net.minecraft.util.text.TextFormatting;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.util.TextHelper;

public class CommandResetLevel implements ICommand {

	private List<String> aliases;

	public CommandResetLevel () {
		this.aliases = new ArrayList<String>();
		this.aliases.add("resetlevel");
		this.aliases.add("kkresetlevel");
	}

	@Override
	public int compareTo (ICommand arg0) {
		return 0;
	}

	@Override
	public String getCommandName () {
		return "resetlevel";
	}

	public int getRequiredPermissionLevel () {
		return 2;
	}

	@Override
	public String getCommandUsage (ICommandSender sender) {
		return "/resetlevel [playername]";
	}

	@Override
	public List getCommandAliases () {
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
			if (args.length == 0) {
				player.getCapability(KingdomKeys.PLAYER_STATS, null).level = 1;
				player.getCapability(KingdomKeys.PLAYER_STATS, null).experience = 0;
				player.getCapability(KingdomKeys.PLAYER_STATS, null).setStrength(1);
				player.getCapability(KingdomKeys.PLAYER_STATS, null).setDefense(1);
				player.getCapability(KingdomKeys.PLAYER_STATS, null).setMagic(1);
				player.getCapability(KingdomKeys.PLAYER_STATS, null).setHP(20);
				player.heal(player.getCapability(KingdomKeys.PLAYER_STATS, null).getHP());
				TextHelper.sendFormattedChatMessage("You level has been reset", TextFormatting.YELLOW, (EntityPlayer) sender.getCommandSenderEntity());

			} else if (args.length == 1) {
				EntityPlayerMP entityplayermp = args.length == 1 ? server.getPlayerList().getPlayerByUUID(UUID.fromString(args[0])) : getCommandSenderAsPlayer(sender);
				entityplayermp.getCapability(KingdomKeys.PLAYER_STATS, null).level = 1;
				entityplayermp.getCapability(KingdomKeys.PLAYER_STATS, null).experience = 0;
				entityplayermp.getCapability(KingdomKeys.PLAYER_STATS, null).setStrength(1);
				entityplayermp.getCapability(KingdomKeys.PLAYER_STATS, null).setDefense(1);
				entityplayermp.getCapability(KingdomKeys.PLAYER_STATS, null).setMagic(1);
				entityplayermp.getCapability(KingdomKeys.PLAYER_STATS, null).setHP(20);
				player.heal(entityplayermp.getCapability(KingdomKeys.PLAYER_STATS, null).getHP());
				TextHelper.sendFormattedChatMessage(args[0] + "'s level has been reset", TextFormatting.YELLOW, (EntityPlayer) sender.getCommandSenderEntity());

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
		// TODO Auto-generated method stub
		return null;
	}
	
}
