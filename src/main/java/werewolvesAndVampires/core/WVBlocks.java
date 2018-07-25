package werewolvesAndVampires.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;
import werewolvesAndVampires.blocks.BlockGarlicCrop;
import werewolvesAndVampires.blocks.BlockWolfsbane;
import werewolvesAndVampires.blocks.BlockWolfsbaneCake;
import werewolvesAndVampires.blocks.BlockWolfsbaneDust;

@ObjectHolder(WVCore.MODID)
public class WVBlocks {
	
	//Block References go here
	public static BlockWolfsbaneCake wolfsbane_cake = new BlockWolfsbaneCake();
	public static BlockWolfsbane wolfsbane = new BlockWolfsbane();
	public static BlockWolfsbaneDust wolfsbane_dust = new BlockWolfsbaneDust();

	//Crops
	public static BlockGarlicCrop garlic_crop = new BlockGarlicCrop();

	public static void regBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> reg = event.getRegistry();
		reg.registerAll(
				wolfsbane,
				wolfsbane_cake,
				wolfsbane_dust,
				garlic_crop
		);
	}
	
	public static void regItemBlocks(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		reg.registerAll(
				new ItemBlock(wolfsbane_cake).setRegistryName(wolfsbane_cake.getRegistryName()),
				new ItemBlock(wolfsbane).setRegistryName(wolfsbane.getRegistryName())
		);
	}
	
	public static void regModels() {
		wolfsbane_cake.regModel();
		wolfsbane.regModel();
		wolfsbane_dust.regModel();
	}
	
}
