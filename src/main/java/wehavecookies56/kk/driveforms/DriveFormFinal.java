package wehavecookies56.kk.driveforms;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.api.driveforms.DriveForm;
import wehavecookies56.kk.lib.Constants;
import wehavecookies56.kk.lib.Reference;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.client.SpawnDriveFormParticles;
import wehavecookies56.kk.network.packet.client.SyncDriveData;
import wehavecookies56.kk.network.packet.server.GlidePacket;
import wehavecookies56.kk.util.SoundHelper;

public class DriveFormFinal extends DriveForm {

	double cost;

	public static boolean jumpHeld = false;

	public DriveFormFinal (double cost) {
		this.cost = cost;
	}

	@Override
	public String getName () {
		return Strings.Form_Final;
	}

	@Override
	public ResourceLocation getTexture () {
		return new ResourceLocation(Reference.MODID, "textures/driveforms/final.png");
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
		if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown())
		{
			if(player.motionY > 0)
			{
				player.motionY += Constants.FINAL_JUMP;
			}
		}

		if (player.onGround && !player.isInWater()) {
			player.motionX *= 1.2D;
			player.motionZ *= 1.2D;
		} else if (player.motionY < 0) if (player.worldObj.isRemote) {
			if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()) {
				jumpHeld = true;
				System.out.println(player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Final));
				switch(player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Final))
				{
				case 0:
					player.motionY *= Constants.FINAL_GLIDE_1;
					break;
				case 1:
					player.motionY *= Constants.FINAL_GLIDE_2;
					break;
				case 2:
					player.motionY *= Constants.FINAL_GLIDE_3;
					break;
				}
				PacketDispatcher.sendToServer(new GlidePacket(jumpHeld));
			} else {
				jumpHeld = false;
				PacketDispatcher.sendToServer(new GlidePacket(jumpHeld));
			}
		} else if (jumpHeld) 
			{
				switch(player.getCapability(KingdomKeys.DRIVE_STATE, null).getDriveLevel(Strings.Form_Final))
				{
				case 0:
					player.motionY *= Constants.FINAL_GLIDE_1;
					break;
				case 1:
					player.motionY *= Constants.FINAL_GLIDE_2;
					break;
				case 2:
					player.motionY *= Constants.FINAL_GLIDE_3;
					break;
				}
			}

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
