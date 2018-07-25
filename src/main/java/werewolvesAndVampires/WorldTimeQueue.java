package werewolvesAndVampires;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

/*
 * Allows you to execute an action at a certain in-game time
 */
@Mod.EventBusSubscriber
public class WorldTimeQueue {
	
	public static final Map<Long, List<Runnable>> queue = new HashMap<>();

	/**
	 * Schedule an action(s)
	 * @param time Time to schedule for (in ticks)
	 * @param runnables Code to execute
	 */
	public static void scheduleFor(long time, Runnable...runnables) {
		List<Runnable> list = queue.getOrDefault(time, new ArrayList<>());

		list.addAll(Arrays.asList(runnables));
		
		if (!queue.containsKey(time))
			queue.put(time, list);
	}

	@SubscribeEvent
	public static void onWorldTick(TickEvent.WorldTickEvent e) {
		if (!e.world.isRemote && e.phase == TickEvent.Phase.START) {
			List<Runnable> list = queue.getOrDefault(e.world.getWorldTime(), Collections.emptyList());
			
			for (Runnable run : list) {
				run.run();
			}

			list.clear();
		}
	}
	
}
