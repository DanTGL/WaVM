package werewolvesAndVampires.werewolves.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class WerewolfCapability implements IWerewolf {

	private boolean isTransformed = false;

	private int bloodLust = 0;

	private int transformCount = 0;

	private WerewolfType werewolfType = WerewolfType.NONE;
	
	private int entityID = 0;

	@Override
	public boolean getIsTransformed() {
		return isTransformed;
	}

	@Override
	public void setIsTransformed(boolean is) {
		isTransformed = is;
	}

	@Override
	public int getBloodLust() {
		return bloodLust;
	}

	@Override
	public void setBloodLust(int time) {
		bloodLust = time;
	}

	@Override
	public int getTransformCount() {
		return transformCount;
	}

	@Override
	public void incrementTransformCount() {
		++transformCount;
	}

	@Override
	public void setTransformCount(int count) {
		transformCount = count;
	}

	@Override
	public WerewolfType getWerewolfType() {
		return werewolfType;
	}

	@Override
	public void setWerewolfType(WerewolfType wwt) {
		werewolfType = wwt;
	}

	@Override
	public void setEntity(int id) {
		entityID = id;
	}

	@Override
	public int getEntity() {
		return entityID;
	}
	
	private static class WerewolfStorage implements Capability.IStorage<IWerewolf> {

		@Override
		public NBTBase writeNBT(Capability<IWerewolf> capability, IWerewolf instance, EnumFacing side) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("werewolftype", instance.getWerewolfType().ordinal());
			nbt.setBoolean("transformed", instance.getIsTransformed());
			nbt.setInteger("bloodLust", instance.getBloodLust());
			nbt.setInteger("transformcount", instance.getTransformCount());
			nbt.setInteger("eid", instance.getEntity());
			return nbt;
		}

		@Override
		public void readNBT(Capability<IWerewolf> capability, IWerewolf instance, EnumFacing side, NBTBase nbt) {
			if (nbt instanceof NBTTagCompound) {
				NBTTagCompound nbtc = (NBTTagCompound) nbt;
				instance.setWerewolfType(WerewolfType.byOrdinal(nbtc.getInteger("werewolftype")));
				instance.setIsTransformed(nbtc.getBoolean("transformed"));
				instance.setBloodLust(nbtc.getInteger("bloodLust"));
				instance.setTransformCount(nbtc.getInteger("transformcount"));
				instance.setEntity(nbtc.getInteger("eid"));
			}
		}
	}

	public static void register() {
		CapabilityManager.INSTANCE.register(IWerewolf.class, new WerewolfCapability.WerewolfStorage(),
				WerewolfCapability.class);
	}
}
