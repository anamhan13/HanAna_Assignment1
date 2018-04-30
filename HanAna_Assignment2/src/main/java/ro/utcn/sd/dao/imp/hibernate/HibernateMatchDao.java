package ro.utcn.sd.dao.imp.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ro.utcn.sd.dao.MatchDao;
import ro.utcn.sd.model.Match;
import ro.utcn.sd.model.Player;
import ro.utcn.sd.model.Tournament;
import ro.utcn.sd.util.HibernateUtil;

public class HibernateMatchDao implements MatchDao {
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public Match find(long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Match cart = (Match) currentSession.get(Match.class, id);
		transaction.commit();
		return cart;
	}

	public void delete(Match objectToDelete) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.delete(objectToDelete);
		transaction.commit();
	}

	public void update(Match objectToUpdate) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.update(objectToUpdate);
		transaction.commit();
	}

	public void insert(Match objectToCreate) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.persist(objectToCreate);
		transaction.commit();
	}

	public void deleteById(long id) {
		// Session currentSession = sessionFactory.getCurrentSession();
		// Transaction transaction = currentSession.beginTransaction();
		// // delete items
		// currentSession.createQuery("delete from Items where cart_id=
		// :idParameter").setLong("idParameter", id).executeUpdate();
		// // delete cart
		// Query hqlQuery = currentSession.createQuery("delete from Match where
		// id= :idParameter");
		// hqlQuery.setLong("idParameter", id);
		// hqlQuery.executeUpdate();
		//
		// transaction.commit();
	}

	public void closeConnection() {
		sessionFactory.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Match> findAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Query query = currentSession.createQuery("from Match");
		List<Match> matches = new ArrayList<Match>();
		matches = query.list();
		transaction.commit();
		return matches;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Match> findByPlayer(Player player) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Query query = currentSession.createQuery("from Match");
		List<Match> matches = new ArrayList<Match>();
		matches = query.list();
		transaction.commit();

		List<Match> meciuri = new ArrayList<>();

		if (!matches.isEmpty())
			for (Match match : matches) {
				if (match.getIdPlayer1() == player.getId() || match.getIdPlayer2() == player.getId())
					meciuri.add(match);
			}
		return meciuri;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Match> findByTournament(Tournament tournament) {
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Query query = currentSession.createQuery("from Match");
		List<Match> matches = new ArrayList<Match>();
		matches = query.list();
		transaction.commit();
		List<Match> meciuri = new ArrayList<>();
		if (!matches.isEmpty()) {
			for (Match match : matches) {
				if (match.getIdTournament() == tournament.getIdTournament())
					meciuri.add(match);
			}
		}
		
		return meciuri;
	}
}