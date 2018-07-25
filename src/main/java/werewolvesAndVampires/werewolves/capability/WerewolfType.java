package werewolvesAndVampires.werewolves.capability;

public enum WerewolfType {

	IMMUNE,
	NONE,
	INFECTED,
	FULL;
	
	public static WerewolfType byOrdinal(int ordinal) {
		return values()[ordinal];
	}
}
