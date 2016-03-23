package wehavecookies56.kk.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.capabilities.PlayerStatsCapability.IPlayerStats;

public class PlayerLevel {

	public static int[] expNeeded = new int[] { 20, 23, 27, 33, 39, 47, 56, 67, 80, 94, 109, 127, 146, 167, 191, 216, 244, 274, 307, 342, 381, 422, 466, 513, 564, 618, 676, 737, 803, 873, 948, 1027, 1112, 1201, 1297, 1397, 1504, 1617, 1737, 1864, 1998, 2140, 2290, 2449, 2616, 2793, 2980, 3177, 3385, 3604, 3835, 4079, 4336, 4606, 4892, 5192, 5509, 5842, 6194, 6563, 6952, 7362, 7793, 8247, 8724, 9226, 9755, 10310, 10895, 11510, 12156, 12836, 13551, 14302, 15092, 15923, 16796, 17714, 18679, 19693, 20758, 21878, 23055, 24292, 25591, 26957, 28392, 29899, 31483, 33148, 34896, 36733, 36733, 38662, 40690, 42819, 45056, 47406, 49874, 52467, 100000 };

	int neededExp;
	
	public int getExpNeeded(int level, int currentExp) {
		//int currentLevel = (int) ((level + 300 * (2 ^ (level / 7))) * (level * 0.25));
		int nextLevel = (int) (((level + 1) + 300 * (2 ^ ((level + 1) / 7))) * ((level + 1) * 0.25));
		int needed = (nextLevel - currentExp);
		return needed;
	}

	public static List<String> messages;

	public PlayerLevel () {
		messages = new ArrayList<String>();
	}

	public static void LevelUp (EntityPlayer player) {
		IPlayerStats STATS = player.getCapability(KingdomKeys.PLAYER_STATS, null);
		
		if (STATS.getLevel() < 1) {
			STATS.setLevel(STATS.getLevel() + 1);
			return;
		}

		if (STATS.getLevel() == 1) if (STATS.getExperience() >= expNeeded[STATS.getLevel() - 1]) {
			STATS.setLevel(STATS.getLevel() + 2);
			levelUpMessage(player);
			STATS.addDefense(1);
		}

		if ((Arrays.stream(expNeeded, 0, STATS.getLevel()).sum() - STATS.getExperience()) < 0) STATS.setExperience(Arrays.stream(expNeeded, 0, STATS.getLevel()).sum());
		if (STATS.getLevel() > 1) if (STATS.getLevel() != 100 && STATS.getExperience() >= Arrays.stream(expNeeded, 0, STATS.getLevel()).sum()) {
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
