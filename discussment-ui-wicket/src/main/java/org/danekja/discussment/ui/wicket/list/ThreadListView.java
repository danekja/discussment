package org.danekja.discussment.ui.wicket.list;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.Post;

import java.util.List;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class ThreadListView extends ListView<Post> {

    public ThreadListView(String id, IModel<? extends List<Post>> model) {
        super(id, model);
    }

    protected void populateItem(ListItem<Post> listItem) {}
}
