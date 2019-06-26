package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.dao.UserPostReputationDao;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.UserPostReputation;
import org.danekja.discussment.core.service.PostReputationService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the PostReputationService interface.
 *
 * Date: 17.2.18
 *
 * @author Jiri Kryda
 */
@Transactional
public class DefaultPostReputationService implements PostReputationService {

    private static final Object monitor = new Object();

    private UserPostReputationDao userPostReputationDao;
    private PostDao postDao;

    private DiscussionUserService userService;

    public DefaultPostReputationService(UserPostReputationDao userPostReputationDao,
                                        PostDao postDao,
                                        DiscussionUserService userService){

        this.userPostReputationDao = userPostReputationDao;
        this.postDao = postDao;

        this.userService = userService;
    }

    public void addLike(Post post){
        synchronized(monitor){
            IDiscussionUser user = userService.getCurrentlyLoggedUser();
            UserPostReputation upr = getVote(user, post);
            if (upr == null) {
                post.getPostReputation().addLike();
                userPostReputationDao.save(new UserPostReputation(user.getDiscussionUserId(), post, true));
                postDao.save(post);
            } else if (upr.getLiked()){
                changeVote(user, post);
            }
        }
    }

    public void addDislike(Post post){
        synchronized(monitor) {
            IDiscussionUser user = userService.getCurrentlyLoggedUser();
            UserPostReputation upr = getVote(user, post);
            if (upr == null) {
                post.getPostReputation().addDislike();
                userPostReputationDao.save(new UserPostReputation(user.getDiscussionUserId(), post, false));
                postDao.save(post);
            } else if(!upr.getLiked()){
                changeVote(user, post);
            }
        }
    }

    public void changeVote(IDiscussionUser user, Post post){
        synchronized (monitor) {
            if (userVotedOn(user, post)) {
                UserPostReputation upr = getVote(user, post);
                if (upr.getLiked()) {
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
        if (user == null) {
            return null;
        } else {
            return userPostReputationDao.getForUser(user, post);
        }
    }

    public boolean userVotedOn(IDiscussionUser user, Post post){
        return getVote(user, post) != null;
    }
}
