package org.danekja.discussment.forum;

import org.apache.wicket.protocol.http.WebApplication;
import org.danekja.discussment.forum.page.dashboard.DashboardPage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 *
 */
public class WicketApplication extends WebApplication {

	private static final String PERSISTENCE_UNIT = "discussment-forum";
	public static EntityManagerFactory factory;

	/**
	 * Constructor
	 */
	public WicketApplication()
	{
		if (factory == (null)) factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	}

	public Class getHomePage()
	{
		return DashboardPage.class;
	}


}
