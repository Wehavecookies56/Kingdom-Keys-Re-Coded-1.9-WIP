package wehavecookies56.kk.network.packet.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.achievements.ModAchievements;
import wehavecookies56.kk.network.packet.AbstractMessage;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.client.SyncMunnyData;
import wehavecookies56.kk.util.AchievementHelper;

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
		PacketDispatcher.sendTo(new SyncMunnyData(player.getCapability(KingdomKeys.MUNNY, null)), (EntityPlayerMP) player);
	}
}
