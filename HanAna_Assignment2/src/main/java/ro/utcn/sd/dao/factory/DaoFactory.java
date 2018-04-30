package ro.utcn.sd.dao.factory;

import ro.utcn.sd.dao.GameDao;
import ro.utcn.sd.dao.MatchDao;
import ro.utcn.sd.dao.PlayerDao;
import ro.utcn.sd.dao.TournamentDao;
import ro.utcn.sd.dao.factory.impl.HibernateDaoFactory;
import ro.utcn.sd.dao.factory.impl.JdbcDaoFactory;

public abstract class DaoFactory {

	public enum Type {
		HIBERNATE,
		JDBC;
	}


	protected DaoFactory(){

	}

	public static DaoFactory getInstance(Type factoryType) {
		switch (factoryType) {
			case HIBERNATE:
				return new HibernateDaoFactory();
			case JDBC:
				return new JdbcDaoFactory();
			default:
				throw new IllegalArgumentException("Invalid factory");
		}
	}

	public abstract GameDao getGameDao();

	public abstract MatchDao getMatchDao();
	
	public abstract PlayerDao getPlayerDao();
	
	public abstract TournamentDao getTournamentDao();
}
