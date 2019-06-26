package org.danekja.discussment.ui.wicket.list.category;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.ui.wicket.list.topic.TopicListPanel;
import org.danekja.discussment.ui.wicket.model.TopicWicketModel;

import java.util.List;

/**
 * Created by Martin Bláha on 04.02.17.
 *
 * The class creates the panel contains categories with the topics
 */
public class CategoryListPanel extends Panel {

    private static int generateId = 0;

    private CategoryService categoryService;
    private TopicService topicService;
    private DiscussionService discussionService;
    private PostService postService;
    private IModel<Category> categoryModel;
    private IModel<List<Category>> categoryListModel;
    private AccessControlService accessControlService;

    /**
     * Constructor for creating a instance of the panel contains categories with the topics
     *
     * @param id id of the element into which the panel is inserted
     * @param categoryListModel model for getting the categories
     * @param categoryModel model for setting the selected category
     * @param categoryService instance of the category service
     * @param topicService instance of the topic service
     */
    public CategoryListPanel(String id,
                             IModel<List<Category>> categoryListModel,
                             IModel<Category> categoryModel,
                             CategoryService categoryService,
                             TopicService topicService,
                             DiscussionService discussionService,
                             PostService postService,
                             AccessControlService accessControlService) {
        super(id);

        this.topicService = topicService;
        this.discussionService = discussionService;
        this.categoryListModel = categoryListModel;
        this.categoryService = categoryService;
        this.categoryModel = categoryModel;
        this.accessControlService = accessControlService;
        this.postService = postService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ListView<Category>("categoryList", categoryListModel) {
            protected void populateItem(ListItem<Category> listItem) {

                listItem.add(createCategoryHeader(listItem.getModel()));
                listItem.add(createTopicListViewPanel(listItem.getModel()));

                generateId++;
            }
        });
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        this.setVisible(!categoryListModel.getObject().isEmpty());
    }

    private TopicListPanel createTopicListViewPanel(IModel<Category> cm) {
        TopicListPanel topicListViewPanel = new TopicListPanel("topicListPanel", new TopicWicketModel(cm, topicService), topicService, discussionService, postService, accessControlService);
        topicListViewPanel.setOutputMarkupId(true);
        topicListViewPanel.setMarkupId("id" + generateId);

        return topicListViewPanel;
    }

    private WebMarkupContainer createCategoryHeader(IModel<Category> cm) {
        WebMarkupContainer categoryHeader = new WebMarkupContainer("categoryHeader");

        WebMarkupContainer categoryIcon = new WebMarkupContainer("categoryIcon");
        categoryIcon.add(new AttributeAppender("data-target", "id" + generateId));

        categoryHeader.add(categoryIcon);
        categoryHeader.add(new Label("categoryName", new PropertyModel<String>(cm, "name")));
        categoryHeader.add(createNewTopicAjaxLink(cm));
        categoryHeader.add(createRemoveCategoryLink(cm));

        return categoryHeader;
    }

    private AjaxLink createNewTopicAjaxLink(final IModel<Category> cm) {
        return new AjaxLink("newTopic") {
            @Override
            protected void onConfigure() {
                super.onConfigure();

                this.setVisible(accessControlService.canAddTopic(cm.getObject()));
            }

            @Override
            public void onClick(AjaxRequestTarget target) {
                categoryModel.setObject(cm.getObject());

                target.appendJavaScript("$('#topicModal').modal('show');");
            }
        };
    }

    private Link createRemoveCategoryLink(final IModel<Category> cm) {
        return new Link("remove") {
            @Override
            protected void onConfigure() {
                super.onConfigure();

                this.setVisible(accessControlService.canRemoveCategory(cm.getObject()));
            }

            @Override
            public void onClick() {
                try{
                    categoryService.removeCategory(cm.getObject());
                } catch (AccessDeniedException e) {
                    // todo: not yet implemented
                }
                setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
            }
        };
    }

}
