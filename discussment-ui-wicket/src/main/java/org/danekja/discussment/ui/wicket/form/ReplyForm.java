package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.PostReputation;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.form.post.PostFormComponent;
import org.danekja.discussment.ui.wicket.session.SessionUtil;

/**
 * Created by Martin Bláha on 21.01.17.
 *
 * The class creates the form for creating a new reply of the post
 */
public class ReplyForm extends Form {

    private PostService postService;
    private PostReputationService postReputationService;

    private IModel<Post> postModel;
    private IModel<Post> replyModel;

    /**
     * Constructor for creating a instance of the form for creating a new reply of the post
     *
     * @param id id of the element into which the panel is inserted
     * @param postModel model contains the post for adding a new post
     * @param replyModel model contains the reply for setting a form
     * @param postReputationService instance of the post reputation service
     */
    public ReplyForm(String id, IModel<Post> postModel, IModel<Post> replyModel, PostReputationService postReputationService) {
        this(id, postModel, replyModel, postReputationService, null);
    }

    /**
     * Constructor for creating a instance of the form for creating a new reply of the post
     *
     * @param id id of the element into which the panel is inserted
     * @param postService instance of the post service
     * @param postModel model contains the post for adding a new post
     * @param replyModel model contains the reply for setting the form
     * @param postReputationService instance of the post reputation service
     */
    public ReplyForm(String id, IModel<Post> postModel, IModel<Post> replyModel, PostReputationService postReputationService, PostService postService) {
        super(id);

        this.postService = postService;
        this.postReputationService = postReputationService;

        this.postModel = postModel;
        this.replyModel = replyModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new PostFormComponent("replyFormComponent", replyModel));
    }

    @Override
    protected void onSubmit() {

        if (postService != null) {
            try {
                replyModel.getObject().setUserId(SessionUtil.getUser().getDiscussionUserId());
                postService.sendReply(replyModel.getObject(), postModel.getObject());
            } catch (AccessDeniedException e) {
                //todo: not yet implemented
            }
            replyModel.setObject(new Post());
        }
        setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
    }
}
