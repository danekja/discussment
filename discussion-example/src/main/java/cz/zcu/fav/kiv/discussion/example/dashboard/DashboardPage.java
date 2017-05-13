package cz.zcu.fav.kiv.discussion.example.dashboard;

import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;
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

        add(new ListView<UserEntity>("usersListView", UserService.getUsers()) {

            public void populateItem(final ListItem<UserEntity> item) {

                final UserEntity user = item.getModelObject();

                item.add(new Label("username", user.getUsername()));
                item.add(new Label("name", user.getName()));
                item.add(new Label("lastname", user.getLastname()));

                item.add(new Label("cc", user.getPermissions().isCreateCategory()));
                item.add(new Label("rc", user.getPermissions().isRemoveCategory()));

                item.add(new Label("ct", user.getPermissions().isCreateTopic()));
                item.add(new Label("rt", user.getPermissions().isRemoveTopic()));

                item.add(new Label("cd", user.getPermissions().isCreateCategory()));
                item.add(new Label("rd", user.getPermissions().isRemoveCategory()));

                item.add(new Label("cp", user.getPermissions().isCreatePost()));
                item.add(new Label("rp", user.getPermissions().isRemovePost()));
                item.add(new Label("dp", user.getPermissions().isDisablePost()));

                item.add(new Label("rpd", user.getPermissions().isReadPrivateDiscussion()));

            }
        });


    }

    @Override
    public String getTitle() {
        return "Dashboard page";
    }
}
