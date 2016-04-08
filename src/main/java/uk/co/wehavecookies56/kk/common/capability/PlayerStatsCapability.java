package uk.co.wehavecookies56.kk.common.capability;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import uk.co.wehavecookies56.kk.common.container.inventory.InventoryPotionsMenu;
import uk.co.wehavecookies56.kk.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.kk.common.network.packet.client.ShowOverlayPacket;

public class PlayerStatsCapability {
	
	public interface IPlayerStats {
		 List<String> messages = null;
		//ArrayList messages = null;
		int getLevel();
		int getMaxLevel();
		int getExperience();
		int getMaxExperience();
		int getStrength();
		int getDefense();
		int getMagic();
		int getHP();		
		double getDP();
		double getMaxDP();
		double getMP();
		double getMaxMP();
		boolean getRecharge();
		boolean setLevel(int level);
		boolean setExperience(int experience);
		void addExperience(EntityPlayer player, int amount, String type);
		void setStrength(int strength);
		void addStrength(int strength);
		void setDefense(int defense);
		void addDefense(int defense);
		void setMagic(int magic);
		void addMagic(int magic);
		int setHP(int hp);
		int addHP(int hp);
		boolean setDP(double dp);
		void addDP(double dp);
		void remDP(double dp);
		boolean setMP(double mp);
		void addMP(double mp);
		void remMP(double mp);
		void setMaxMP(double mp);
		void setRecharge(boolean recharge);
		
		InventoryPotionsMenu getInventoryPotionsMenu();
		
		boolean getHudMode();
		void setHudMode(boolean mode);
		int getExpNeeded(int level, int experience);		
	}

	public static class Storage implements IStorage<IPlayerStats> {

		@Override
		public NBTBase writeNBT(Capability<IPlayerStats> capability, IPlayerStats instance, EnumFacing side) {
			NBTTagCompound properties = new NBTTagCompound();
			properties.setInteger("Level", instance.getLevel());
			properties.setInteger("Experience", instance.getExperience());
			properties.setInteger("Strength", instance.getStrength());
			properties.setInteger("Defense", instance.getDefense());
			properties.setInteger("Magic", instance.getMagic());
			properties.setInteger("HP", instance.getHP());
			properties.setDouble("DP", instance.getDP());
			properties.setDouble("MP", instance.getMP());
			properties.setDouble("Max MP", instance.getMaxMP());
			properties.setBoolean("Recharge", instance.getRecharge());
			
			properties.setBoolean("HUD", instance.getHudMode());
			
			instance.getInventoryPotionsMenu().writeToNBT(properties);

			return properties;
		}

		@Override
		public void readNBT(Capability<IPlayerStats> capability, IPlayerStats instance, EnumFacing side, NBTBase nbt) {
			NBTTagCompound properties = (NBTTagCompound) nbt;
			instance.setLevel(properties.getInteger("Level"));
			instance.setExperience(properties.getInteger("Experience"));
			instance.setStrength(properties.getInteger("Strength"));
			instance.setDefense(properties.getInteger("Defense"));
			instance.setMagic(properties.getInteger("Magic"));
			instance.setHP(properties.getInteger("HP"));
			instance.setDP(properties.getDouble("DP"));
			instance.setMP(properties.getDouble("MP"));
			instance.setMaxMP(properties.getDouble("Max MP"));
			instance.setRecharge(properties.getBoolean("Recharge"));
			instance.setHudMode(properties.getBoolean("HUD"));
			instance.getInventoryPotionsMenu().readFromNBT(properties);
		}
	}
	
	public static class Default implements IPlayerStats {
		private int level = 1;
		private int maxLevel = 99;
		private int experience = 0;
		private int maxExperience = 999999;
		private int strength = 1;
		private int defense = 1;
		private int magic = 1;
		private int hp = 20;
        private double mp = 100;
        private double maxMP = 100;
        private double dp = 0;
        private double maxDP = 1000;
		private boolean recharge = false;
		private boolean cheatMode = false;
		private boolean hudmode = true;
		private int remainingExp = 0;
		public static List<String> messages;
		
