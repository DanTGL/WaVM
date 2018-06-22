package werewolvesAndVampires.core;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WVRecipes {
	
	public static void init() {
		GameRegistry.addSmelting(WVBlocks.wolfsbane, new ItemStack(WVItems.wolfsbane_dust), 1.0f);
		GameRegistry.addShapedRecipe(new ResourceLocation(WVCore.MODID, "wolfsbane_cake"), null, new ItemStack(WVBlocks.wolfsbane_cake), "MMM", "DED", "WWW", 'M', Items.MILK_BUCKET, 'D', WVItems.wolfsbane_dust, 'E', Items.EGG, 'W', Items.WHEAT);
	}
	
}
