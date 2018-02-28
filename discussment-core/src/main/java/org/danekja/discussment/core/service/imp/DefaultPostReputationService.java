package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.dao.UserPostReputationDao;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.PostReputation;
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

    private static final Object monitor = new Object();

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

    public void addLike(Post post){
        synchronized(monitor){
            IDiscussionUser user = userService.getCurrentlyLoggedUser();
            if (!userVotedOn(user, post)) {
                post.getPostReputation().addLike();
                userPostReputationDao.save(new UserPostReputation(user.getDiscussionUserId(), post, true));
                postDao.save(post);
            } else if (!userLiked(user, post)){
                changeVote(user, post);
            }
        }
    }

    public void addDislike(Post post){
        synchronized(monitor) {
            IDiscussionUser user = userService.getCurrentlyLoggedUser();
            if (!userVotedOn(user, post)) {
                post.getPostReputation().addDislike();
                userPostReputationDao.save(new UserPostReputation(user.getDiscussionUserId(), post, false));
                postDao.save(post);
            } else if(userLiked(user, post)){
                changeVote(user, post);
            }
        }
    }

    public void changeVote(IDiscussionUser user, Post post){
        synchronized (monitor) {
            UserPostReputation upr = getVote(user, post);
            if (upr != null) {
                if (userLiked(user, post)) {
                    post.getPostReputation().removeLike();
                    post.getPostReputation().addDislike();
                    upr.changeLiked();
                } else {
                    post.getPostReputation().addLike();
                    post.getPostReputation().removeDislike();
                    upr.changeLiked();
                }
                userPostReputationDao.save(upr);
                postDao.save(post);
            }
        }
    }

    public UserPostReputation getVote(IDiscussionUser user, Post post){
        return userPostReputationDao.getForUser(user, post);
    }

    public boolean userVotedOn(IDiscussionUser user, Post post){
        if(getVote(user, post) != null){
            return true;
        } else {
            return false;
        }
    }

    public boolean userLiked(IDiscussionUser user, Post post){
        return getVote(user, post).getLiked();
    }
}
