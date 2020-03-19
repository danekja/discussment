package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.exception.MaxReplyLevelExceeded;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.form.post.PostFormComponent;

/**
 * Created by Martin Bláha on 21.01.17.
 *
 * The class creates the form for creating a new reply of the post
 */
public class ReplyForm extends Form {

    private PostService postService;
    private AccessControlService accessControlService;

    private IModel<Discussion> discussionModel;
    private IModel<Post> postModel;
    private IModel<Post> replyModel;

    /**
     * Constructor for creating a instance of the form for creating a new reply of the post
     *
     * @param id id of the element into which the panel is inserted
     * @param discussionModel model contains the discussion for adding a new post
     * @param postModel model contains the post for adding a new post
     * @param replyModel model contains the reply for setting the form
     * @param accessControlService instance of the access control service
     * @param postService instance of the post service
     */
    public ReplyForm(String id,
                     IModel<Discussion> discussionModel,
                     IModel<Post> postModel,
                     IModel<Post> replyModel,
                     PostService postService,
                     AccessControlService accessControlService) {
        super(id);

        this.postService = postService;
        this.accessControlService = accessControlService;

        this.discussionModel = discussionModel;
        this.postModel = postModel;
        this.replyModel = replyModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new PostFormComponent("replyFormComponent", replyModel));
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        this.setVisible(accessControlService.canAddPost(discussionModel.getObject()));
    }

    @Override
    protected void onSubmit() {
        try {
            postService.sendReply(replyModel.getObject(), postModel.getObject());
        } catch (AccessDeniedException e) {
            //todo: not yet implemented
        } catch (MaxReplyLevelExceeded e) {
            this.error(getString("error.maxReplyLevelExceeded"));
        }

        replyModel.setObject(new Post());

        setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
    }
}
