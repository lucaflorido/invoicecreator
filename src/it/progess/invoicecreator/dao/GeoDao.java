package it.progess.invoicecreator.dao;

import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.TblCity;
import it.progess.invoicecreator.pojo.TblCountry;
import it.progess.invoicecreator.pojo.TblTaxrate;
import it.progess.invoicecreator.pojo.TblZone;
import it.progess.invoicecreator.vo.City;
import it.progess.invoicecreator.vo.Country;
import it.progess.invoicecreator.vo.TaxRate;
import it.progess.invoicecreator.vo.User;
import it.progess.invoicecreator.vo.Zone;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

public class GeoDao {
	public ArrayList<Country> getCountries(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Country> list = new ArrayList<Country>();
		try{
			session.clear();
			Criteria cr = session.createCriteria(TblCountry.class,"country");
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblCountry> countries = cr.list();
			if (countries.size() > 0){
				for (Iterator<TblCountry> iterator = countries.iterator(); iterator.hasNext();){
					TblCountry tblcountry = iterator.next();
					Country country = new Country();
					country.convertFromTable(tblcountry);
					list.add(country);
				}
			}
			session.clear();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			
			session.close();
		}
		return list;
	}
	public ArrayList<Zone> getZones(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Zone> list = new ArrayList<Zone>();
		try{
			session.clear();
			Criteria cr = session.createCriteria(TblZone.class,"zone");
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblZone> zones = cr.list();
			if (zones.size() > 0){
				for (Iterator<TblZone> iterator = zones.iterator(); iterator.hasNext();){
					TblZone tblzone = iterator.next();
					Zone zone = new Zone();
					zone.convertFromTable(tblzone);
					list.add(zone);
				}
			}
			session.clear();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			
			session.close();
		}
		return list;
	}
	public ArrayList<Zone> getZones(int idcountry){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Zone> list = new ArrayList<Zone>();
		try{
			session.clear();
			Criteria cr = session.createCriteria(TblZone.class,"zone");
			cr.createAlias("zone.country", "country");
			cr.add(Restrictions.eq("country.idcountry", idcountry));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblZone> zones = cr.list();
			if (zones.size() > 0){
				for (Iterator<TblZone> iterator = zones.iterator(); iterator.hasNext();){
					TblZone tblzone = iterator.next();
					Zone zone = new Zone();
					zone.convertFromTable(tblzone);
					list.add(zone);
				}
			}
			session.clear();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			
			session.close();
		}
		return list;
	}
	public ArrayList<City> getCities(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<City> list = new ArrayList<City>();
		try{
			session.clear();
			Criteria cr = session.createCriteria(TblCity.class,"city");
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblCity> cities = cr.list();
			if (cities.size() > 0){
				for (Iterator<TblCity> iterator = cities.iterator(); iterator.hasNext();){
					TblCity tblcity = iterator.next();
					City city = new City();
					city.convertFromTable(tblcity);
					list.add(city);
				}
			}
			session.clear();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			
			session.close();
		}
		return list;
	}
	public ArrayList<City> getCitiesByZone(int idzone){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<City> list = new ArrayList<City>();
		try{
			session.clear();
			Criteria cr = session.createCriteria(TblCity.class,"city");
			cr.createAlias("city.zone", "zone");
			cr.add(Restrictions.eq("zone.idzone", idzone));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblCity> cities = cr.list();
			if (cities.size() > 0){
				for (Iterator<TblCity> iterator = cities.iterator(); iterator.hasNext();){
					TblCity tblcity = iterator.next();
					City city = new City();
					city.convertFromTable(tblcity);
					list.add(city);
				}
			}
			session.clear();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			
			session.close();
		}
		return list;
	}
	public ArrayList<City> getCitiesByCountry(int idcountry){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<City> list = new ArrayList<City>();
		try{
			session.clear();
			Criteria cr = session.createCriteria(TblCity.class,"city");
			cr.createAlias("city.country", "country");
			cr.add(Restrictions.eq("country.idcountry", idcountry));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblCity> cities = cr.list();
			if (cities.size() > 0){
				for (Iterator<TblCity> iterator = cities.iterator(); iterator.hasNext();){
					TblCity tblcity = iterator.next();
					City city = new City();
					city.convertFromTable(tblcity);
					list.add(city);
				}
			}
			session.clear();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			
			session.close();
		}
		return list;
	}
}