		private final InventoryPotionsMenu inventoryPotions = new InventoryPotionsMenu();
		
		@Override public InventoryPotionsMenu getInventoryPotionsMenu(){return this.inventoryPotions;}

        @Override public double getMP() { return this.mp; }
        @Override public double getMaxMP() { return this.maxMP; }
		@Override public int getLevel() { return this.level; }
		@Override public int getMaxLevel() { return this.maxLevel; }
		@Override public int getExperience() { return this.experience; }
		@Override public int getMaxExperience() { return this.maxExperience; }
		@Override public int getStrength() { return this.strength; }
		@Override public int getDefense() { return this.defense; }
		@Override public int getMagic() { return this.magic; }
		@Override public int getHP() { return this.hp; }
		@Override public double getDP() { return this.dp; }
		@Override public double getMaxDP() { return this.maxDP; }
		@Override public boolean getRecharge() { return this.recharge; }
		@Override public boolean getHudMode() {return this.hudmode;}
		
		@Override public boolean setLevel(int level) { if (level <= this.maxLevel) { this.level = level; return true; } return false;}
		@Override public boolean setExperience(int experience) { if (experience <= this.maxExperience) { this.experience = experience; return true; } return false; }
		@Override 
		//public void addExperience(int experience) {if (this.experience + experience <= this.maxExperience) this.experience += experience; else this.experience = this.maxExperience;}
		public void addExperience(EntityPlayer player, int amount, String type)
		{
			if(player != null)
			{
				IPlayerStats stats = player.getCapability(ModCapabilities.PLAYER_STATS, null);
				switch(type)
				{
					case "normal":
							if (this.experience + amount <= this.maxExperience)
								this.experience += amount; else this.experience = this.maxExperience;
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
		@Override public void setStrength(int strength) { this.strength = strength; }
		@Override public void addStrength(int strength) { 
			this.strength += strength; 
			messages.add("str");
		}
		@Override public void setDefense(int defense) { this.defense = defense; }
		@Override public void addDefense(int defense) { 
			this.defense += defense; 
			messages.add("def");
		}
		@Override public void setMagic(int magic) { this.magic = magic; }
		@Override public void addMagic(int magic) { 
			this.magic += magic; 
			messages.add("mag");
		}
		@Override public int setHP(int hp) { 
			this.hp = hp; 
			return this.hp;
		}
		@Override public int addHP(int hp) { 
			this.hp += hp; 
			messages.add("hp");
			return this.hp;
		}
        @Override public boolean setDP(double dp) { if (dp <= this.maxDP) {this.dp = dp; return true; } return false; }
        @Override public void addDP(double dp) { if (dp + this.dp > this.maxDP) this.dp = this.maxDP; else this.dp += dp; }
        @Override public void remDP(double dp) { if (dp + this.dp < 0) this.dp = 0; else this.dp -= dp; }
        @Override public boolean setMP(double mp) { if (mp <= this.maxMP) {this.mp = mp; return true; } return false; }
        @Override public void addMP(double mp) { if (mp + this.mp > this.maxMP) this.mp = this.maxMP; else this.mp += mp; }
        @Override public void remMP(double mp) { if (this.mp - mp < 0) this.mp = 0;	else this.mp -= mp; }
        @Override public void setMaxMP(double maxMP) { this.maxMP = maxMP;}
		@Override public void setRecharge(boolean recharge) { this.recharge = recharge; }
		@Override public void setHudMode(boolean hud) { this.hudmode = hud; }
		
		public int getExpNeeded(int level, int currentExp) {
			//int currentLevel = (int) ((level + 300 * (2 ^ (level / 7))) * (level * 0.25));
			int nextLevel = (int) (((level + 1) + 300 * (2 ^ ((level + 1) / 7))) * ((level + 1) * 0.25));
			int needed = (nextLevel - currentExp);
			this.remainingExp = needed;
			return remainingExp;
		}
		
		public static void levelUpMessage (EntityPlayer player) {
			messages.clear();
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
    }
}