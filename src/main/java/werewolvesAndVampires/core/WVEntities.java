package werewolvesAndVampires.core;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import werewolvesAndVampires.werewolves.entity.EntityAngryPlayer;
import werewolvesAndVampires.werewolves.entity.WerewolfEntity;
import werewolvesAndVampires.werewolves.rendering.RenderPlayerMob;
import werewolvesAndVampires.werewolves.rendering.WerewolfRenderMob;

public class WVEntities {
	
	public static void init() {
	    int id = 1;
	    EntityRegistry.registerModEntity(new ResourceLocation(WVCore.MODID ,"werewolf_entity"), WerewolfEntity.class, "werewolf_entity", id++, WVCore.instance, 64, 3, true, 0xcc0c0c, 0x665b5b);
	    EntityRegistry.registerModEntity(new ResourceLocation(WVCore.MODID ,"player_werewolf_entity"), EntityAngryPlayer.class, "player_werewolf_entity", id++, WVCore.instance, 64, 3, true);
	}
	
	@SideOnly(Side.CLIENT)
    public static void initRender() {
		RenderingRegistry.registerEntityRenderingHandler(WerewolfEntity.class, WerewolfRenderMob.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityAngryPlayer.class, RenderPlayerMob.FACTORY);
	}
}
