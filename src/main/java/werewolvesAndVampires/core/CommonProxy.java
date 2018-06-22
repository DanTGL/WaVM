package werewolvesAndVampires.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import werewolvesAndVampires.werewolves.capability.WerewolfCapability;

@Mod.EventBusSubscriber
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		
		WVEntities.init();
	}
	
	public void init(FMLInitializationEvent e) {
		WVRecipes.init();
		WerewolfCapability.register();
	}
	
	public void postInit(FMLPostInitializationEvent e) {
		
	}
	
	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
	WVItems.regItems(event);
	WVBlocks.regItemBlocks(event);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
	WVBlocks.regBlocks(event);
    }
}
