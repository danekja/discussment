package org.danekja.discussment.forum;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.resource.loader.PackageStringResourceLoader;
import org.danekja.discussment.forum.page.article.ArticlePage;
import org.danekja.discussment.forum.page.dashboard.DashboardPage;
import org.danekja.discussment.forum.page.discussion.DiscussionPage;
import org.danekja.discussment.forum.session.UserSession;

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

	@Override
	public void init() {
		super.init();

		mountPages();

		PackageStringResourceLoader l = new PackageStringResourceLoader();
		l.setFilename("Messages.utf8");
		getResourceSettings().getStringResourceLoaders().add(l);
	}

	@Override
	public Session newSession(Request request, Response response)
	{
		return new UserSession(request);
	}

	public Class getHomePage() {
		return DashboardPage.class;
	}

	private void mountPages(){
		mountPage("/dashboard", DashboardPage.class);
		mountPage("/article", ArticlePage.class);
		mountPage("/forum", DiscussionPage.class);
	}
}
