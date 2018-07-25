package werewolvesAndVampires.packets;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketRegister {
	
	private static int Id = 0;

    public static SimpleNetworkWrapper INSTANCE = null;

    public PacketRegister() {
    }

    public static int nextID() {
        return Id++;
    }

    public static void regMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        INSTANCE.registerMessage(SyncWerewolfCap.Handler.class, SyncWerewolfCap.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(WerewolfCameraUpdate.Handler.class, WerewolfCameraUpdate.class, nextID(), Side.CLIENT);
    }
}
