package ro.utcn.sd.dao.factory.impl;

import ro.utcn.sd.dao.GameDao;
import ro.utcn.sd.dao.MatchDao;
import ro.utcn.sd.dao.PlayerDao;
import ro.utcn.sd.dao.TournamentDao;
import ro.utcn.sd.dao.factory.DaoFactory;
import ro.utcn.sd.dao.imp.hibernate.HibernateGameDao;
import ro.utcn.sd.dao.imp.hibernate.HibernateMatchDao;
import ro.utcn.sd.dao.imp.hibernate.HibernatePlayerDao;
import ro.utcn.sd.dao.imp.hibernate.HibernateTournamentDao;

public class HibernateDaoFactory extends DaoFactory {

	@Override
	public GameDao getGameDao() {
		return new HibernateGameDao();
	}

	@Override
	public MatchDao getMatchDao() {
		return new HibernateMatchDao();
	}

	@Override
	public PlayerDao getPlayerDao() {
		return new HibernatePlayerDao();
	}

	@Override
	public TournamentDao getTournamentDao() {
		return new HibernateTournamentDao();
	}

}
