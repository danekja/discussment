package org.danekja.discussment.example.page.dashboard;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.accesscontrol.dao.jpa.PermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.domain.Permission;
import org.danekja.discussment.core.accesscontrol.service.PermissionService;
import org.danekja.discussment.core.accesscontrol.service.impl.DefaultPermissionService;
import org.danekja.discussment.example.core.DefaultUserService;
import org.danekja.discussment.example.core.User;
import org.danekja.discussment.example.core.UserDaoMock;
import org.danekja.discussment.example.core.UserService;
import org.danekja.discussment.example.page.base.BasePage;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class DashboardPage extends BasePage {

    public DashboardPage(final PageParameters parameters) {

        final UserService userService = new DefaultUserService(new UserDaoMock(), new PermissionDaoJPA());

        final PermissionService permissionService = new DefaultPermissionService(new PermissionDaoJPA(), userService);

        add(new ListView<User>("usersListView", userService.getUsers()) {

            public void populateItem(final ListItem<User> item) {

                final User user = item.getModelObject();

                item.add(new Label("username", user.getUsername()));

                Permission p = permissionService.getUsersPermissions(user);
                item.add(new Label("cc", p.isCreateCategory()));
                item.add(new Label("rc", p.isRemoveCategory()));

                item.add(new Label("ct", p.isCreateTopic()));
                item.add(new Label("rt", p.isRemoveTopic()));

                item.add(new Label("cd", p.isCreateCategory()));
                item.add(new Label("rd", p.isRemoveCategory()));

                item.add(new Label("cp", p.isCreatePost()));
                item.add(new Label("rp", p.isRemovePost()));
                item.add(new Label("dp", p.isDisablePost()));

                item.add(new Label("rpd", p.isReadPrivateDiscussion()));

            }
        });


    }

    @Override
    public String getTitle() {
        return "Dashboard page";
    }
}
