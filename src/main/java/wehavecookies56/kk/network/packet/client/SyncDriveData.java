package wehavecookies56.kk.network.packet.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.capabilities.DriveStateCapability.IDriveState;
import wehavecookies56.kk.capabilities.PlayerStatsCapability.IPlayerStats;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractClientMessage;

public class SyncDriveData extends AbstractClientMessage<SyncDriveData> {

	boolean inDrive;
	String driveName;
	int antiPoints;
	int valorLevel;
	int wisdomLevel;
	int limitLevel;
	int masterLevel;
	int finalLevel;
	double dp;
	
	public SyncDriveData() {}
	
	public SyncDriveData(IDriveState state, IPlayerStats stats) {
		this.inDrive = state.getInDrive();
		this.driveName = state.getActiveDriveName();
		this.antiPoints = state.getAntiPoints();
		this.valorLevel = state.getDriveLevel(Strings.Form_Valor);
		this.wisdomLevel = state.getDriveLevel(Strings.Form_Wisdom);
		this.limitLevel = state.getDriveLevel(Strings.Form_Limit);
		this.masterLevel = state.getDriveLevel(Strings.Form_Master);
		this.finalLevel = state.getDriveLevel(Strings.Form_Final);
		this.dp = stats.getDP();
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.inDrive = buffer.readBoolean();
		this.driveName = buffer.readStringFromBuffer(100);
		this.antiPoints = buffer.readInt();
		this.valorLevel = buffer.readInt();
		this.wisdomLevel = buffer.readInt();
		this.limitLevel = buffer.readInt();
		this.masterLevel = buffer.readInt();
		this.finalLevel = buffer.readInt();
		this.dp = buffer.readDouble();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBoolean(this.inDrive);
		buffer.writeString(this.driveName);
		buffer.writeInt(this.antiPoints);
		buffer.writeInt(this.valorLevel);
		buffer.writeInt(this.wisdomLevel);
		buffer.writeInt(this.limitLevel);
		buffer.writeInt(this.masterLevel);
		buffer.writeInt(this.finalLevel);
		buffer.writeDouble(this.dp);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		final IDriveState state = player.getCapability(KingdomKeys.DRIVE_STATE, null);
		final IPlayerStats stats = player.getCapability(KingdomKeys.PLAYER_STATS, null);
		state.setInDrive(inDrive);
		state.setActiveDriveName(driveName);
		state.setAntiPoints(antiPoints);
		state.setDriveLevel(Strings.Form_Valor, valorLevel);
		state.setDriveLevel(Strings.Form_Wisdom, wisdomLevel);
		state.setDriveLevel(Strings.Form_Limit, limitLevel);
		state.setDriveLevel(Strings.Form_Master, masterLevel);
		state.setDriveLevel(Strings.Form_Final, finalLevel);
		stats.setDP(dp);
	}

}
