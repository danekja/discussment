package org.danekja.discussment.spring;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.resource.loader.PackageStringResourceLoader;
import org.danekja.discussment.spring.page.article.ArticlePage;
import org.danekja.discussment.spring.page.dashboard.DashboardPage;
import org.danekja.discussment.spring.page.discussion.DiscussionPage;
import org.danekja.discussment.spring.session.UserSession;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
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

		PackageStringResourceLoader l = new PackageStringResourceLoader();
		l.setFilename("Messages.utf8");
		getResourceSettings().getStringResourceLoaders().add(l);
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

	@Override
	public Session newSession(Request request, Response response)
	{
		return new UserSession(request);
	}
}