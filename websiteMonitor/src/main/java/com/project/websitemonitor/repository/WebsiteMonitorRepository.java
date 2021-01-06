package com.project.websitemonitor.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.websitemonitor.entity.Check;
import com.project.websitemonitor.entity.WebsiteMonitor;

@Repository
@Transactional
public class WebsiteMonitorRepository {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Save the check in to database
	 * 
	 * @param check
	 * @return persisted check contains generated Id
	 */
	@Transactional
	public Check saveCheck(Check check) {
		getSession().save(check);
		return check;
	}

	/**
	 * Updates the check as enable/disable, disable check will not do monitoring of
	 * website
	 * 
	 * @param isEnable
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public void updateCheck(boolean isEnable, int id) {
		Query query = getSession().createQuery("UPDATE Check SET enabled=:isEnable WHERE id=:id");
		query.setParameter("isEnable", isEnable);
		query.setParameter("id", id);
		query.executeUpdate();
	}

	/**
	 * Returns all the created checks
	 * 
	 * @return list of checks
	 */
	public List<Check> getAllChecks() {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Check> criteria = builder.createQuery(Check.class);
		criteria.from(Check.class);
		List<Check> data = getSession().createQuery(criteria).getResultList();
		return data;
	}

	/**
	 * Returns the filtered checks by name
	 * 
	 * @param name
	 * @return filtered checks
	 */
	public List<Check> getFilteredChecks(String name) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Check> criteria = builder.createQuery(Check.class);
		Root<Check> root = criteria.from(Check.class);
		criteria.where(builder.like(root.get("name"), name));
		List<Check> data = getSession().createQuery(criteria).getResultList();
		return data;
	}

	/**
	 * Returns the filtered checks by interval
	 * 
	 * @param interval
	 * @return filtered checks
	 */
	public List<Check> getFilteredChecks(int interval) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Check> criteria = builder.createQuery(Check.class);
		Root<Check> root = criteria.from(Check.class);
		criteria.where(builder.equal(root.get("frequency"), interval));
		List<Check> data = getSession().createQuery(criteria).getResultList();
		return data;
	}

	/**
	 * Fetches only active checks from the database
	 * 
	 * @return
	 */
	public List<Check> getActiveChecks() {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Check> criteria = builder.createQuery(Check.class);
		Root<Check> root = criteria.from(Check.class);
		criteria.where(builder.equal(root.get("enabled"), true));
		List<Check> data = getSession().createQuery(criteria).getResultList();
		return data;
	}

	/**
	 * Return check by its Id
	 * 
	 * @param id
	 * @return check
	 */
	public Check getCheck(int id) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Check> criteria = builder.createQuery(Check.class);
		Root<Check> root = criteria.from(Check.class);
		criteria.where(builder.equal(root.get("id"), id));
		List<Check> data = getSession().createQuery(criteria).getResultList();
		return data.get(0);
	}

	/**
	 * Save the status, ping, response time, url of website by the scheduled jobs
	 * 
	 * @param monitor
	 */
	@Transactional
	public void saveWebsiteMonitor(WebsiteMonitor monitor) {
		getSession().save(monitor);
	}

	/**
	 * Return the object which contains website status, url, average response time
	 * and uptime/downtime
	 * 
	 * @param websiteUrl
	 * @return
	 */
	public WebsiteMonitor getWebsiteStatus(String websiteUrl) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<WebsiteMonitor> criteria = builder.createQuery(WebsiteMonitor.class);
		Root<WebsiteMonitor> root = criteria.from(WebsiteMonitor.class);
		criteria.orderBy(builder.desc(root.get("id")));
		criteria.where(builder.like(root.get("website_url"), websiteUrl));
		List<WebsiteMonitor> data = getSession().createQuery(criteria).setFirstResult(0).setMaxResults(1)
				.getResultList();
		return data.get(0);
	}

	/**
	 * Returns the last 10 records of website status to calculate average response,
	 * number of records can be externalize, as of now taking 10
	 * 
	 * @param websiteUrl
	 * @return list of website monitors objects
	 */
	public List<WebsiteMonitor> getWebsiteStatusForAvgResponse(String websiteUrl) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<WebsiteMonitor> criteria = builder.createQuery(WebsiteMonitor.class);
		Root<WebsiteMonitor> root = criteria.from(WebsiteMonitor.class);
		criteria.orderBy(builder.desc(root.get("id")));
		Predicate predicateForStatus = builder.equal(root.get("status"), "UP");
		Predicate predicateForUrl = builder.equal(root.get("website_url"), websiteUrl);
		Predicate finalPredicate = builder.and(predicateForStatus, predicateForUrl);
		criteria.where(finalPredicate);
		// Taking average response on the basis of last 10 records, it can be increased
		// or externalize for customized
		List<WebsiteMonitor> data = getSession().createQuery(criteria).setFirstResult(0).setMaxResults(10)
				.getResultList();
		return data;
	}

}
