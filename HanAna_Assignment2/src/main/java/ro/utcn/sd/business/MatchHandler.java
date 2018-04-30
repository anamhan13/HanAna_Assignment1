package ro.utcn.sd.business;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ro.utcn.sd.dao.MatchDao;
import ro.utcn.sd.dao.factory.DaoFactory;
import ro.utcn.sd.model.Match;

public class MatchHandler {

	private MatchDao matchDao;
	
	public MatchHandler() {
		matchDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getMatchDao();
    }

	public ObservableList<Match> getMatchesList() {

		List<Match> matchesList = new ArrayList<Match>();
		matchesList = matchDao.findAll();

		if (matchesList.size() != 0) {

			ObservableList<Match> observableList = FXCollections.observableArrayList();
			for (Match match : matchesList) {
				observableList.add(match);
			}

			return observableList;
		}

		return null;
	}
}
