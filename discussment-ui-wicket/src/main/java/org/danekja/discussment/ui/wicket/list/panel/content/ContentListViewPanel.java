package org.danekja.discussment.ui.wicket.list.panel.content;

import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.ui.wicket.form.panel.category.CategoryModalFormPanel;
import org.danekja.discussment.ui.wicket.form.panel.topic.TopicModalFormPanel;
import org.danekja.discussment.ui.wicket.list.panel.category.CategoryListViewPanel;
import org.danekja.discussment.ui.wicket.list.panel.topic.TopicListViewPanel;
import org.danekja.discussment.ui.wicket.model.CategoryWicketModel;
import org.danekja.discussment.ui.wicket.model.TopicWicketModel;
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

        User user = (User) getSession().getAttribute("user");

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
