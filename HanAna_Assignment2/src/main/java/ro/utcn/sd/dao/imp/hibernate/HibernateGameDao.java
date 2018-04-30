package ro.utcn.sd.dao.imp.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ro.utcn.sd.dao.GameDao;
import ro.utcn.sd.model.Game;
import ro.utcn.sd.util.HibernateUtil;

public class HibernateGameDao implements GameDao {
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public Game find(long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Game cart = (Game) currentSession.get(Game.class, id);
		transaction.commit();
		return cart;
	}

	public void delete(Game objectToDelete) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.delete(objectToDelete);
		transaction.commit();
	}

	public void update(Game objectToUpdate) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.update(objectToUpdate);
		transaction.commit();
	}

	public void insert(Game objectToCreate) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.persist(objectToCreate);
		transaction.commit();
	}

	public void deleteById(long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		// delete items
		// currentSession.createQuery("delete from games where
		// idGame="+id).executeUpdate();
		// delete cart
		Query hqlQuery = currentSession.createQuery("delete from Game G where G.idGame= :param").setParameter("param",
				id);
		// hqlQuery.setLong("idParameter", id);
		hqlQuery.executeUpdate();

		transaction.commit();
	}

	public void closeConnection() {
		sessionFactory.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Game> findAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Query query = currentSession.createQuery("from Game G");
		List<Game> games = new ArrayList<Game>();
		games = query.list();
		transaction.commit();
		return games;
	}

	public List<Game> findByMatch(long id) {
//		Session currentSession = sessionFactory.getCurrentSession();
//		Transaction transaction = currentSession.beginTransaction();
//		Query query = currentSession.createQuery("from Game G where G.match_idMatch= :param");
//		query.setLong("param", id);
		List<Game> games = new ArrayList<Game>();
//		games = query.list();
//		transaction.commit();
		
		return games;
	}
}