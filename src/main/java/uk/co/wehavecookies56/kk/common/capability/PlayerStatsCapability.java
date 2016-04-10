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
		void LevelUp();
		void levelUpMessage();
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

		@Override
		public int getExpNeeded(int level, int currentExp) {
			//int currentLevel = (int) ((level + 300 * (2 ^ (level / 7))) * (level * 0.25));
			int nextLevel = (int) (((level + 1) + 300 * (2 ^ ((level + 1) / 7))) * ((level + 1) * 0.25));
			int needed = (nextLevel - currentExp);
			this.remainingExp = needed;
			return remainingExp;
		}

		@Override
		public void levelUpMessage () {
			messages.clear();
		}

		@Override
		public void LevelUp () {
			
			if (this.level < 1) {
				this.setLevel(this.level + 1);
				return;
			}

			if (this.level == 1) if (this.experience >= getExpNeeded(this.level, this.experience)) {
				this.setLevel(this.level + 2);
				levelUpMessage();
				this.addDefense(1);
			}

			if (getExpNeeded(this.level, this.experience) < 0) this.setExperience(getExpNeeded(this.level, this.experience));
			if (this.level > 1) if (this.level != 100 && this.experience >= getExpNeeded(this.level, this.experience)) {
				this.setLevel(this.level + 1);
				levelUpMessage();
				switch (this.level) {
					case 3:
						this.addStrength(1);
						break;
					case 4:
						this.addDefense(1);
						break;
					case 5:
						this.addStrength(1);
						this.addHP(5);
						break;
					case 6:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 7:
						this.addStrength(1);
						break;
					case 8:
						this.addMagic(1);
						break;
					case 9:
						this.addStrength(1);
						break;
					case 10:
						this.addMagic(1);
						this.addDefense(1);
						this.addHP(5);
						break;
					case 11:
						this.addStrength(1);
						break;
					case 12:
						this.addMagic(1);
						break;
					case 13:
						this.addStrength(1);
						break;
					case 14:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 15:
						this.addStrength(1);
						this.addHP(5);
						break;
					case 16:
						this.addMagic(1);
						break;
					case 17:
						this.addStrength(1);
						break;
					case 18:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 19:
						this.addStrength(1);
						break;
					case 20:
						this.addMagic(1);
						this.addHP(5);
						break;
					case 21:
						this.addStrength(1);
						break;
					case 22:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 23:
						this.addStrength(1);
						break;
					case 24:
						this.addMagic(1);
						break;
					case 25:
						this.addStrength(1);
						this.addHP(5);
						break;
					case 26:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 27:
						this.addStrength(1);
						this.addMagic(1);
						break;
					case 28:
						this.addMagic(1);
						break;
					case 29:
						this.addStrength(1);
						break;
					case 30:
						this.addMagic(1);
						this.addDefense(1);
						this.addHP(5);
						break;
					case 31:
						this.addStrength(1);
						break;
					case 32:
						this.addStrength(1);
						this.addMagic(1);
						break;
					case 33:
						this.addStrength(1);
						break;
					case 34:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 35:
						this.addStrength(1);
						this.addHP(5);
						break;
					case 36:
						this.addMagic(1);
						break;
					case 37:
						this.addStrength(1);
						break;
					case 38:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 39:
						this.addStrength(1);
						break;
					case 40:
						this.addMagic(1);
						this.addHP(5);
						break;
					case 41:
						this.addStrength(1);
						break;
					case 42:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 43:
						this.addStrength(1);
						this.addMagic(1);
						break;
					case 44:
						this.addMagic(1);
						break;
					case 45:
						this.addStrength(1);
						this.addHP(5);
						break;
					case 46:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 47:
						this.addStrength(1);
						break;
					case 48:
						this.addStrength(1);
						this.addMagic(1);
						break;
					case 49:
						this.addStrength(1);
						break;
					case 50:
						this.addMagic(1);
						this.addDefense(1);
						this.addHP(5);
						break;
					case 51:
						this.addStrength(1);
						break;
					case 52:
						this.addMagic(1);
						break;
					case 53:
						this.addStrength(1);
						break;
					case 54:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 55:
						this.addStrength(1);
						this.addHP(5);
						break;
					case 56:
						this.addMagic(1);
						break;
					case 57:
						this.addStrength(1);
						break;
					case 58:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 59:
						this.addStrength(1);
						break;
					case 60:
						this.addMagic(1);
						this.addHP(5);
						break;
					case 61:
						this.addStrength(1);
						break;
					case 62:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 63:
						this.addStrength(1);
						break;
					case 64:
						this.addMagic(1);
						break;
					case 65:
						this.addStrength(1);
						this.addHP(5);
						break;
					case 66:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 67:
						this.addStrength(1);
						break;
					case 68:
						this.addMagic(1);
						break;
					case 69:
						this.addStrength(1);
						break;
					case 70:
						this.addMagic(1);
						this.addDefense(1);
						this.addHP(5);
						break;
					case 71:
						this.addStrength(1);
						break;
					case 72:
						this.addMagic(1);
						break;
					case 73:
						this.addStrength(1);
						break;
					case 74:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 75:
						this.addStrength(1);
						this.addHP(5);
						break;
					case 76:
						this.addMagic(1);
						break;
					case 77:
						this.addStrength(1);
						break;
					case 78:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 79:
						this.addStrength(1);
						break;
					case 80:
						this.addMagic(1);
						this.addHP(5);
						break;
					case 81:
						this.addStrength(1);
						break;
					case 82:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 83:
						this.addStrength(1);
						break;
					case 84:
						this.addMagic(1);
						break;
					case 85:
						this.addStrength(1);
						this.addHP(5);
						break;
					case 86:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 87:
						this.addStrength(1);
						break;
					case 88:
						this.addMagic(1);
						break;
					case 89:
						this.addStrength(1);
						break;
					case 90:
						this.addMagic(1);
						this.addDefense(1);
						this.addHP(5);
						break;
					case 91:
						this.addStrength(1);
						break;
					case 92:
						this.addMagic(1);
						break;
					case 93:
						this.addStrength(1);
						break;
					case 94:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 95:
						this.addStrength(1);
						this.addHP(5);
						break;
					case 96:
						this.addMagic(1);
						break;
					case 97:
						this.addStrength(1);
						break;
					case 98:
						this.addMagic(1);
						this.addDefense(1);
						break;
					case 99:
						this.addStrength(1);
						break;
					case 100:
						this.addStrength(10);
						this.addDefense(10);
						this.addMagic(10);
						this.addHP(5);
						break;
				}
			}
		}
    }
}