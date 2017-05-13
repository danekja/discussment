package org.danekja.discussment.example.base;

import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.example.LoginForm;
import org.danekja.discussment.example.RegistrationForm;
import org.danekja.discussment.example.article.ArticlePage;
import org.danekja.discussment.example.dashboard.DashboardPage;
import org.danekja.discussment.example.discussion.DiscussionPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;


/**
 * Created by Martin Bláha on 21.01.17.
 */
public abstract class BasePage extends WebPage {

    private AjaxLink loginLink;
    private AjaxLink registrationLink;
    private Link logoutLink;
    private Label usernameLabel;

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        User user = (User) getSession().getAttribute("user");

        if (user == null) {
            loginLink.setVisible(true);
            logoutLink.setVisible(false);
            usernameLabel.setVisible(false);
            registrationLink.setVisible(true);
        } else {
            loginLink.setVisible(false);
            logoutLink.setVisible(true);
            usernameLabel.setVisible(true);
            usernameLabel.setDefaultModelObject(user.getUsername());
            registrationLink.setVisible(false);
        }
    }

    public BasePage() {

        setMenu();

        logoutLink = new Link("logout") {
            @Override
            public void onClick() {
                getSession().removeAttribute("user");
            }
        };
        add(logoutLink);

        loginLink = new AjaxLink("login"){
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}
        };
        add(loginLink);

        registrationLink = new AjaxLink("registration"){
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}
        };
        add(registrationLink);


        usernameLabel = new Label("username", Model.of(""));
        add(usernameLabel);

        Label titleLabel = new Label("title", new Model(getTitle()));
        add(titleLabel);


        add(new LoginForm("loginForm"));
        add(new RegistrationForm("registrationForm"));
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

        Link discussionPageLink = new Link("discussionPage") {
            @Override
            public void onClick() {
                setResponsePage(DiscussionPage.class);
            }
        };
        add(discussionPageLink);
    }

    public abstract String getTitle();
}
