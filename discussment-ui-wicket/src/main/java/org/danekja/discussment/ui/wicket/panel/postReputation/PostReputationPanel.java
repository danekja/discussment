package org.danekja.discussment.ui.wicket.panel.postReputation;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
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
                try {
                    if (userService.isGuest()) {
                        setVisible(false);
                    } else {
                        final String currentUserId = userService.getCurrentlyLoggedUser().getDiscussionUserId();
                        setVisible(postModel.getObject().getUserPostReputations().stream().anyMatch(pr -> currentUserId.equals(pr.getUserId())));
                    }
                }catch (Exception ex) {
                    logger.error("Exception occurred while configuring the 'liked' label: ", ex);
                    setVisible(false);
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
     */
    public void likePost(IModel<Post> post) {
        Post p = post.getObject();
        try {
            postReputationService.addLike(p);
        } catch (Exception ex) {
            Long postId = p == null ? null : p.getId();
            logger.error("Exception occurred while liking post {}:  {}", postId, ex);
        }
    }

    /**
     * Handler for dislike button.
     *
     * @param post
     */
    public void dislikePost(IModel<Post> post) {
        Post p = post.getObject();
        try {
            postReputationService.addDislike(p);
        } catch (Exception ex) {
            Long postId = p == null ? null : p.getId();
            logger.error("Exception occurred while disliking post {}:  {}", postId, ex);
        }
    }

    /**
     * Handler for changing post vote.
     *
     * @param post
     */
    public void changePostVote(IModel<Post> post) {
        Post p = post.getObject();
        try {
            postReputationService.changeVote(userService.getCurrentlyLoggedUser(), p);
        } catch (Exception ex) {
            Long postId = p == null ? null : p.getId();
            logger.error("Exception occurred while changing vote for post {}:  {}", postId, ex);
        }
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
