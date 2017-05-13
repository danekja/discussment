package cz.zcu.fav.kiv.discussion.gui.list.panel.content;

import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;
import cz.zcu.fav.kiv.discussion.gui.form.panel.category.CategoryModalFormPanel;
import cz.zcu.fav.kiv.discussion.gui.form.panel.topic.TopicModalFormPanel;
import cz.zcu.fav.kiv.discussion.gui.list.panel.category.CategoryListViewPanel;
import cz.zcu.fav.kiv.discussion.gui.list.panel.topic.TopicListViewPanel;
import cz.zcu.fav.kiv.discussion.gui.model.CategoryWicketModel;
import cz.zcu.fav.kiv.discussion.gui.model.TopicWicketModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;


/**
 * Created by Martin Bláha on 29.01.17.
 */
public class ContentListViewPanel extends Panel {

    private AjaxLink createCategory;
    private AjaxLink createTopic;

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        UserEntity user = (UserEntity) getSession().getAttribute("user");

        if (user != null && user.getPermissions().isCreateCategory()) {
            createCategory.setVisible(true);
        } else {
            createCategory.setVisible(false);
        }

        if (user != null && user.getPermissions().isCreateTopic()) {
            createTopic.setVisible(true);
        } else {
            createTopic.setVisible(false);
        }

    }

    public ContentListViewPanel(String id, CategoryWicketModel categoryWicketModel, TopicWicketModel topicWicketModel) {
        super(id);

        add(new CategoryModalFormPanel("categoryForm"));

        createCategory = new AjaxLink("createCategory") {
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}
        };
        add(createCategory);


        TopicModalFormPanel topicModalFormPanel = new TopicModalFormPanel("topicForm");
        add(topicModalFormPanel);

        createTopic = new AjaxLink("createTopic") {
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}
        };
        add(createTopic);

        add(new CategoryListViewPanel("categoryPanel", categoryWicketModel, topicModalFormPanel));

        TopicListViewPanel topicListViewPanel = new TopicListViewPanel("withoutTopicListPanel", topicWicketModel);
        add(topicListViewPanel);



        //System.out.println("velikost je: " + topicWicketModel.getObject().size());

        /*if (topicWicketModel.getObject().size() == 0) {
            topicListViewPanel.setVisible(false);
            //topicListViewPanel.add(new AttributeAppender("style", "display: none;"));
        } else {
            topicListViewPanel.setVisible(true);
        }*/


    }




}
