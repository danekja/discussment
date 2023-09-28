package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.PermissionData;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.ui.wicket.form.topic.TopicFormComponent;

/**
 * Created by Martin Bláha on 25.01.17.
 *
 * The class creates the form for creating a new topic
 */
public class TopicForm extends Form {

    private TopicService topicService;
    private final DiscussionUserService userService;
    private final AccessControlService accessControlService;
    private final PermissionManagementService permissionService;

    private final IModel<Topic> topicModel;
    private final IModel<Category> categoryModel;

    /**
     * Constructor for creating a instance of the form for creating a new topic
     *
     * @param id id of the element into which the panel is inserted
     * @param topicService instance of the topic service
     * @param categoryModel model contains the category for adding a new topic
     * @param topicModel model contains the topic for setting the form
     */
    public TopicForm(String id,
                     TopicService topicService,
                     DiscussionUserService userService,
                     AccessControlService accessControlService,
                     PermissionManagementService permissionService,
                     IModel<Category> categoryModel,
                     IModel<Topic> topicModel) {
        super(id);

        this.topicService = topicService;
        this.userService = userService;
        this.accessControlService = accessControlService;
        this.permissionService = permissionService;

        this.categoryModel = categoryModel;
        this.topicModel = topicModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new TopicFormComponent("topicFormComponent", topicModel));
    }

    public void setTopicService(TopicService topicService) {
        this.topicService = topicService;
    }


    @Override
    protected void onSubmit() {
        try {
            Topic topic = topicService.createTopic(categoryModel.getObject(), topicModel.getObject());

            if (!accessControlService.canAddDiscussion(topic)) {
                permissionService.configureDiscussionPermissions(userService.getCurrentlyLoggedUser(), topic, new PermissionData(true, false, false, true));
            }

        } catch (AccessDeniedException e) {
            // todo: not yet impemented
        }

        topicModel.setObject(new Topic());
        setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
    }
}
