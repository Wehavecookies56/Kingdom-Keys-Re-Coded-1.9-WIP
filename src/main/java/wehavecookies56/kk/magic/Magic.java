package wehavecookies56.kk.magic;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.lib.Strings;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.server.magics.MagicAero;
import wehavecookies56.kk.network.packet.server.magics.MagicBlizzard;
import wehavecookies56.kk.network.packet.server.magics.MagicCure;
import wehavecookies56.kk.network.packet.server.magics.MagicFire;
import wehavecookies56.kk.network.packet.server.magics.MagicStop;
import wehavecookies56.kk.network.packet.server.magics.MagicThunder;

public class Magic {

	public static double getMagicCost (String magic, EntityPlayer player) {
		double cost = 0;
		if (player.getCapability(KingdomKeys.PLAYER_STATS, null).getCheatMode()) cost = 0;
		return cost;
	}

	public static void getMagic (EntityPlayer player, World world, String magic) {
		switch (magic) {
			case Strings.Spell_Fire:
				Fire(player, world);
				break;
			case Strings.Spell_Blizzard:
				Blizzard(player, world);
				break;
			case Strings.Spell_Cure:
				Cure(player, world);
				break;
			case Strings.Spell_Thunder:
				Thunder(player, world);
				break;
			case Strings.Spell_Aero:
				Aero(player, world);
				break;
			case Strings.Spell_Stop:
				Stop(player, world);
				break;
			default:
				break;
		}
	}

	public static void Fire (EntityPlayer player, World world) {
		if(Minecraft.getMinecraft().thePlayer.getCapability(KingdomKeys.MAGIC_STATE, null).getKH1Fire())
			System.out.println("KH1");
		else
		{
			PacketDispatcher.sendToServer(new MagicFire());
			player.swingArm(EnumHand.MAIN_HAND);
			world.playSound(player.posX, player.posY, player.posZ, SoundEvents.entity_ghast_shoot, SoundCategory.PLAYERS, 1, 1, false);
		}
	}

	public static void Blizzard (EntityPlayer player, World world) {
		PacketDispatcher.sendToServer(new MagicBlizzard());
		player.swingArm(EnumHand.MAIN_HAND);
		//if (FMLCommonHandler.instance().getSide() == Side.SERVER) PacketDispatcher.sendToDimension(new MagicBlizzard(), world.provider.getDimensionId());
	}

	public static void Thunder (EntityPlayer player, World world) {
		PacketDispatcher.sendToServer(new MagicThunder());
		player.swingArm(EnumHand.MAIN_HAND);
	}

	public static void Cure (EntityPlayer player, World world) {
		PacketDispatcher.sendToServer(new MagicCure());
		player.swingArm(EnumHand.MAIN_HAND);

	}

	public static void Aero (EntityPlayer player, World world) {
		PacketDispatcher.sendToServer(new MagicAero());
		player.swingArm(EnumHand.MAIN_HAND);
		world.playSound(player.posX, player.posY, player.posZ, SoundEvents.entity_ghast_shoot, SoundCategory.PLAYERS, 1, 1, false);
	}

	public static void Stop (EntityPlayer player, World world) {
		PacketDispatcher.sendToServer(new MagicStop());
		player.swingArm(EnumHand.MAIN_HAND);
		world.playSound(player.posX, player.posY, player.posZ, SoundEvents.entity_ghast_shoot, SoundCategory.PLAYERS, 1, 1, false);
	}
}
