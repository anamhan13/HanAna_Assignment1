package ro.utcn.sd.dao.imp.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ro.utcn.sd.dao.TournamentDao;
import ro.utcn.sd.model.Tournament;
import ro.utcn.sd.util.HibernateUtil;

public class HibernateTournamentDao implements TournamentDao {
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public Tournament find(long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Tournament cart = (Tournament) currentSession.get(Tournament.class, id);
		transaction.commit();
		return cart;
	}

	public void delete(Tournament objectToDelete) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.delete(objectToDelete);
		transaction.commit();
	}

	public void update(Tournament objectToUpdate) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.update(objectToUpdate);
		transaction.commit();
	}

	public void insert(Tournament objectToCreate) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.persist(objectToCreate);
		transaction.commit();
	}

	
	//SCHIMBAAAA
	public void deleteById(long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		//delete items
		currentSession.createQuery("delete from Items where cart_id= :idParameter").setLong("idParameter", id).executeUpdate();
		//delete cart
		Query hqlQuery = currentSession.createQuery("delete from Tournament where id= :idParameter");
		hqlQuery.setLong("idParameter", id);
		hqlQuery.executeUpdate();

		transaction.commit();
	}

	public void closeConnection() {
		sessionFactory.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tournament> findAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		List<Tournament> tournaments = new ArrayList<Tournament>();
		tournaments = currentSession.createQuery("from Tournament T").list();
		transaction.commit();
		return tournaments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tournament> findByStatus(String status) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		
		List<Tournament> tournaments = new ArrayList<Tournament>();
		tournaments= currentSession.createQuery("from Tournament T where T.status= :param").setParameter("param", status).list();
		transaction.commit();
		for (Tournament tournament : tournaments)
			System.out.println(tournament.getStatus());
		
		return tournaments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Tournament findByName(String name) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		
		List<Tournament> tournaments = new ArrayList<Tournament>();
		tournaments= currentSession.createQuery("from Tournament T where T.name= :stat").setParameter("stat", name).list();
		transaction.commit();
		if (tournaments.isEmpty())
			return null;
		return tournaments.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tournament> findByType(String type) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		List<Tournament> tournaments = new ArrayList<Tournament>();
		
		if (type.equals("Free"))
			tournaments = currentSession.createQuery("from Tournament T where T.paid=0").list();
		else 
			tournaments = currentSession.createQuery("from Tournament T where T.paid=1").list();
	
		transaction.commit();
		return tournaments;
	}
}