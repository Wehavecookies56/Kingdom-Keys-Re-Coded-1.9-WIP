package wehavecookies56.kk.driveforms;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.api.driveforms.DriveForm;
import wehavecookies56.kk.entities.ExtendedPlayer;
import wehavecookies56.kk.lib.Constants;
import wehavecookies56.kk.lib.Reference;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.client.SpawnDriveFormParticles;
import wehavecookies56.kk.network.packet.server.MasterFormPacket;
import wehavecookies56.kk.util.SoundHelper;

public class DriveFormMaster extends DriveForm {

	double cost;

	public DriveFormMaster (double cost) {
		this.cost = cost;
	}

	int jumps = 0;

	@Override
	public String getName () {
		return Strings.Form_Master;
	}

	@Override
	public ResourceLocation getTexture () {
		return new ResourceLocation(Reference.MODID, "textures/driveforms/master.png");
	}

	@Override
	public double getCost () {
		return this.cost;
	}

	@Override
	public void initDrive (EntityPlayer player) {
		player.getCapability(KingdomKeys.DRIVE_STATE, null).setActiveDriveName(getName());
		player.getCapability(KingdomKeys.DRIVE_STATE, null).setInDrive(true);
		PacketDispatcher.sendToAllAround(new SpawnDriveFormParticles(player), player, 64.0D);
		SoundHelper.playSoundAtEntity(player.worldObj, player, SoundHelper.Drive, 0.5f, 1);
	}

	@Override
	public void update (EntityPlayer player) {
		if (player.onGround && !player.isInWater()) {
			player.motionX *= 1.18D;
			player.motionZ *= 1.18D;
		}
		
		if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown())
		{
			if(player.motionY > 0)
			{
				player.motionY += Constants.MASTER_JUMP_1;
			}
		}
		if (player.onGround)
			jumps = 0;
		else if (player.worldObj.isRemote) if (player.motionY < 0 && Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()) if (this.jumps < 1) {
			this.jumps++;
			player.jump();
			PacketDispatcher.sendToServer(new MasterFormPacket());
		}

		if (ExtendedPlayer.get(player).cheatMode == false) if (player.getCapability(KingdomKeys.PLAYER_STATS, null).getDP() > 0) {
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
