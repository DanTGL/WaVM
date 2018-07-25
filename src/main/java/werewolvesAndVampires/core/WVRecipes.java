package werewolvesAndVampires.core;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WVRecipes {
	
	public static void init() {
		GameRegistry.addSmelting(WVBlocks.wolfsbane, new ItemStack(WVItems.wolfsbane_dust), 1.0f);
		GameRegistry.addShapedRecipe(new ResourceLocation(WVCore.MODID, "wolfsbane_cake"), null, new ItemStack(WVBlocks.wolfsbane_cake),
				"MMM",
						 "DED",
						 "WWW",
				'M', Items.MILK_BUCKET,
				'D', WVItems.wolfsbane_dust,
				'E', Items.EGG,
				'W', Items.WHEAT
		);

		GameRegistry.addShapedRecipe(new ResourceLocation(WVCore.MODID, "nether_apple"), null, new ItemStack(WVItems.nether_apple),
				"WSW",
					     "SAS",
					     "WSW",
				'W', Items.NETHER_WART,
				'S', Items.NETHER_STAR,
				'A', Items.APPLE
		);

		GameRegistry.addShapelessRecipe(new ResourceLocation(WVCore.MODID, "garlic_soup"), null, new ItemStack(WVItems.garlic_soup),
				Ingredient.fromItem(WVItems.garlic),
				Ingredient.fromItem(WVItems.garlic),
				Ingredient.fromItem(WVItems.garlic),
				Ingredient.fromItem(Items.BOWL)
		);
	}
	
}
