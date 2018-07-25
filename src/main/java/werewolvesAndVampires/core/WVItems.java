package werewolvesAndVampires.core;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;
import werewolvesAndVampires.items.*;

@ObjectHolder(WVCore.MODID)
public class WVItems {
	
	//Item References go here
	public static BaseItem werewolf_totem = new ItemWerewolfTotem();
	public static BaseItem wolfsbane_dust = new ItemWolfsbaneDust();

	// Food
	public static BaseItemFood nether_apple = new ItemNetherApple();
	public static ItemGarlic garlic = new ItemGarlic();
	public static ItemGarlicSoup garlic_soup = new ItemGarlicSoup();

	// Test items
	public static BaseItem ww_test_item = new WWTestItem();
	

	public static void regItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		reg.registerAll(
				werewolf_totem,
				wolfsbane_dust,
				nether_apple,
				garlic,
				garlic_soup
		);

		// Test items
		reg.registerAll(
				ww_test_item
		);
	}
	
	public static void regModels() {
		werewolf_totem.regModel();
		wolfsbane_dust.regModel();

		nether_apple.regModel();
		garlic.regModel();
		garlic_soup.regModel();
	}
	
}
