package validation;

public class GameEndValidation {

	public GameEndValidation() {

	}

	public static boolean needRematch(int score1, int score2) {

		if (score1 == 11 || score2 == 11) {
			if (Math.abs(score1-score2)<2)
				return true;
		}
		return false;
	}

	public static int detectGameEnd(int score1, int score2) {

		if (score1 == 11 && score1 - score2 > 1)
			return 1;
		if (score2 == 11 && score2 - score1 > 1)
			return 2;

		return -1;
	}

}
