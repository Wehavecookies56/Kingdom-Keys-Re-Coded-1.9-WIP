package wehavecookies56.kk.capabilities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import wehavecookies56.kk.api.driveforms.DriveForm;
import wehavecookies56.kk.inventory.InventoryDriveForms;
import wehavecookies56.kk.item.ItemDriveForm;
import wehavecookies56.kk.lib.Strings;

public class DriveStateCapability {
	
	public interface IDriveState {
		boolean getInDrive();
		String getActiveDriveName();
		int getAntiPoints();
		int getDriveLevel(String drive);
		
		void setInDrive(boolean drive);
		void setActiveDriveName(String drive);
		void setAntiPoints(int points);
		void setDriveLevel(String drive, int level);
		void learnDriveForm(DriveForm form);
		
		InventoryDriveForms getInventoryDriveForms();
	}

	public static class Storage implements IStorage<IDriveState> {

		@Override
		public NBTBase writeNBT(Capability<IDriveState> capability, IDriveState instance, EnumFacing side) {
			NBTTagCompound properties = new NBTTagCompound();
			properties.setBoolean("InDrive", instance.getInDrive());
			properties.setString("ActiveDriveName", instance.getActiveDriveName());
			properties.setInteger("AntiPoints", instance.getAntiPoints());
			properties.setInteger("DriveLevelValor", instance.getDriveLevel(Strings.Form_Valor));
			properties.setInteger("DriveLevelWisdom", instance.getDriveLevel(Strings.Form_Wisdom));
			properties.setInteger("DriveLevelLimit", instance.getDriveLevel(Strings.Form_Limit));
			properties.setInteger("DriveLevelMaster", instance.getDriveLevel(Strings.Form_Master));
			properties.setInteger("DriveLevelFinal", instance.getDriveLevel(Strings.Form_Final));
			instance.getInventoryDriveForms().writeToNBT(properties);

			return properties;
		}

		@Override
		public void readNBT(Capability<IDriveState> capability, IDriveState instance, EnumFacing side, NBTBase nbt) {
			NBTTagCompound properties = (NBTTagCompound) nbt;
			instance.setInDrive(properties.getBoolean("InDrive"));
			instance.setActiveDriveName(properties.getString("ActiveDriveName"));
			instance.setAntiPoints(properties.getInteger("AntiPoints"));
			instance.setDriveLevel(Strings.Form_Valor, properties.getInteger("DriveLevelValor"));
			instance.setDriveLevel(Strings.Form_Wisdom, properties.getInteger("DriveLevelWisdom"));
			instance.setDriveLevel(Strings.Form_Limit, properties.getInteger("DriveLevelLimit"));
			instance.setDriveLevel(Strings.Form_Master, properties.getInteger("DriveLevelMaster"));
			instance.setDriveLevel(Strings.Form_Final, properties.getInteger("DriveLevelFinal"));

			instance.getInventoryDriveForms().readFromNBT(properties);
		}
	}
	
	public static class Default implements IDriveState {
        private boolean inDrive = false;
        private String activeDrive = "none";
        int antiPoints = 0;
		private final InventoryDriveForms inventoryDrive = new InventoryDriveForms();
		private static List<String> driveForms = new ArrayList<String>();

		int valor, wisdom, limit, master, Final;
		@Override public boolean getInDrive() { return inDrive; }
		@Override public String getActiveDriveName() { return activeDrive; }
		@Override public int getAntiPoints() { return antiPoints; }
		@Override
		public int getDriveLevel(String drive) {
			switch(drive) {
				case Strings.Form_Valor:
					return valor;
				case Strings.Form_Wisdom:
					return wisdom;
				case Strings.Form_Limit:
					return limit;
				case Strings.Form_Master:
					return master;
				case Strings.Form_Final:
					return Final;
			}
			return 0;
		}

		@Override public void setInDrive(boolean drive) { this.inDrive = drive; }
		@Override public void setActiveDriveName(String drive) { this.activeDrive = drive; }
		@Override public void setAntiPoints(int points) { this.antiPoints = points; }
		@Override
		public void setDriveLevel(String drive, int level) {
			switch (drive) {
				case Strings.Form_Valor:
					valor = level;
				case Strings.Form_Wisdom:
					wisdom = level;
				case Strings.Form_Limit:
					limit = level;
				case Strings.Form_Master:
					master = level;
				case Strings.Form_Final:
					Final = level;
			}
		}
		@Override
		public void learnDriveForm(DriveForm form) {
			driveForms.add(form.getName());
		}
		@Override public InventoryDriveForms getInventoryDriveForms(){return this.inventoryDrive;}

    }
}


