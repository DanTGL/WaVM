package werewolvesAndVampires.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import werewolvesAndVampires.werewolves.WerewolfEventhandler;

public class WerewolfCameraUpdate implements IMessage{
	
	int entityId;
	
	public WerewolfCameraUpdate() {}
	
	public WerewolfCameraUpdate(int id) {
		entityId = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		entityId = buf.readInt();	
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityId);
	}

	public static class Handler implements IMessageHandler<WerewolfCameraUpdate, IMessage> {

		@Override
		public IMessage onMessage(WerewolfCameraUpdate message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(WerewolfCameraUpdate message, MessageContext ctx) {
			Entity spec = Minecraft.getMinecraft().world.getEntityByID(message.entityId);
			WerewolfEventhandler.cam = spec;
		}

	}
}
