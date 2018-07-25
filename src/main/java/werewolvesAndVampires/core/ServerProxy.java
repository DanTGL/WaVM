package werewolvesAndVampires.core;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ServerProxy extends CommonProxy {

	@Override
	public World getWorld() {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
	}
}
