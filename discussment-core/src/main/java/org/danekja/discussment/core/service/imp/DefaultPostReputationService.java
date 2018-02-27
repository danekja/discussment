package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.dao.UserPostReputationDao;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.UserPostReputation;
import org.danekja.discussment.core.service.PostReputationService;

/**
 * Implementation of the PostReputationService interface.
 *
 * Date: 17.2.18
 *
 * @author Jiri Kryda
 */
public class DefaultPostReputationService implements PostReputationService {

    private UserPostReputationDao userPostReputationDao;
    private PostDao postDao;

    private AccessControlService accessControlService;
    private DiscussionUserService userService;

    public DefaultPostReputationService(UserPostReputationDao userPostReputationDao,
                                        PostDao postDao,
                                        DiscussionUserService userService,
                                        AccessControlService accessControlService){

        this.userPostReputationDao = userPostReputationDao;
        this.postDao = postDao;

        this.userService = userService;
        this.accessControlService = accessControlService;
    }

    public synchronized void addLike(Post post){
        // todo: only one service must exist in the app
        IDiscussionUser user = userService.getCurrentlyLoggedUser();
        post.getPostReputation().addLike();
        if (!userVotedOn(user, post)) {
            userPostReputationDao.save(new UserPostReputation(user.getDiscussionUserId(), post, true));
        }
        postDao.save(post);
    }

    public synchronized void addDislike(Post post){
        // todo: only one service must exist in the app
        IDiscussionUser user = userService.getCurrentlyLoggedUser();
        post.getPostReputation().addDislike();
        if (!userVotedOn(user, post)) {
            userPostReputationDao.save(new UserPostReputation(user.getDiscussionUserId(), post, false));
        }
        postDao.save(post);
    }

    public synchronized void changeVote(Post post){
        IDiscussionUser user = userService.getCurrentlyLoggedUser();
        UserPostReputation upr = getVote(user, post);
        if(upr != null){
            if(userLiked(user, post)){
                post.getPostReputation().removeLike();
                post.getPostReputation().addDislike();
                upr.changeVote();
            } else {
                post.getPostReputation().addLike();
                post.getPostReputation().removeDislike();
                upr.changeVote();
            }
            userPostReputationDao.save(upr);
            postDao.save(post);
        }
    }

    public UserPostReputation getVote(IDiscussionUser user, Post post){
        return userPostReputationDao.getForUser(user, post);
    }

    public boolean userVotedOn(IDiscussionUser user, Post post){
        if(getVote(user, post) != null){
            return true;
        } else return false;
    }

    public boolean userLiked(IDiscussionUser user, Post post){
        return getVote(user, post).getLiked();
    }
}
