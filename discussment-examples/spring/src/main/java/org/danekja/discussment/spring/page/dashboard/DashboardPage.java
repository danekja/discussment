package org.danekja.discussment.spring.page.dashboard;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlManagerService;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.spring.core.domain.User;
import org.danekja.discussment.spring.core.service.UserService;
import org.danekja.discussment.spring.page.base.BasePage;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class DashboardPage extends BasePage {

    @SpringBean
    private UserService userService;

    @SpringBean(name = "accessControlService")
    private AccessControlService accessControlService;

    @SpringBean(name = "accessControlManagerService")
    private AccessControlManagerService accessControlManagerService;

    @SpringBean
    private CategoryService categoryService;

    @SpringBean
    private TopicService topicService;

    @SpringBean
    private DiscussionService discussionService;

    public DashboardPage(PageParameters parameters) {

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
