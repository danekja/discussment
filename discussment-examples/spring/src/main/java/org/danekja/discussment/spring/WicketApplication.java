package org.danekja.discussment.spring;

import org.apache.wicket.protocol.http.WebApplication;
import org.danekja.discussment.spring.page.article.ArticlePage;
import org.danekja.discussment.spring.page.dashboard.DashboardPage;
import org.danekja.discussment.spring.page.discussion.DiscussionPage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 *
 */
public class WicketApplication extends WebApplication {

	private static final String PERSISTENCE_UNIT = "discussment-spring";

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * Constructor
	 */
	public WicketApplication()
	{

	}

	@Override
	public void init()
	{
		super.init();

		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		/*AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.scan("org.danekja.discussment.spring.**");
		ctx.refresh();*/


		getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));
		mountPages();
	}

	public Class getHomePage()
	{
		return DashboardPage.class;
	}

	private void mountPages(){
		mountPage("/dashboard", DashboardPage.class);
		mountPage("/article", ArticlePage.class);
		mountPage("/forum", DiscussionPage.class);
	}


}
