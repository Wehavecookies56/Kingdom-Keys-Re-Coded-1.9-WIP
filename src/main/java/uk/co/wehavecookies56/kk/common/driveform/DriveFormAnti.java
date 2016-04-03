package uk.co.wehavecookies56.kk.common.driveform;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import uk.co.wehavecookies56.kk.api.driveforms.DriveForm;
import uk.co.wehavecookies56.kk.client.sound.ModSounds;
import uk.co.wehavecookies56.kk.common.capability.ModCapabilities;
import uk.co.wehavecookies56.kk.common.lib.Reference;
import uk.co.wehavecookies56.kk.common.lib.Strings;
import uk.co.wehavecookies56.kk.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.kk.common.network.packet.client.SpawnDriveFormParticles;
import uk.co.wehavecookies56.kk.common.network.packet.client.SyncDriveData;

public class DriveFormAnti extends DriveForm {

	public DriveFormAnti () {

	}

	@Override
	public String getName () {
		return Strings.Form_Anti;
	}

	@Override
	public ResourceLocation getTexture () {
		return new ResourceLocation(Reference.MODID, "textures/driveform/anti.png");
	}

	@Override
	public double getCost () {
		return 0;
	}

	@Override
	public void initDrive (EntityPlayer player) {
		player.getCapability(ModCapabilities.DRIVE_STATE, null).setActiveDriveName(getName());
		player.getCapability(ModCapabilities.DRIVE_STATE, null).setInDrive(true);
		PacketDispatcher.sendTo(new SyncDriveData(player.getCapability(ModCapabilities.DRIVE_STATE, null), player.getCapability(ModCapabilities.PLAYER_STATS, null)), (EntityPlayerMP) player);
		PacketDispatcher.sendToAllAround(new SpawnDriveFormParticles(player), player, 64.0D);
		player.worldObj.playSound((EntityPlayer)null, player.getPosition(), ModSounds.antidrive, SoundCategory.MASTER, 1.0f, 1.0f);
	}

	@Override
	public void update (EntityPlayer player) {
		if (player.getCapability(ModCapabilities.CHEAT_MODE, null).getCheatMode() == false){
			if (player.getCapability(ModCapabilities.PLAYER_STATS, null).getDP() > 0) 
			{
				player.getCapability(ModCapabilities.PLAYER_STATS, null).remDP(0.1);
				if (player.getCapability(ModCapabilities.PLAYER_STATS, null).getDP() < 0) 
				{
					player.getCapability(ModCapabilities.PLAYER_STATS, null).setDP(0);
				}
			}else{
				player.getCapability(ModCapabilities.PLAYER_STATS, null).setDP(0);
				player.getCapability(ModCapabilities.DRIVE_STATE, null).setInDrive(false);
				player.getCapability(ModCapabilities.DRIVE_STATE, null).setActiveDriveName("none");
				PacketDispatcher.sendTo(new SyncDriveData(player.getCapability(ModCapabilities.DRIVE_STATE, null), player.getCapability(ModCapabilities.PLAYER_STATS, null)), (EntityPlayerMP) player);
				//endDrive(player);
			}
		}
	}

	@Override
	public void endDrive (EntityPlayer player) {
		player.getCapability(ModCapabilities.PLAYER_STATS, null).setDP(0);
		player.getCapability(ModCapabilities.DRIVE_STATE, null).setInDrive(false);
		player.getCapability(ModCapabilities.DRIVE_STATE, null).setActiveDriveName("none");
		PacketDispatcher.sendTo(new SyncDriveData(player.getCapability(ModCapabilities.DRIVE_STATE, null), player.getCapability(ModCapabilities.PLAYER_STATS, null)), (EntityPlayerMP) player);
	}

}
