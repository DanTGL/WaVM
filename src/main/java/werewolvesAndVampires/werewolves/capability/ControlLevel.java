package werewolvesAndVampires.werewolves.capability;

public enum ControlLevel {
	NONE(0), ALL(1);

	public final int id;

	ControlLevel(int idd) {
		id = idd;
	}

	public static ControlLevel getEnumFromId(int in) {
		switch (in) {
		case 0:
			return ControlLevel.NONE;
		case 1:
			return ControlLevel.ALL;
		default:
			return ControlLevel.ALL;
		}
	}
}
