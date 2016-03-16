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
import wehavecookies56.kk.item.ItemKeyblade;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.client.SyncExtendedPlayer;
import wehavecookies56.kk.network.packet.server.DeSummonKeyblade;
import wehavecookies56.kk.network.packet.server.RemoveItemInSlot;
import wehavecookies56.kk.util.TextHelper;

public class CommandRemoveKeychain implements ICommand {

	private List<String> aliases;

	public CommandRemoveKeychain () {
		this.aliases = new ArrayList<String>();
		this.aliases.add("kkremovechain");
	}

	@Override
	public int compareTo (ICommand arg0) {
		return 0;
	}

	@Override
	public String getCommandName () {
		return "removechain";
	}

	public int getRequiredPermissionLevel () {
		return 2;
	}

	@Override
	public String getCommandUsage (ICommandSender sender) {
		return "/removechain [player]";
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
		return true;
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
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer) sender.getCommandSenderEntity());
			EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
			if (args.length == 0) {
				if (props.inventoryKeychain.getStackInSlot(0) != null) {
					PacketDispatcher.sendToServer(new RemoveItemInSlot("keychain", 0));
					PacketDispatcher.sendToAllAround(new RemoveItemInSlot("keychain", 0), (EntityPlayer) sender.getCommandSenderEntity(), 1);

					if (props.isKeybladeSummoned()) if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ItemKeyblade) // props.isKeybladeSummoned()
																																											// ==
																																											// true
						PacketDispatcher.sendToServer(new DeSummonKeyblade(player.inventory.getCurrentItem()));
					PacketDispatcher.sendToServer(new SyncExtendedPlayer(player));
					TextHelper.sendFormattedChatMessage("Your keychain has been removed!", TextFormatting.YELLOW, (EntityPlayer) sender.getCommandSenderEntity());
				} else
					TextHelper.sendFormattedChatMessage("The chain slot has no chain!", TextFormatting.RED, (EntityPlayer) sender.getCommandSenderEntity());
			} else if (args.length == 1) {
				EntityPlayerMP playermp = args.length == 1 ? server.getPlayerList().getPlayerByUUID(UUID.fromString(args[0])) : getCommandSenderAsPlayer(sender);
				ExtendedPlayer propsmp = ExtendedPlayer.get(playermp);

				if (propsmp.inventoryKeychain.getStackInSlot(0) != null) {
					PacketDispatcher.sendToServer(new RemoveItemInSlot("keychain", 0));
					if (propsmp.isKeybladeSummoned()) if (playermp.inventory.getCurrentItem() != null && playermp.inventory.getCurrentItem().getItem() instanceof ItemKeyblade) PacketDispatcher.sendToServer(new DeSummonKeyblade(playermp.inventory.getCurrentItem()));
					PacketDispatcher.sendToServer(new SyncExtendedPlayer(playermp));
					TextHelper.sendFormattedChatMessage(args[0] + "'s keychain has been removed!", TextFormatting.YELLOW, (EntityPlayer) sender.getCommandSenderEntity());
				} else
					TextHelper.sendFormattedChatMessage("The chain slot has no chain!", TextFormatting.RED, (EntityPlayer) sender.getCommandSenderEntity());
			} else
				TextHelper.sendFormattedChatMessage("Invalid arguments! Usage: /removechain [player]", TextFormatting.RED, (EntityPlayer) sender.getCommandSenderEntity());
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
