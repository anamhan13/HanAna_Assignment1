package validation;

import model.Tournament;

public class TournamentValidation {

	public TournamentValidation() {

	}

	public static boolean canPlayerRegister(Tournament tournament) {

		if (!tournament.getStatus().equals("canceled")) {
			if (tournament.getStatus().equals("open registration")) {
				if (tournament.getPlayersList().size() < 8) {
					return true;
				}
			}
		}
		
		return false;
	}

	public static boolean areDatesCronological(Tournament tournament) {

		if (tournament.getDateFinish().before(tournament.getDateStart()))
			return false;
		return true;
	}
	
}
