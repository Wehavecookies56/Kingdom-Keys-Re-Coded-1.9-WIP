package wehavecookies56.kk.driveforms;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.api.driveforms.DriveForm;
import wehavecookies56.kk.entities.ExtendedPlayer;
import wehavecookies56.kk.lib.Reference;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.client.SpawnDriveFormParticles;
import wehavecookies56.kk.util.SoundHelper;

public class DriveFormAnti extends DriveForm {

	public DriveFormAnti () {

	}

	@Override
	public String getName () {
		return Strings.Form_Anti;
	}

	@Override
	public ResourceLocation getTexture () {
		return new ResourceLocation(Reference.MODID, "textures/driveforms/anti.png");
	}

	@Override
	public double getCost () {
		return 0;
	}

	@Override
	public void initDrive (EntityPlayer player) {
		player.getCapability(KingdomKeys.DRIVE_STATE, null).setActiveDriveName(getName());
		player.getCapability(KingdomKeys.DRIVE_STATE, null).setInDrive(true);
		PacketDispatcher.sendToAllAround(new SpawnDriveFormParticles(player), player, 64.0D);
		SoundHelper.playSoundAtEntity(player.worldObj, player, SoundHelper.AntiDrive, 0.5f, 1);
	}

	@Override
	public void update (EntityPlayer player) {
		if (player.getCapability(KingdomKeys.PLAYER_STATS, null).getCheatMode() == false) if (player.getCapability(KingdomKeys.PLAYER_STATS, null).getDP() > 0) {
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
	}

}
