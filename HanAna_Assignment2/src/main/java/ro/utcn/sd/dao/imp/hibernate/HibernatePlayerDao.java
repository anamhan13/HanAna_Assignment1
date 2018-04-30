package ro.utcn.sd.dao.imp.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ro.utcn.sd.dao.PlayerDao;
import ro.utcn.sd.model.Player;
import ro.utcn.sd.util.HibernateUtil;

public class HibernatePlayerDao implements PlayerDao {
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Override
	public Player find(long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Player cart = (Player) currentSession.get(Player.class, id);
		transaction.commit();
		return cart;
	}
	
	@SuppressWarnings("unchecked")
	public Player findByMail(String mail) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		List<Player> players = new ArrayList<Player>();
		players = (List<Player>) currentSession.createQuery("from Player P where P.mail =:stat").setParameter("stat", mail).list();
	
		transaction.commit();
		if (players.size() == 0)
			return null;
		return players.get(0);
	}

	public void delete(Player objectToDelete) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.delete(objectToDelete);
		transaction.commit();
	}

	public void update(Player objectToUpdate) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.update(objectToUpdate);
		transaction.commit();
	}

	public void insert(Player objectToCreate) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.persist(objectToCreate);
		transaction.commit();
	}

	public void deleteById(long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		//delete items
		currentSession.createQuery("delete from Items where cart_id= :idParameter").setLong("idParameter", id).executeUpdate();
		//delete cart
		Query hqlQuery = currentSession.createQuery("delete from Player where id= :idParameter");
		hqlQuery.setLong("idParameter", id);
		hqlQuery.executeUpdate();

		transaction.commit();
	}

	public void closeConnection() {
		sessionFactory.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Player> findAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Query query = currentSession.createQuery("from Player");
		List<Player> players = new ArrayList<Player>();
		players = query.list();
		transaction.commit();
		return players;
	}

}