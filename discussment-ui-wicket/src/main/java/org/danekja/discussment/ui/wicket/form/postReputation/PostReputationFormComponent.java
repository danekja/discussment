package org.danekja.discussment.ui.wicket.form.postReputation;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostReputationService;

/**
 * The class contains input fields for making a new vote on post.
 *
 * Date: 18.2.18
 *
 * @author Jiri Kryda
 */
public class PostReputationFormComponent extends Panel {

    private IModel<Post> postModel;

    private PostReputationService postReputationService;
    private DiscussionUserService userService;

    /**
     * Constructor for creating a instance of getting a name and text of the article.
     *
     * @param id id of the element into which the panel is inserted
     * @param postModel model contains the post to add a vote
     * @param userService instance of the user service
     * @param postReputationService instance of the post reputation service
     */
    public PostReputationFormComponent (String id, IModel<Post> postModel, DiscussionUserService userService, PostReputationService postReputationService) {
        super(id);
        this.postModel = postModel;

        this.postReputationService = postReputationService;
        this.userService = userService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        AjaxButton addLike = new AjaxButton("addLike") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form prform)
            {
                postReputationService.addLike(postModel.getObject());
                setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
            }

            @Override
            protected void onConfigure(){
                this.setVisible(!userService.isGuest() && !postReputationService.userVotedOn(userService.getCurrentlyLoggedUser(), postModel.getObject()));
            }
        };

        AjaxButton addDislike = new AjaxButton("addDislike") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form prform)
            {
                postReputationService.addDislike(postModel.getObject());
                setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
            }

            @Override
            protected void onConfigure(){
                this.setVisible(!userService.isGuest() && !postReputationService.userVotedOn(userService.getCurrentlyLoggedUser(), postModel.getObject()));
            }
        };

        AjaxButton changeVote = new AjaxButton("changeVote") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form prform)
            {
                postReputationService.changeVote(userService.getCurrentlyLoggedUser(), postModel.getObject());
                setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
            }

            @Override
            protected void onConfigure(){
                this.setVisible(!userService.isGuest() && postReputationService.userVotedOn(userService.getCurrentlyLoggedUser(), postModel.getObject()));
            }
        };

        add(addLike);
        add(addDislike);
        add(changeVote);
    }

}
