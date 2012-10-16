package com.altoCloud.dbQuery;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.altoCloud.common.HibernateUtil;
import com.altoCloud.domain.Weather;

public class WeatherQuery {

	public void add(Weather weather) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(weather);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}

	}

	public void remove(Weather item) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
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

	public List<Weather> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Weather> getAllByStationId(Weather template) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		ArrayList<Weather> r = new ArrayList<Weather>();
		try {
			session.beginTransaction();
			org.hibernate.Criteria c = session.createCriteria(Weather.class);
			if (template.getStn_id() != null) {
				c.add(Restrictions.eq("stn_id", template.getStn_id()));
			}
			r = (ArrayList<Weather>) c.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		// System.out.println("find by stn id"
		// + r.get(0).getStn_id().getStn_name());
		return r;
	}

	public List<Weather> getAllByMnetId(int mnet) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<Weather> r = new ArrayList<Weather>();
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("FROM Weather as w join  fetch w.stn_id as s  WHERE s.mnet=:type");
			query.setInteger("type", mnet);

			r = (List<Weather>) query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}

		return r;
	}

	public List<Weather> getAllByState(String state) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		ArrayList<Object> r = new ArrayList<Object>();
		List<Weather> res = new ArrayList<Weather>();
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("FROM Weather as w join w.stn_id as s WHERE s.state=:type");
			query.setString("type", state);
			r = (ArrayList<Object>) query.list();

			// System.out.println(r.get(0).getId());
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		for (int i = 0; i < r.size(); i++) {
			Object[] o = (Object[]) r.get(i);
			Weather obj = (Weather) o[0];
			res.add(obj);
		}
		return res;
	}

	public double findAverageTempByState(String state, int month) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		// ArrayList<Object> r = new ArrayList<Object>();
		double avg = 0;
		List<Double> res = new ArrayList<Double>();
		try {
			session.beginTransaction();
			Query query = session
					.createQuery(" Select w.TMPF FROM Weather as w join w.stn_id as s WHERE s.state=:type");
			query.setString("type", state);
			res = (ArrayList<Double>) query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		int size = res.size();
		int tempsize = size;
		;
		for (int i = 0; i < size; i++) {
			double temp = res.get(i);
			if (temp != -9999.00) {
				avg += temp;
			} else
				tempsize = tempsize - 1;
		}
		return avg / tempsize;

	}

	// public String findHottestState(int month) {
	// Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	// ArrayList<Object> r = new ArrayList<Object>();
	// List<Weather> res = new ArrayList<Weather>();
	// try {
	// session.beginTransaction();
	// Query query = session
	// .createQuery("FROM Weather as w join w.stn_id as s WHERE s.state=:type");
	// query.setString("type", state);
	// r = (ArrayList<Object>) query.list();
	//
	// // System.out.println(r.get(0).getId());
	// session.getTransaction().commit();
	// } catch (HibernateException e) {
	// session.getTransaction().rollback();
	// e.printStackTrace();
	// }
	// for (int i = 0; i < r.size(); i++) {
	// Object[] o = (Object[]) r.get(i);
	// Weather obj = (Weather) o[0];
	// res.add(obj);
	// }
	// return res;
	// }

	public String findHottestState(int month) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		// ArrayList<Object> r = new ArrayList<Object>();
		ArrayList<Result> res = new ArrayList<Result>();
		double avg = 0;
		try {
			session.beginTransaction();
			Query query = session
					.createQuery(" select new com.altoCloud.dbQuery.Result(s.state,avg(w.TMPF)) FROM Weather as w join w.stn_id as s WHERE w.TMPF!=-9999 group by s.state order by avg(w.TMPF) desc");
			// query.setString("type", state);
			res = (ArrayList<Result>) query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return res.get(0).state;
	}

	public Weather findById(long id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Weather r = null;
		try {
			session.beginTransaction();
			r = (Weather) session.load(Weather.class, id);

			// System.out.println(r.getStn_id().getStn_name());
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}

		return r;
	}

	protected boolean validate(Weather p) {
		if (p == null)
			return false;

		// TODO validate values
		return true;
	}

	public void updateTable(Weather weather) {
		if (!validate(weather) || weather.getId() == -1)
			throw new RuntimeException("Invalid weather");

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(weather);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

}
