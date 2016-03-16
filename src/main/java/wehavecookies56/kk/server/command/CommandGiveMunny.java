package wehavecookies56.kk.server.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import wehavecookies56.kk.entities.ExtendedPlayer;
import wehavecookies56.kk.util.TextHelper;

public class CommandGiveMunny implements ICommand {

	private List<String> aliases;

	public CommandGiveMunny () {
		this.aliases = new ArrayList<String>();
		this.aliases.add("givemunny");
		this.aliases.add("addmunny");
		this.aliases.add("kkgivemunny");
	}

	@Override
	public int compareTo (ICommand arg0) {
		return 0;
	}

	@Override
	public String getCommandName () {
		return "givemunny";
	}

	public int getRequiredPermissionLevel () {
		return 2;
	}

	@Override
	public String getCommandUsage (ICommandSender sender) {
		return "/givemunny <value>";
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

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (sender.getCommandSenderEntity() instanceof EntityPlayer) if (args.length == 0)
			TextHelper.sendFormattedChatMessage("Invalid arguments, usage \"/givemunny <amount>\"", TextFormatting.RED, (EntityPlayer) sender.getCommandSenderEntity());
		else {
			ExtendedPlayer munny = ExtendedPlayer.get((EntityPlayer) sender.getCommandSenderEntity());
			if (!isInteger(args[0].toString()))
				TextHelper.sendFormattedChatMessage("Invalid arguments, usage \"/givemunny <amount>\"", TextFormatting.RED, (EntityPlayer) sender.getCommandSenderEntity());
			else if (Integer.parseInt(args[0].toString()) > munny.getMaxMunny() || Integer.parseInt(args[0].toString()) < 0)
				TextHelper.sendFormattedChatMessage("Invalid arguments, enter a value between 0 and " + (munny.getMaxMunny() - munny.getMunny()), TextFormatting.RED, (EntityPlayer) sender.getCommandSenderEntity());
			else if (Integer.parseInt(args[0].toString()) + munny.getMunny() > munny.getMaxMunny())
				TextHelper.sendFormattedChatMessage("Invalid arguments, adding this value would go over the maximum amount", TextFormatting.RED, (EntityPlayer) sender.getCommandSenderEntity());
			else {
				munny.addMunny(Integer.parseInt(args[0].toString()));
				TextHelper.sendFormattedChatMessage("Successfully added " + args[0].toString() + " munny", TextFormatting.GREEN, (EntityPlayer) sender.getCommandSenderEntity());
			}
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canCommandSenderUseCommand(getRequiredPermissionLevel(), getCommandName());
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos pos) {
		return null;
	}
}
