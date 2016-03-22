package wehavecookies56.kk.driveforms;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.api.driveforms.DriveForm;
import wehavecookies56.kk.lib.Reference;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.client.SpawnDriveFormParticles;
import wehavecookies56.kk.network.packet.client.SyncDriveData;
import wehavecookies56.kk.util.SoundHelper;

public class DriveFormLimit extends DriveForm {

	double cost;

	public DriveFormLimit (double cost) {
		this.cost = cost;
	}

	@Override
	public String getName () {
		return Strings.Form_Limit;
	}

	@Override
	public ResourceLocation getTexture () {
		return new ResourceLocation(Reference.MODID, "textures/driveforms/limit.png");
	}

	@Override
	public double getCost () {
		return this.cost;
	}

	@Override
	public void initDrive (EntityPlayer player) {
		player.getCapability(KingdomKeys.DRIVE_STATE, null).setActiveDriveName(getName());
		player.getCapability(KingdomKeys.DRIVE_STATE, null).setInDrive(true);
		PacketDispatcher.sendTo(new SyncDriveData(player.getCapability(KingdomKeys.DRIVE_STATE, null), player.getCapability(KingdomKeys.PLAYER_STATS, null)), (EntityPlayerMP) player);
		PacketDispatcher.sendToAllAround(new SpawnDriveFormParticles(player), player, 64.0D);
		SoundHelper.playSoundAtEntity(player.worldObj, player, SoundHelper.Drive, 0.5f, 1);
	}

	@Override
	public void update (EntityPlayer player) {
		if (player.getCapability(KingdomKeys.CHEAT_MODE, null).getCheatMode() == false) if (player.getCapability(KingdomKeys.PLAYER_STATS, null).getDP() > 0) {
			player.getCapability(KingdomKeys.PLAYER_STATS, null).remDP(0.1);
			if (player.getCapability(KingdomKeys.PLAYER_STATS, null).getDP() < 0) player.getCapability(KingdomKeys.PLAYER_STATS, null).setDP(0);
		} else
			endDrive(player);
	}

	@Override
	public void endDrive (EntityPlayer player) {
		player.getCapability(KingdomKeys.PLAYER_STATS, null).setDP(0);
		player.getCapability(KingdomKeys.DRIVE_STATE, null).setInDrive(false);
		player.getCapability(KingdomKeys.DRIVE_STATE, null).setActiveDriveName("none");
		PacketDispatcher.sendTo(new SyncDriveData(player.getCapability(KingdomKeys.DRIVE_STATE, null), player.getCapability(KingdomKeys.PLAYER_STATS, null)), (EntityPlayerMP) player);
	}
}
