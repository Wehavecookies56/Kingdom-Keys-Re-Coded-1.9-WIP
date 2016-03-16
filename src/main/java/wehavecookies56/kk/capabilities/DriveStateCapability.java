package wehavecookies56.kk.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTBase.NBTPrimitive;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.test.NoBedSleepingTest.IExtraSleeping;

public class DriveStateCapability {
	
	public interface IDriveState {
		boolean getInDrive();
		String getActiveDriveName();
		int getAntiPoints();
		
		void setInDrive(boolean drive);
		void setActiveDriveName(String drive);
		void setAntiPoints(int points);
	}

	public static class Storage implements IStorage<IDriveState> {

		@Override
		public NBTBase writeNBT(Capability<IDriveState> capability, IDriveState instance, EnumFacing side) {
			NBTTagCompound properties = new NBTTagCompound();
			properties.setBoolean("InDrive", instance.getInDrive());
			properties.setString("ActiveDriveName", instance.getActiveDriveName());
			properties.setInteger("AntiPoints", instance.getAntiPoints());
			
			return properties;
		}

		@Override
		public void readNBT(Capability<IDriveState> capability, IDriveState instance, EnumFacing side, NBTBase nbt) {
			NBTTagCompound properties = (NBTTagCompound) nbt;
			instance.setInDrive(properties.getBoolean("InDrive"));
			instance.setActiveDriveName(properties.getString("ActiveDriveName"));
			instance.setAntiPoints(properties.getInteger("AntiPoints"));
			
		}
	}
	
	public static class Default implements IDriveState {
        private boolean inDrive = false;
        private String activeDrive = "";
        int antiPoints = 0;
		@Override public boolean getInDrive() { return inDrive; }
		@Override public String getActiveDriveName() { return activeDrive; }
		@Override public int getAntiPoints() { return antiPoints; }
		@Override public void setInDrive(boolean drive) { this.inDrive = drive; }
		@Override public void setActiveDriveName(String drive) { this.activeDrive = drive; }
		@Override public void setAntiPoints(int points) { this.antiPoints = points; }
        
    }
}


