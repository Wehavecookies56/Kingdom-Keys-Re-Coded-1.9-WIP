package wehavecookies56.kk.network.packet.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.capabilities.CheatModeCapability.ICheatMode;
import wehavecookies56.kk.capabilities.MagicStateCapability.IMagicState;
import wehavecookies56.kk.capabilities.PlayerStatsCapability.IPlayerStats;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractClientMessage;

public class SyncCheatModeData extends AbstractClientMessage<SyncCheatModeData> {

	boolean cheatmode;
	
	public SyncCheatModeData() {}
	
	public SyncCheatModeData(ICheatMode state, IPlayerStats stats) {
		this.cheatmode = state.getCheatMode();
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.cheatmode = buffer.readBoolean();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBoolean(this.cheatmode);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		final ICheatMode state = player.getCapability(KingdomKeys.CHEAT_MODE, null);
		state.setCheatMode(this.cheatmode);
	}
}
