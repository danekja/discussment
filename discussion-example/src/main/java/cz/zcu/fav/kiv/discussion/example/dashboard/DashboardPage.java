package cz.zcu.fav.kiv.discussion.example.dashboard;

import cz.zcu.fav.kiv.discussion.core.model.UserModel;
import cz.zcu.fav.kiv.discussion.core.service.UserService;
import cz.zcu.fav.kiv.discussion.example.base.BasePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class DashboardPage extends BasePage {

    public DashboardPage() {

        add(new ListView<UserModel>("usersListView", UserService.getUsers()) {

            public void populateItem(final ListItem<UserModel> item) {

                final UserModel user = item.getModelObject();

                item.add(new Label("username", user.getUsername()));
                item.add(new Label("name", user.getName()));
                item.add(new Label("lastname", user.getLastname()));

                item.add(new Label("cc", user.getPermission().isCreateCategory()));
                item.add(new Label("rc", user.getPermission().isRemoveCategory()));

                item.add(new Label("ct", user.getPermission().isCreateTopic()));
                item.add(new Label("rt", user.getPermission().isRemoveTopic()));

                item.add(new Label("cd", user.getPermission().isCreateCategory()));
                item.add(new Label("rd", user.getPermission().isRemoveCategory()));

                item.add(new Label("cp", user.getPermission().isCreatePost()));
                item.add(new Label("rp", user.getPermission().isRemovePost()));
                item.add(new Label("dp", user.getPermission().isDisablePost()));

                item.add(new Label("rpd", user.getPermission().isReadPrivateDiscussion()));

            }
        });


    }

    @Override
    public String getTitle() {
        return "Dashboard page";
    }
}
