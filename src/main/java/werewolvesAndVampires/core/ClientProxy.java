package werewolvesAndVampires.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import werewolvesAndVampires.blocks.BlockWolfsbaneDust;

import java.awt.*;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		WVEntities.initRender();
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);

		BlockColors colors = Minecraft.getMinecraft().getBlockColors();
		colors.registerBlockColorHandler((state, worldIn, pos, tintIndex) -> 0xAA8FC4, WVBlocks.wolfsbane_dust);
	}

	@Override
	public World getWorld() {
		return Minecraft.getMinecraft().world;
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		WVItems.regModels();
		WVBlocks.regModels();
	}

}
