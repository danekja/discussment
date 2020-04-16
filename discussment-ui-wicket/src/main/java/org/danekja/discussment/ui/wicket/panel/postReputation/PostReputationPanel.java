package org.danekja.discussment.ui.wicket.panel.postReputation;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.PostReputation;
import org.danekja.discussment.core.domain.UserPostReputation;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.form.PostReputationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class creates the panel which contains likes and dislike for a post and calls a form for making a new vote.
 *
 * Date: 19.2.18
 *
 * @author Jiri Kryda
 */
public class PostReputationPanel extends Panel  {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private IModel<Post> postModel;
    private IModel<PostReputation> postReputationModel;
    private PostService postService;
    private PostReputationService postReputationService;
    private DiscussionUserService userService;
    private IModel<String> likedModel;
    private Label dislikesLabel, likesLabel;
    private PostReputationForm reputationForm;

    /**
     * Constructor for creating the panel which contains the specific article and its discussion.
     *
     * @param id id of the element into which the panel is inserted
     * @param postModel model contains the post to get votes for
     * @param postService instance of the post service
     * @param postReputationService instance of the post reputation service
     * @param userService instance of the user service
     */
    public PostReputationPanel(String id,
                               IModel<Post> postModel,
                               PostService postService,
                               PostReputationService postReputationService,
                               DiscussionUserService userService){
        super(id);
        this.postModel = postModel;
        this.postReputationModel = new Model<>();

        this.postService = postService;
        this.postReputationService = postReputationService;
        this.userService = userService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);

        this.likedModel = getLikedModel();

        postReputationModel.setObject(postModel.getObject().getPostReputation());

        dislikesLabel = new Label("dislikes", new PropertyModel(postReputationModel, "dislikes"));
        dislikesLabel.setOutputMarkupId(true);
        add(dislikesLabel);

        likesLabel = new Label("likes", new PropertyModel(postReputationModel, "likes"));
        likesLabel.setOutputMarkupId(true);
        add(likesLabel);

        reputationForm = new PostReputationForm("prform", this, postModel, userService);
        reputationForm.setOutputMarkupId(true);
        add(reputationForm);

        add(new Label("liked", likedModel) {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                if (userService.isGuest()) {
                    setVisible(false);
                } else {
                    final String currentUserId = userService.getCurrentlyLoggedUser().getDiscussionUserId();
                    setVisible(postModel.getObject().getUserPostReputations().stream().anyMatch(pr -> currentUserId.equals(pr.getUserId())));
                }
            }
        });
    }

    @Override
    protected void onConfigure(){
        super.onConfigure();

        if(postModel.getObject().isDisabled()) {
            this.setVisible(false);
        }

    }

    /**
     * Handler for like button.
     *
     * @param post
     * @param target
     */
    public void likePost(IModel<Post> post, AjaxRequestTarget target) {
        Post p = post.getObject();
        postReputationService.addLike(p);
    }

    /**
     * Handler for dislike button.
     *
     * @param post
     * @param target
     */
    public void dislikePost(IModel<Post> post, AjaxRequestTarget target) {
        Post p = post.getObject();
        postReputationService.addDislike(p);
    }

    /**
     * Handler for changing post vote.
     *
     * @param post
     * @param target
     */
    public void changePostVote(IModel<Post> post, AjaxRequestTarget target) {
        Post p = post.getObject();
        postReputationService.changeVote(userService.getCurrentlyLoggedUser(), p);
    }

    private LoadableDetachableModel<String> getLikedModel() {
        return new LoadableDetachableModel<String>() {
            @Override
            protected String load() {
                final String currentUserId = userService.getCurrentlyLoggedUser().getDiscussionUserId();

                for(UserPostReputation upr : postModel.getObject().getUserPostReputations()) {
                    if (currentUserId.equals(upr.getUserId())) {
                        if (upr.getLiked()) {
                            return getString("postReputation.liked");
                        } else {
                            return getString("postReputation.disliked");
                        }
                    }
                }

                // liked/disliked is not visible if current user didn't react to post
                return "";
            }
        };
    }

}
