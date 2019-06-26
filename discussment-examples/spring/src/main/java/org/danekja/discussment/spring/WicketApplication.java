package org.danekja.discussment.spring;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.loader.BundleStringResourceLoader;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.danekja.discussment.spring.page.article.ArticlePage;
import org.danekja.discussment.spring.page.dashboard.DashboardPage;
import org.danekja.discussment.spring.page.discussion.DiscussionPage;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 *
 */
public class WicketApplication extends WebApplication {

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

		getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));
		mountPages();

		getResourceSettings().getStringResourceLoaders().add(new BundleStringResourceLoader("org.danekja.discussment.ui.wicket.Messages"));
	}

	public Class<? extends Page> getHomePage()
	{
		return DashboardPage.class;
	}

	private void mountPages(){
		mountPage("/dashboard", DashboardPage.class);
		mountPage("/article", ArticlePage.class);
		mountPage("/forum", DiscussionPage.class);
	}
}