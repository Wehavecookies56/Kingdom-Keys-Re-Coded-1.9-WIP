package wehavecookies56.kk.capabilities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import wehavecookies56.kk.entities.PlayerLevel;
import wehavecookies56.kk.inventory.InventoryKeychain;
import wehavecookies56.kk.inventory.InventoryPotionsMenu;
import wehavecookies56.kk.inventory.InventorySpells;
import wehavecookies56.kk.item.ItemKKPotion;
import wehavecookies56.kk.item.ItemSpellOrb;

public class PlayerStatsCapability {
	
	public interface IPlayerStats {
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
		void addExperience(int experience);
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
		
		InventoryKeychain getInventoryKeychain();
		InventoryPotionsMenu getInventoryPotionsMenu();
		InventorySpells getInventorySpells();

		List getSpellsList();
		List getItemsList();
		
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
			
			instance.getInventoryKeychain().writeToNBT(properties);
			instance.getInventoryPotionsMenu().writeToNBT(properties);
			instance.getInventorySpells().writeToNBT(properties);

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
			
			instance.getInventoryKeychain().readFromNBT(properties);
			instance.getInventoryPotionsMenu().readFromNBT(properties);
			instance.getInventorySpells().readFromNBT(properties);
			
			instance.getSpellsList().clear();
			for (int i = 0; i < instance.getInventorySpells().getSizeInventory(); i++) {
				if (instance.getInventorySpells().getStackInSlot(i) != null) {
					instance.getSpellsList().add(((ItemSpellOrb) instance.getInventorySpells().getStackInSlot(i).getItem()).getMagicName());
				}
			}
			
			instance.getItemsList().clear();
			for (int i = 0; i < instance.getInventoryPotionsMenu().getSizeInventory(); i++)
				if (instance.getInventoryPotionsMenu().getStackInSlot(i) != null) instance.getItemsList().add(((ItemKKPotion) instance.getInventoryPotionsMenu().getStackInSlot(i).getItem()).getItemName());
			
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
		
		private final InventoryKeychain inventoryKeychain = new InventoryKeychain();
		private final InventoryPotionsMenu inventoryPotions = new InventoryPotionsMenu();
		private final InventorySpells inventorySpells = new InventorySpells();
		
		private static List<String> spells = new ArrayList<String>();
		private static List<String> items = new ArrayList<String>();
		
		@Override public InventoryKeychain getInventoryKeychain(){return this.inventoryKeychain;}
		@Override public InventoryPotionsMenu getInventoryPotionsMenu(){return this.inventoryPotions;}
		@Override public InventorySpells getInventorySpells(){return this.inventorySpells;}
		
		@Override public List getSpellsList(){return this.spells;}
		@Override public List getItemsList(){return this.items;}

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
		
		@Override public boolean setLevel(int level) { if (level <= this.maxLevel) { this.level = level; return true; } return false;}
		@Override public boolean setExperience(int experience) { if (experience <= this.maxExperience) { this.experience = experience; return true; } return false; }
		@Override public void addExperience(int experience) { if (this.experience + experience <= this.maxExperience) { this.experience += experience; } this.experience = this.maxExperience; }
		@Override public void setStrength(int strength) { this.strength = strength; }
		@Override public void addStrength(int strength) { 
			this.strength += strength; 
			PlayerLevel.messages.add("str");
		}
		@Override public void setDefense(int defense) { this.defense = defense; }
		@Override public void addDefense(int defense) { 
			this.defense += defense; 
			PlayerLevel.messages.add("def");
		}
		@Override public void setMagic(int magic) { this.magic = magic; }
		@Override public void addMagic(int magic) { 
			this.magic += magic; 
			PlayerLevel.messages.add("mag");
		}
		@Override public int setHP(int hp) { 
			this.hp = hp; 
			return this.hp;
		}
		@Override public int addHP(int hp) { 
			this.hp += hp; 
			PlayerLevel.messages.add("hp");
			return this.hp;
		}
        @Override public boolean setDP(double dp) { if (dp <= this.maxDP) {this.dp = dp; return true; } return false; }
        @Override public void addDP(double dp) { if (dp + this.dp > this.maxDP) this.dp = this.maxDP; else this.dp += dp; }
        @Override public void remDP(double dp) { if (dp + this.dp < 0) this.dp = 0; else this.dp -= dp; }
        @Override public boolean setMP(double mp) { if (mp <= this.maxMP) {this.mp = mp; return true; } return false; }
        @Override public void addMP(double mp) { if (mp + this.mp > this.maxMP) this.mp = this.maxMP; else this.mp += mp; }
        @Override public void remMP(double mp) { if (mp + this.mp < 0) this.mp = 0; else this.mp -= mp; }
        @Override public void setMaxMP(double maxMP) { this.maxMP = mp;}
		@Override public void setRecharge(boolean recharge) { this.recharge = recharge; }
    }
}