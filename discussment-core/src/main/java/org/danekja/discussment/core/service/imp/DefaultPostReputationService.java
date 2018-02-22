package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.PostReputationDao;
import org.danekja.discussment.core.dao.UserPostReputationDao;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.PostReputation;
import org.danekja.discussment.core.domain.UserPostReputation;
import org.danekja.discussment.core.service.PostReputationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the PostReputationService interface.
 *
 * Date: 17.2.18
 *
 * @author Jiri Kryda
 */
public class DefaultPostReputationService implements PostReputationService {

    private PostReputationDao postReputationDao;
    private UserPostReputationDao userPostReputationDao;

    private AccessControlService accessControlService;
    private DiscussionUserService userService;

    public DefaultPostReputationService(PostReputationDao postReputationDao,
                                        UserPostReputationDao userPostReputationDao,
                                        DiscussionUserService userService,
                                        AccessControlService accessControlService){

        this.postReputationDao = postReputationDao;
        this.userPostReputationDao = userPostReputationDao;

        this.userService = userService;
        this.accessControlService = accessControlService;
    }

    public PostReputation createPostReputation (PostReputation entity){
        return postReputationDao.save(entity);
    }

    public PostReputation getPostReputationByPost (Post post){
        return postReputationDao.getByPost(post);
    }

    public void addLike(PostReputation postReputation){
        postReputation.setLikes((postReputation.getLikes())+1);
        userPostReputationDao.save(new UserPostReputation(userService.getCurrentlyLoggedUser().getDiscussionUserId(), postReputation, true));
    }

    public void addDislike(PostReputation postReputation){
        postReputation.setDislikes((postReputation.getDislikes())+1);
        userPostReputationDao.save(new UserPostReputation(userService.getCurrentlyLoggedUser().getDiscussionUserId(), postReputation, false));
    }

    public boolean userVotedOn(PostReputation postReputation){
        if(userPostReputationDao.getForUser(userService.getCurrentlyLoggedUser(), postReputation) != null){
            return true;
        } else return false;
    }

    public boolean userLiked(PostReputation postReputation){
        return userPostReputationDao.getForUser(userService.getCurrentlyLoggedUser(), postReputation).getLiked();
    }

    public void removePostReputation(PostReputation entity){
        postReputationDao.remove(entity);
    }
}
