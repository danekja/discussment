package org.danekja.discussment.forum.page.dashboard;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.accesscontrol.dao.jpa.NewPermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.dao.jpa.CategoryDaoJPA;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.dao.jpa.PostDaoJPA;
import org.danekja.discussment.core.dao.jpa.TopicDaoJPA;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.core.service.imp.NewCategoryService;
import org.danekja.discussment.core.service.imp.NewDiscussionService;
import org.danekja.discussment.core.service.imp.NewTopicService;
import org.danekja.discussment.forum.WicketApplication;
import org.danekja.discussment.forum.core.dao.jpa.UserDaoJPA;
import org.danekja.discussment.forum.core.domain.User;
import org.danekja.discussment.forum.core.service.UserService;
import org.danekja.discussment.forum.core.service.imp.DefaultDashboardService;
import org.danekja.discussment.forum.core.service.imp.DefaultUserService;
import org.danekja.discussment.forum.page.base.BasePage;

import javax.persistence.EntityManager;


/**
 * Created by Martin Bláha on 21.01.17.
 */
public class DashboardPage extends BasePage {

    private EntityManager em;

    private static final long CATEGORY_ID = 1;
    private static final long TOPIC_ID = 1;
    private static final long DISCUSSION_ID = 1;

    public DashboardPage(final PageParameters parameters) {

        em = WicketApplication.factory.createEntityManager();

        final UserService userService = new DefaultUserService(new UserDaoJPA(em));
        final AccessControlService accessControlService = new PermissionService(new NewPermissionDaoJPA(em), userService);
        final DefaultDashboardService dashboardService = new DefaultDashboardService(new NewPermissionDaoJPA(em));
        final CategoryService categoryService = new NewCategoryService(new CategoryDaoJPA(em), accessControlService, userService);
        final DiscussionService discussionService = new NewDiscussionService(new DiscussionDaoJPA(em), new PostDaoJPA(em), accessControlService, userService);
        final TopicService topicService = new NewTopicService(new TopicDaoJPA(em), accessControlService, userService);


        add(new ListView<User>("usersListView", userService.getUsers()) {

            public void populateItem(final ListItem<User> item) {

                final User user = item.getModelObject();
                item.add(new Label("username", user.getDisplayName()));

                try {
                    item.add(new Label("cc", dashboardService.canAddCategory(user)));
                } catch (NullPointerException e){
                    item.add(new Label("cc", "Error: null"));
                }

                try {
                    item.add(new Label("rc", dashboardService.canRemoveCategory(user, categoryService.getCategoryById(CATEGORY_ID))));
                } catch (AccessDeniedException e) {
                    item.add(new Label("rc", "Error: access denied"));
                } catch (NullPointerException ex) {
                    item.add(new Label("rc", "Error: null exception"));
                }

                try {
                    item.add(new Label("ec", dashboardService.canEditCategory(user, categoryService.getCategoryById(CATEGORY_ID))));
                } catch (AccessDeniedException e) {
                    item.add(new Label("ec", "Error: access denied"));
                } catch (NullPointerException ex) {
                    item.add(new Label("ec", "Error: null exception"));
                }

                try {
                    item.add(new Label("vc", dashboardService.canViewCategories(user)));
                } catch (NullPointerException e){
                    item.add(new Label("vc", "Error: null"));
                }

                try {
                    item.add(new Label("ct", dashboardService.canAddTopic(user, categoryService.getCategoryById(CATEGORY_ID))));
                } catch (AccessDeniedException e) {
                    item.add(new Label("ct", "Error: access denied"));
                } catch (NullPointerException ex) {
                    item.add(new Label("ct", "Error: null exception"));
                }

                try {
                    item.add(new Label("rt", dashboardService.canRemoveTopic(user, topicService.getTopicById(TOPIC_ID))));
                } catch (AccessDeniedException e) {
                    item.add(new Label("rt", "Error: access denied"));
                } catch (NullPointerException ex) {
                    item.add(new Label("rt", "Error: null exception"));
                }

                try {
                    item.add(new Label("et", dashboardService.canEditTopic(user, topicService.getTopicById(TOPIC_ID))));
                } catch (AccessDeniedException e) {
                    item.add(new Label("et", "Error: access denied"));
                } catch (NullPointerException ex) {
                    item.add(new Label("et", "Error: null exception"));
                }

                try {
                    item.add(new Label("vt", dashboardService.canViewTopics(user, categoryService.getCategoryById(CATEGORY_ID))));
                } catch (AccessDeniedException e) {
                    item.add(new Label("vt", "Error: access denied"));
                } catch (NullPointerException ex) {
                    item.add(new Label("vt", "Error: null exception"));
                }

                try {
                    item.add(new Label("cd", dashboardService.canAddDiscussion(user, topicService.getTopicById(TOPIC_ID))));
                } catch (AccessDeniedException e) {
                    item.add(new Label("cd", "Error: access denied"));
                } catch (NullPointerException ex) {
                    item.add(new Label("cd", "Error: null exception"));
                }

                try {
                    item.add(new Label("rd", dashboardService.canRemoveDiscussion(user, discussionService.getDiscussionById(DISCUSSION_ID))));
                } catch (AccessDeniedException e) {
                    item.add(new Label("rd", "Error: access denied"));
                } catch (NullPointerException ex) {
                    item.add(new Label("rd", "Error: null exception"));
                }

                try {
                    item.add(new Label("ed", dashboardService.canEditDiscussion(user, discussionService.getDiscussionById(DISCUSSION_ID))));
                } catch (AccessDeniedException e) {
                    item.add(new Label("ed", "Error: access denied"));
                } catch (NullPointerException ex) {
                    item.add(new Label("ed", "Error: null exception"));
                }

                try {
                    item.add(new Label("vd", dashboardService.canViewDiscussions(user, topicService.getTopicById(TOPIC_ID))));
                } catch (AccessDeniedException e) {
                    item.add(new Label("vd", "Error: access denied"));
                } catch (NullPointerException ex) {
                    item.add(new Label("vd", "Error: null exception"));
                }

                try {
                    item.add(new Label("cp", dashboardService.canAddPost(user, discussionService.getDiscussionById(DISCUSSION_ID))));
                } catch (AccessDeniedException e) {
                    item.add(new Label("cp", "Error: access denied"));
                } catch (NullPointerException ex) {
                    item.add(new Label("cp", "Error: null exception"));
                }

                try {
                    item.add(new Label("rp", dashboardService.canRemovePost(user, discussionService.getDiscussionById(DISCUSSION_ID))));
                } catch (AccessDeniedException e) {
                    item.add(new Label("rp", "Error: access denied"));
                } catch (NullPointerException ex) {
                    item.add(new Label("rp", "Error: null exception"));
                }

                try {
                    item.add(new Label("ep", dashboardService.canEditPost(user, discussionService.getDiscussionById(DISCUSSION_ID))));
                } catch (AccessDeniedException e) {
                    item.add(new Label("ep", "Error: access denied"));
                } catch (NullPointerException ex) {
                    item.add(new Label("ep", "Error: null exception"));
                }
                try {
                    item.add(new Label("vp", dashboardService.canViewPosts(user, discussionService.getDiscussionById(DISCUSSION_ID))));
                } catch (AccessDeniedException e) {
                    item.add(new Label("vp", "Error: access denied"));
                } catch (NullPointerException ex) {
                    item.add(new Label("vp", "Error: null exception"));
                }
            }
        });


    }

    @Override
    public String getTitle() {
        return "Dashboard page";
    }
}
