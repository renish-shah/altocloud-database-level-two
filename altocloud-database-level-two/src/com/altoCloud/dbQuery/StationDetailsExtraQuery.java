package com.altoCloud.dbQuery;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import com.altoCloud.common.HibernateUtil1;
import com.altoCloud.domain.StationDetailsExtra;
import com.altoCloud.domain.StationDetailsExtra;

public class StationDetailsExtraQuery {
	public void add(StationDetailsExtra s_details) {
		Session session = HibernateUtil1.getSessionFactory()
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

	public void remove(StationDetailsExtra item) {
		Session session = HibernateUtil1.getSessionFactory()
				.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(item);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}

	}

	public StationDetailsExtra findById(String id) {
		Session session = HibernateUtil1.getSessionFactory()
				.getCurrentSession();
		StationDetailsExtra r = null;
		try {
			session.beginTransaction();
			r = (StationDetailsExtra) session.load(
					StationDetailsExtra.class, id);

			// System.out.println(r.getStn_id().getStn_name());
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}

		return r;
	}

	public List<StationDetailsExtra> getAllByMnetId(String id) {
		Session session = HibernateUtil1.getSessionFactory()
				.getCurrentSession();
		List<StationDetailsExtra> r = new ArrayList<StationDetailsExtra>();
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("FROM StationDetailsExtra as sd join  fetch sd.stn_details as s  WHERE s.stn=:type");
			query.setString("type", id);

			r = (List<StationDetailsExtra>) query.list();
			System.out.println(r.get(0).getStn_details().getState());
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}

		return r;
	}

	protected boolean validate(StationDetailsExtra p) {
		if (p == null)
			return false;

		// TODO validate values
		return true;
	}

	public void updateTable(StationDetailsExtra s_details) {
		if (!validate(s_details) || s_details.getStn_details() == null)
			throw new RuntimeException("Invalid person");

		Session session = HibernateUtil1.getSessionFactory()
				.getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(s_details);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

}
