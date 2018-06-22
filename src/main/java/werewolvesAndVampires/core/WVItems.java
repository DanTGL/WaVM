package werewolvesAndVampires.core;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import werewolvesAndVampires.items.BaseItem;
import werewolvesAndVampires.items.ItemWerewolfTotem;
import werewolvesAndVampires.items.ItemWolfsbaneDust;

@ObjectHolder(WVCore.MODID)
public class WVItems {
	
	//Item References go here
	public static BaseItem werewolf_totem = new ItemWerewolfTotem();
	public static BaseItem wolfsbane_dust = new ItemWolfsbaneDust();
	
	

	public static void regItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(werewolf_totem);
		event.getRegistry().register(wolfsbane_dust);
	}
	
	public static void regModels() {
		werewolf_totem.regModel();
		wolfsbane_dust.regModel();
	}
	
}
