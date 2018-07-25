package werewolvesAndVampires.werewolves;

public enum MoonPhase {

	FULL,
	WANING_GIBBOUS,
	LAST_QUARTER,
	WANISNG_CRESCENT,
	NEW,
	WAXING_CRESCENT,
	FIRST_QUARTER,
	WAXING_GIBBOUS;

	public int getDaysToFullMoon() {
		return 8 - ordinal();
	}

	public static MoonPhase byOrdinal(int ordinal) {
		return values()[ordinal];
	}

}
