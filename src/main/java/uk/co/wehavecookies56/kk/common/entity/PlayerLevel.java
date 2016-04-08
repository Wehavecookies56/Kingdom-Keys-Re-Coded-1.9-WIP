package uk.co.wehavecookies56.kk.common.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import uk.co.wehavecookies56.kk.common.capability.ModCapabilities;
import uk.co.wehavecookies56.kk.common.capability.PlayerStatsCapability;
import uk.co.wehavecookies56.kk.common.capability.PlayerStatsCapability.IPlayerStats;
import uk.co.wehavecookies56.kk.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.kk.common.network.packet.client.ShowOverlayPacket;

public class PlayerLevel {
	
	public static int getExpNeeded(int level, int currentExp) {
		//int currentLevel = (int) ((level + 300 * (2 ^ (level / 7))) * (level * 0.25));
		int nextLevel = (int) (((level + 1) + 300 * (2 ^ ((level + 1) / 7))) * ((level + 1) * 0.25));
		int needed = (nextLevel - currentExp);
		return needed;
	}

	public static List<String> messages;

	public PlayerLevel () {
		messages = new ArrayList<String>();
	}

	public static void addExperience(EntityPlayer player, int amount, String type)
	{
		if(player != null)
		{
			IPlayerStats stats = player.getCapability(ModCapabilities.PLAYER_STATS, null);
			switch(type)
			{
				case "normal":
						stats.addExperience(amount);
						PacketDispatcher.sendTo(new ShowOverlayPacket("exp"),(EntityPlayerMP)player);
						System.out.println("ActualExp: "+stats.getExperience());
					break;
				case "valor":
					break;
				case "wisdom":
					break;
				case "limit":
					break;
				case "master":
					break;
				case "final":
					break;
			}
		}
	}
	
