package org.danekja.discussment.article.page.base;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.article.WicketApplication;
import org.danekja.discussment.article.core.dao.jpa.UserDaoJPA;
import org.danekja.discussment.article.core.domain.User;
import org.danekja.discussment.article.core.service.UserService;
import org.danekja.discussment.article.core.service.imp.DefaultUserService;
import org.danekja.discussment.article.page.article.ArticlePage;
import org.danekja.discussment.article.page.dashboard.DashboardPage;
import org.danekja.discussment.article.ui.wicket.form.LoginForm;
import org.danekja.discussment.article.ui.wicket.form.RegistrationForm;
import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.dao.jpa.PermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.dao.jpa.CategoryDaoJPA;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.dao.jpa.PostDaoJPA;
import org.danekja.discussment.core.dao.jpa.TopicDaoJPA;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.core.service.imp.DefaultCategoryService;
import org.danekja.discussment.core.service.imp.DefaultDiscussionService;
import org.danekja.discussment.core.service.imp.DefaultTopicService;

import javax.persistence.EntityManager;


/**
 * Created by Martin Bláha on 21.01.17.
 */
public abstract class BasePage extends WebPage {

    private EntityManager em;

    private UserService userService;
    private AccessControlService accessControlService;
    private PermissionManagementService permissionService;
    private CategoryService categoryService;
    private TopicService topicService;
    private DiscussionService discussionService;

    public BasePage() {

        em = WicketApplication.factory.createEntityManager();
        setMenu();

        add(createLogoutLink());
        add(createLoginAjaxLink());
        add(createRegistrationAjaxLink());
        add(createUsernameLabel());
        add(createUsernameAlert());

        add(new Label("title", new Model<String>(getTitle())));

        PermissionDao permissionDao = new PermissionDaoJPA(em);

        this.userService = new DefaultUserService(new UserDaoJPA(em));
        this.accessControlService = new PermissionService(permissionDao, userService);
        this.permissionService = new PermissionService(permissionDao, userService);
        this.categoryService = new DefaultCategoryService(new CategoryDaoJPA(em), accessControlService, userService);
        this.topicService = new DefaultTopicService(new TopicDaoJPA(em), categoryService, accessControlService, userService);
        this.discussionService = new DefaultDiscussionService(new DiscussionDaoJPA(em), new PostDaoJPA(em), topicService, accessControlService, userService);

        add(new LoginForm("loginForm", userService, new Model<User>(new User())));
        add(new RegistrationForm("registrationForm",
                new DefaultUserService(new UserDaoJPA(em)),
                new Model<User>(new User()),
                permissionService,
                categoryService,
                topicService,
                discussionService));
    }

    private Label createUsernameLabel() {
        IModel<String> model = new Model() {
            @Override
            public String getObject() {
                IDiscussionUser user = userService.getCurrentlyLoggedUser();

                if (user == null) {
                    return "";
                } else {
                    return user.getDisplayName();
                }
            }
        };

        return new Label("username", model);
    }

    private Link createLogoutLink() {
        return new Link("logout") {
            @Override
            public void onClick() {
                getSession().invalidate();
                setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                IDiscussionUser user = userService.getCurrentlyLoggedUser();
                this.setVisible(user != null);
            }
        };
    }

    private AjaxLink createLoginAjaxLink() {
        return new AjaxLink("login"){
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}

            @Override
            protected void onConfigure() {
                super.onConfigure();

                IDiscussionUser user = userService.getCurrentlyLoggedUser();
                this.setVisible(user == null);
            }
        };
    }

    private AjaxLink createRegistrationAjaxLink() {
        return new AjaxLink("registration"){
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}

            @Override
            protected void onConfigure() {
                super.onConfigure();

                IDiscussionUser user = userService.getCurrentlyLoggedUser();
                this.setVisible(user == null);
            }
        };
    }

    private WebMarkupContainer createUsernameAlert() {
         return new WebMarkupContainer("alertUsername") {
            @Override
            protected void onConfigure() {
                super.onConfigure();

                if (getSession().getAttribute("error") != null && getSession().getAttribute("error").equals("username")) {
                    setVisible(true);
                    getSession().setAttribute("error", null);
                } else {
                    setVisible(false);
                }
            }
        };
    }

    private void setMenu() {
        Link dashboardPagePageLink = new Link("dashboardPage") {
            @Override
            public void onClick() {
                setResponsePage(DashboardPage.class);
            }
        };
        add(dashboardPagePageLink);

        Link articlePageLink = new Link("articlePage") {
            @Override
            public void onClick() {
                setResponsePage(ArticlePage.class);
            }
        };
        add(articlePageLink);
    }

    public abstract String getTitle();
}
