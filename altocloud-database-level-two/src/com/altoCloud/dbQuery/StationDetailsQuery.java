
package com.altoCloud.dbQuery;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.altoCloud.common.HibernateUtil;
import com.altoCloud.domain.Station_Details;

public class StationDetailsQuery {

	public void add(com.altoCloud.domain.Station_Details s_details) {
		// com.altoCloud.domain.LocationHash locator = new
		// com.altoCloud.domain.LocationHash(
		// 2);

		// Session session = locator.locate(s_details.getState());
		System.out.println("station details query called");
		Session session = HibernateUtil.getSessionFactory()
				.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(s_details);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}

	}

	// public void remove(Station_Details item) {
	// Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	// Transaction transaction = null;
	// try {
	// transaction = session.beginTransaction();
	// session.delete(item);
	// transaction.commit();
	// } catch (HibernateException e) {
	// transaction.rollback();
	// e.printStackTrace();
	// }
	//
	// }
	//
	public Station_Details findByKey(String id, String name) {
		// Session session =
		// HibernateUtil1.getSessionFactory().getCurrentSession();
		// Station_Details r = new Station_Details();
		// try {
		// session.beginTransaction();
		// r = (Station_Details) session.load(Station_Details.class,key);
		//
		// // System.out.println(r.getStn_id().getStn_name());
		// session.getTransaction().commit();
		// } catch (HibernateException e) {
		// session.getTransaction().rollback();
		// e.printStackTrace();
		// }
		//
		// return r;
		Session session = HibernateUtil.getSessionFactory()
				.getCurrentSession();
		ArrayList<Station_Details> r = new ArrayList<Station_Details>();
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("FROM Station_Details s WHERE s.stn=:type AND s.stn_name = :type2");
			query.setString("type", id);
			query.setString("type2", name);
			r = (ArrayList<Station_Details>) query.list();
			
           
			//System.out.println(r.get(0).getId());
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}

		return r.get(0);
	}

	public ArrayList<Station_Details> findById(String id) {
		Session session = HibernateUtil.getSessionFactory()
				.getCurrentSession();
		ArrayList<Station_Details> r = new ArrayList<Station_Details>();
		try {
			session.beginTransaction();
			org.hibernate.Criteria c = session
					.createCriteria(Station_Details.class);

			c.add(Restrictions.eq("stn", id));
			r = (ArrayList<Station_Details>) c.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		// System.out.println("find by stn id"
		// + r.get(0).getStn_id().getStn_name());
		return r;
	}
	public Station_Details findByIdInsert(String id,double latitude, double longitude) {
		Session session = HibernateUtil.getSessionFactory()
				.getCurrentSession();
		ArrayList<Station_Details> r = new ArrayList<Station_Details>();
		try {
			session.beginTransaction();
			org.hibernate.Criteria c = session
					.createCriteria(Station_Details.class);

			c.add(Restrictions.eq("stn", id));
			c.add(Restrictions.eq("lat",latitude));
			c.add(Restrictions.eq("lon",longitude));
			r = (ArrayList<Station_Details>) c.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		// System.out.println("find by stn id"
		// + r.get(0).getStn_id().getStn_name());
		return r.get(0);
	}

}
//
// protected boolean validate(Station_Details p) {
// if (p == null)
// return false;
//
// // TODO validate values
// return true;
// }
//
// public void updateTable(Station_Details s_details) {
// if (!validate(s_details) || s_details.getStn() == null)
// throw new RuntimeException("Invalid person");
//
// Session session = HibernateUtil.getSessionFactory().getCurrentSession();
// try {
// session.beginTransaction();
// session.saveOrUpdate(s_details);
// session.getTransaction().commit();
// } catch (RuntimeException e) {
// session.getTransaction().rollback();
// throw e;
// }
// }