	public static void LevelUp (EntityPlayer player) {
		PlayerStatsCapability.IPlayerStats STATS = player.getCapability(ModCapabilities.PLAYER_STATS, null);
		
		if (STATS.getLevel() < 1) {
			STATS.setLevel(STATS.getLevel() + 1);
			return;
		}

		if (STATS.getLevel() == 1) if (STATS.getExperience() >= getExpNeeded(STATS.getLevel(), STATS.getExperience())) {
			STATS.setLevel(STATS.getLevel() + 2);
			levelUpMessage(player);
			STATS.addDefense(1);
		}

		if (getExpNeeded(STATS.getLevel(), STATS.getExperience()) < 0) STATS.setExperience(getExpNeeded(STATS.getLevel(), STATS.getExperience()));
		if (STATS.getLevel() > 1) if (STATS.getLevel() != 100 && STATS.getExperience() >= getExpNeeded(STATS.getLevel(), STATS.getExperience())) {
			STATS.setLevel(STATS.getLevel() + 1);
			levelUpMessage(player);
			switch (STATS.getLevel()) {
				case 3:
					STATS.addStrength(1);
					break;
				case 4:
					STATS.addDefense(1);
					break;
				case 5:
					STATS.addStrength(1);
					STATS.addHP(5);
					break;
				case 6:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 7:
					STATS.addStrength(1);
					break;
				case 8:
					STATS.addMagic(1);
					break;
				case 9:
					STATS.addStrength(1);
					break;
				case 10:
					STATS.addMagic(1);
					STATS.addDefense(1);
					STATS.addHP(5);
					break;
				case 11:
					STATS.addStrength(1);
					break;
				case 12:
					STATS.addMagic(1);
					break;
				case 13:
					STATS.addStrength(1);
					break;
				case 14:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 15:
					STATS.addStrength(1);
					STATS.addHP(5);
					break;
				case 16:
					STATS.addMagic(1);
					break;
				case 17:
					STATS.addStrength(1);
					break;
				case 18:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 19:
					STATS.addStrength(1);
					break;
				case 20:
					STATS.addMagic(1);
					STATS.addHP(5);
					break;
				case 21:
					STATS.addStrength(1);
					break;
				case 22:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 23:
					STATS.addStrength(1);
					break;
				case 24:
					STATS.addMagic(1);
					break;
				case 25:
					STATS.addStrength(1);
					STATS.addHP(5);
					break;
				case 26:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 27:
					STATS.addStrength(1);
					STATS.addMagic(1);
					break;
				case 28:
					STATS.addMagic(1);
					break;
				case 29:
					STATS.addStrength(1);
					break;
				case 30:
					STATS.addMagic(1);
					STATS.addDefense(1);
					STATS.addHP(5);
					break;
				case 31:
					STATS.addStrength(1);
					break;
				case 32:
					STATS.addStrength(1);
					STATS.addMagic(1);
					break;
				case 33:
					STATS.addStrength(1);
					break;
				case 34:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 35:
					STATS.addStrength(1);
					STATS.addHP(5);
					break;
				case 36:
					STATS.addMagic(1);
					break;
				case 37:
					STATS.addStrength(1);
					break;
				case 38:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 39:
					STATS.addStrength(1);
					break;
				case 40:
					STATS.addMagic(1);
					STATS.addHP(5);
					break;
				case 41:
					STATS.addStrength(1);
					break;
				case 42:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 43:
					STATS.addStrength(1);
					STATS.addMagic(1);
					break;
				case 44:
					STATS.addMagic(1);
					break;
				case 45:
					STATS.addStrength(1);
					STATS.addHP(5);
					break;
				case 46:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 47:
					STATS.addStrength(1);
					break;
				case 48:
					STATS.addStrength(1);
					STATS.addMagic(1);
					break;
				case 49:
					STATS.addStrength(1);
					break;
				case 50:
					STATS.addMagic(1);
					STATS.addDefense(1);
					STATS.addHP(5);
					break;
				case 51:
					STATS.addStrength(1);
					break;
				case 52:
					STATS.addMagic(1);
					break;
				case 53:
					STATS.addStrength(1);
					break;
				case 54:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 55:
					STATS.addStrength(1);
					STATS.addHP(5);
					break;
				case 56:
					STATS.addMagic(1);
					break;
				case 57:
					STATS.addStrength(1);
					break;
				case 58:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 59:
					STATS.addStrength(1);
					break;
				case 60:
					STATS.addMagic(1);
					STATS.addHP(5);
					break;
				case 61:
					STATS.addStrength(1);
					break;
				case 62:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 63:
					STATS.addStrength(1);
					break;
				case 64:
					STATS.addMagic(1);
					break;
				case 65:
					STATS.addStrength(1);
					STATS.addHP(5);
					break;
				case 66:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 67:
					STATS.addStrength(1);
					break;
				case 68:
					STATS.addMagic(1);
					break;
				case 69:
					STATS.addStrength(1);
					break;
				case 70:
					STATS.addMagic(1);
					STATS.addDefense(1);
					STATS.addHP(5);
					break;
				case 71:
					STATS.addStrength(1);
					break;
				case 72:
					STATS.addMagic(1);
					break;
				case 73:
					STATS.addStrength(1);
					break;
				case 74:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 75:
					STATS.addStrength(1);
					STATS.addHP(5);
					break;
				case 76:
					STATS.addMagic(1);
					break;
				case 77:
					STATS.addStrength(1);
					break;
				case 78:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 79:
					STATS.addStrength(1);
					break;
				case 80:
					STATS.addMagic(1);
					STATS.addHP(5);
					break;
				case 81:
					STATS.addStrength(1);
					break;
				case 82:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 83:
					STATS.addStrength(1);
					break;
				case 84:
					STATS.addMagic(1);
					break;
				case 85:
					STATS.addStrength(1);
					STATS.addHP(5);
					break;
				case 86:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 87:
					STATS.addStrength(1);
					break;
				case 88:
					STATS.addMagic(1);
					break;
				case 89:
					STATS.addStrength(1);
					break;
				case 90:
					STATS.addMagic(1);
					STATS.addDefense(1);
					STATS.addHP(5);
					break;
				case 91:
					STATS.addStrength(1);
					break;
				case 92:
					STATS.addMagic(1);
					break;
				case 93:
					STATS.addStrength(1);
					break;
				case 94:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 95:
					STATS.addStrength(1);
					STATS.addHP(5);
					break;
				case 96:
					STATS.addMagic(1);
					break;
				case 97:
					STATS.addStrength(1);
					break;
				case 98:
					STATS.addMagic(1);
					STATS.addDefense(1);
					break;
				case 99:
					STATS.addStrength(1);
					break;
				case 100:
					STATS.addStrength(10);
					STATS.addDefense(10);
					STATS.addMagic(10);
					STATS.addHP(5);
					break;
			}
		}

	}

	public static void levelUpMessage (EntityPlayer player) {
		messages.clear();
	}
}
