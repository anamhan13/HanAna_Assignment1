package ro.utcn.sd.dao.factory.impl;

import ro.utcn.sd.dao.GameDao;
import ro.utcn.sd.dao.MatchDao;
import ro.utcn.sd.dao.PlayerDao;
import ro.utcn.sd.dao.TournamentDao;
import ro.utcn.sd.dao.factory.DaoFactory;
import ro.utcn.sd.dao.impl.jdbc.JdbcGameDao;
import ro.utcn.sd.dao.impl.jdbc.JdbcMatchDao;
import ro.utcn.sd.dao.impl.jdbc.JdbcPlayerDao;
import ro.utcn.sd.dao.impl.jdbc.JdbcTournamentDao;

public class JdbcDaoFactory extends DaoFactory {

	@Override
	public GameDao getGameDao() {
		return new JdbcGameDao();
	}

	@Override
	public MatchDao getMatchDao() {
		return new JdbcMatchDao();
	}

	@Override
	public PlayerDao getPlayerDao() {
		return new JdbcPlayerDao();
	}

	@Override
	public TournamentDao getTournamentDao() {
		return new JdbcTournamentDao();
	}
}
