package uk.co.wehavecookies56.common.network.packet.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.common.capability.CheatModeCapability.ICheatMode;
import uk.co.wehavecookies56.common.capability.ModCapabilities;
import uk.co.wehavecookies56.common.capability.PlayerStatsCapability.IPlayerStats;
import uk.co.wehavecookies56.common.network.packet.AbstractMessage.AbstractClientMessage;

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
		final ICheatMode state = player.getCapability(ModCapabilities.CHEAT_MODE, null);
		state.setCheatMode(this.cheatmode);
	}
}
