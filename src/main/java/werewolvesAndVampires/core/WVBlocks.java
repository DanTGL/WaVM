package werewolvesAndVampires.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import werewolvesAndVampires.blocks.BlockWolfsbane;
import werewolvesAndVampires.blocks.BlockWolfsbaneCake;

@ObjectHolder(WVCore.MODID)
public class WVBlocks {
	
	//Block References go here
	public static BlockWolfsbaneCake wolfsbane_cake = new BlockWolfsbaneCake();
	public static BlockWolfsbane wolfsbane = new BlockWolfsbane();
	
	
	public static void regBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(wolfsbane_cake);
		event.getRegistry().register(wolfsbane);
	}
	
	public static void regItemBlocks(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new ItemBlock(wolfsbane_cake).setRegistryName(wolfsbane_cake.getRegistryName()));
		event.getRegistry().register(new ItemBlock(wolfsbane).setRegistryName(wolfsbane.getRegistryName()));
	}
	
	public static void regModels() {
		wolfsbane_cake.regModel();
		wolfsbane.regModel();
	}
	
}
