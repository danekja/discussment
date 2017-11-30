package org.danekja.discussment.example.page.dashboard;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.accesscontrol.dao.jpa.PermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
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

        final AccessControlService accessControlService = new PermissionService(new PermissionDaoJPA(), userService);

        add(new ListView<User>("usersListView", userService.getUsers()) {

            public void populateItem(final ListItem<User> item) {

                final User user = item.getModelObject();

                item.add(new Label("username", user.getDisplayName()));

                // todo: replacement
                item.add(new Label("cc", Model.of(false)));
                item.add(new Label("rc", Model.of(false)));

                item.add(new Label("ct", Model.of(false)));
                item.add(new Label("rt", Model.of(false)));

                item.add(new Label("cd", Model.of(false)));
                item.add(new Label("rd", Model.of(false)));

                item.add(new Label("cp", Model.of(false)));
                item.add(new Label("rp", Model.of(false)));
                item.add(new Label("dp", Model.of(false)));

                item.add(new Label("rpd", Model.of(false)));

            }
        });


    }

    @Override
    public String getTitle() {
        return "Dashboard page";
    }
}
