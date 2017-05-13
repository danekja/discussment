package cz.zcu.fav.kiv.discussion.gui.list;

import cz.zcu.fav.kiv.discussion.core.entity.PostEntity;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class ThreadListView extends ListView<PostEntity> {

    public ThreadListView(String id, IModel<? extends List<PostEntity>> model) {
        super(id, model);
    }

    protected void populateItem(ListItem<PostEntity> listItem) {}
}
