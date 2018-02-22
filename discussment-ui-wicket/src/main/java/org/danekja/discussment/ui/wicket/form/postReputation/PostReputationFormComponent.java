package org.danekja.discussment.ui.wicket.form.postReputation;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.PostReputation;
import org.danekja.discussment.core.service.PostReputationService;

import java.util.Arrays;
import java.util.List;

/**
 * The class contains input fields for making a new vote on post.
 *
 * Date: 18.2.18
 *
 * @author Jiri Kryda
 */
public class PostReputationFormComponent extends Panel {

    private IModel<PostReputation> postReputationModel;
    private PostReputationService postReputationService;
    /**
     * Constructor for creating a instance of getting a name and text of the article.
     *
     * @param id id of the element into which the panel is inserted
     */
    public PostReputationFormComponent (String id, IModel<PostReputation> postReputationModel, PostReputationService postReputationService) {
        super(id);
        this.postReputationModel = postReputationModel;
        this.postReputationService = postReputationService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        AjaxButton addLike = new AjaxButton("addLike") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form prform)
            {
                postReputationService.addLike(postReputationModel.getObject());
                setResponsePage(getPage());
            }
        };

        AjaxButton addDislike = new AjaxButton("addDislike") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form prform)
            {
                postReputationService.addDislike(postReputationModel.getObject());
                setResponsePage(getPage());
            }
        };
        add(addLike);
        add(addDislike);
    }

}
