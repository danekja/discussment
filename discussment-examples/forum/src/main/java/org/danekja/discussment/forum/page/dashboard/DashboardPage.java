package org.danekja.discussment.forum.page.dashboard;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.accesscontrol.dao.jpa.PermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.service.AccessControlManagerService;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
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
import org.danekja.discussment.forum.WicketApplication;
import org.danekja.discussment.forum.core.dao.jpa.UserDaoJPA;
import org.danekja.discussment.forum.core.domain.User;
import org.danekja.discussment.forum.core.service.UserService;
import org.danekja.discussment.forum.core.service.imp.DefaultUserService;
import org.danekja.discussment.forum.page.base.BasePage;

import javax.persistence.EntityManager;


/**
 * Created by Martin Bláha on 21.01.17.
 */
public class DashboardPage extends BasePage {

    private final EntityManager em;

    public DashboardPage(final PageParameters parameters) {

        em = WicketApplication.factory.createEntityManager();

        final UserService userService = new DefaultUserService(new UserDaoJPA(em));
        final AccessControlService accessControlService = new PermissionService(new PermissionDaoJPA(em), userService);
        final AccessControlManagerService accessControlManagerService = new PermissionService(new PermissionDaoJPA(em), userService);
        final CategoryService categoryService = new DefaultCategoryService(new CategoryDaoJPA(em), accessControlService, userService);
        final TopicService topicService = new DefaultTopicService(new TopicDaoJPA(em), categoryService, accessControlService, userService);
        final DiscussionService discussionService = new DefaultDiscussionService(new DiscussionDaoJPA(em), new PostDaoJPA(em), topicService, accessControlService, userService);



        add(new ListView<User>("usersListView", userService.getUsers()) {

            public void populateItem(final ListItem<User> item) {

                final User user = item.getModelObject();
                item.add(new Label("username", user.getDisplayName()));

                try {
                    item.add(new Label("cc", accessControlManagerService.canAddCategory(user)));
                } catch (NullPointerException e){
                    item.add(new Label("cc", "Error: null"));
                }

                try {
                    item.add(new Label("rc", accessControlManagerService.canRemoveCategory(user, categoryService.getDefaultCategory())));
                } catch (NullPointerException ex) {
                    item.add(new Label("rc", "Error: null exception"));
                }

                try {
                    item.add(new Label("ec", accessControlManagerService.canEditCategory(user, categoryService.getDefaultCategory())));
                } catch (NullPointerException ex) {
                    item.add(new Label("ec", "Error: null exception"));
                }

                try {
                    item.add(new Label("vc", accessControlManagerService.canViewCategories(user)));
                } catch (NullPointerException e){
                    item.add(new Label("vc", "Error: null"));
                }

                try {
                    item.add(new Label("ct", accessControlManagerService.canAddTopic(user, categoryService.getDefaultCategory())));
                } catch (NullPointerException ex) {
                    item.add(new Label("ct", "Error: null exception"));
                }

                try {
                    item.add(new Label("rt", accessControlManagerService.canRemoveTopic(user, topicService.getDefaultTopic())));
                } catch (NullPointerException ex) {
                    item.add(new Label("rt", "Error: null exception"));
                }

                try {
                    item.add(new Label("et", accessControlManagerService.canEditTopic(user, topicService.getDefaultTopic())));
                } catch (NullPointerException ex) {
                    item.add(new Label("et", "Error: null exception"));
                }

                try {
                    item.add(new Label("vt", accessControlManagerService.canViewTopics(user, categoryService.getDefaultCategory())));
                } catch (NullPointerException ex) {
                    item.add(new Label("vt", "Error: null exception"));
                }

                try {
                    item.add(new Label("cd", accessControlManagerService.canAddDiscussion(user, topicService.getDefaultTopic())));
                } catch (NullPointerException ex) {
                    item.add(new Label("cd", "Error: null exception"));
                }

                try {
                    item.add(new Label("rd", accessControlManagerService.canRemoveDiscussion(user, discussionService.getDefaultDiscussion())));
                } catch (NullPointerException ex) {
                    item.add(new Label("rd", "Error: null exception"));
                }

                try {
                    item.add(new Label("ed", accessControlManagerService.canEditDiscussion(user, discussionService.getDefaultDiscussion())));
                } catch (NullPointerException ex) {
                    item.add(new Label("ed", "Error: null exception"));
                }

                try {
                    item.add(new Label("vd", accessControlManagerService.canViewDiscussions(user, topicService.getDefaultTopic())));
                } catch (NullPointerException ex) {
                    item.add(new Label("vd", "Error: null exception"));
                }

                try {
                    item.add(new Label("cp", accessControlManagerService.canAddPost(user, discussionService.getDefaultDiscussion())));
                } catch (NullPointerException ex) {
                    item.add(new Label("cp", "Error: null exception"));
                }

                try {
                    item.add(new Label("rp", accessControlManagerService.canRemovePosts(user, discussionService.getDefaultDiscussion())));
                } catch (NullPointerException ex) {
                    item.add(new Label("rp", "Error: null exception"));
                }

                try {
                    item.add(new Label("ep", accessControlManagerService.canEditPosts(user, discussionService.getDefaultDiscussion())));
                } catch (NullPointerException ex) {
                    item.add(new Label("ep", "Error: null exception"));
                }
                try {
                    item.add(new Label("vp", accessControlManagerService.canViewPosts(user, discussionService.getDefaultDiscussion())));
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
