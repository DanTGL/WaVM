package werewolvesAndVampires.werewolves.capability;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class WerewolfCapability implements IWerewolf {

	private boolean isTransformed = false;

	private int bloodLust = -1;

	private int transformCount = 0;

	private WerewolfType werewolfType = WerewolfType.NONE;

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

	private static class WerewolfStorage implements Capability.IStorage<IWerewolf> {

		@Override
		public NBTBase writeNBT(Capability<IWerewolf> capability, IWerewolf instance, EnumFacing side) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("werewolftype", instance.getWerewolfType().id);
			nbt.setBoolean("transformed", instance.getIsTransformed());
			nbt.setInteger("bloodLust", instance.getBloodLust());
			nbt.setInteger("transformcount", instance.getTransformCount());
			return nbt;
		}

		@Override
		public void readNBT(Capability<IWerewolf> capability, IWerewolf instance, EnumFacing side, NBTBase nbt) {
			if (nbt instanceof NBTTagCompound) {
				NBTTagCompound nbtc = (NBTTagCompound) nbt;
				instance.setWerewolfType(WerewolfType.getEnumFromId(nbtc.getInteger("werewolftype")));
				instance.setIsTransformed(nbtc.getBoolean("transformed"));
				instance.setBloodLust(nbtc.getInteger("bloodLust"));
				instance.setTransformCount(nbtc.getInteger("transformcount"));
			}
		}
	}

	public static void register() {
		CapabilityManager.INSTANCE.register(IWerewolf.class, new WerewolfCapability.WerewolfStorage(),
				WerewolfCapability.class);
	}
}
