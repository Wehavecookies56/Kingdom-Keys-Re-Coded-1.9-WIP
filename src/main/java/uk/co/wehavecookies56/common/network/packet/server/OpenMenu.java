package uk.co.wehavecookies56.common.network.packet.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.common.achievement.ModAchievements;
import uk.co.wehavecookies56.common.capability.ModCapabilities;
import uk.co.wehavecookies56.common.network.packet.AbstractMessage;
import uk.co.wehavecookies56.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.common.network.packet.client.SyncDriveData;
import uk.co.wehavecookies56.common.network.packet.client.SyncMagicData;
import uk.co.wehavecookies56.common.network.packet.client.SyncMunnyData;
import uk.co.wehavecookies56.common.core.helper.AchievementHelper;

public class OpenMenu extends AbstractMessage<OpenMenu> {

	public OpenMenu () {}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {

	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {

	}

	@Override
	public void process (EntityPlayer player, Side side) {
		AchievementHelper.addAchievement(player, ModAchievements.openMenu);
		PacketDispatcher.sendTo(new SyncMunnyData(player.getCapability(ModCapabilities.MUNNY, null)), (EntityPlayerMP) player);
		PacketDispatcher.sendTo(new SyncMagicData(player.getCapability(ModCapabilities.MAGIC_STATE, null), player.getCapability(ModCapabilities.PLAYER_STATS, null)), (EntityPlayerMP) player);
		PacketDispatcher.sendTo(new SyncDriveData(player.getCapability(ModCapabilities.DRIVE_STATE, null), player.getCapability(ModCapabilities.PLAYER_STATS, null)), (EntityPlayerMP) player);
	}
}
