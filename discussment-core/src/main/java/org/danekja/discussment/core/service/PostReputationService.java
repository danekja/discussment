package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.UserPostReputation;

/**
 * The interface contains the service methods for working with the post reputation.
 *
 * Date: 19.2.18
 *
 * @author Jiri Kryda
 */
public interface PostReputationService {

    /**
     * Adds a like to post reputaion.
     *
     * @param post post to add like
     */
    void addLike (Post post);

    /**
     * Adds a dislike to post reputaion.
     *
     * @param post post to add dislike
     */
    void addDislike (Post post);

    /**
     * Checks if user already voted in post reputation.
     *
     * @param user user for which the check is made
     * @param post post to check
     * @return true, if user already voted
     */
    boolean userVotedOn(IDiscussionUser user, Post post);

    /**
     * Gets user's vote on the post
     *
     * @param user user to get the vote for
     * @param post post to get vote
     * @return UserPostReputation
     */
    UserPostReputation getVote(IDiscussionUser user, Post post);

    /**
     * Checks if user has voted on the post before.
     * If he did it then changes his vote.
     *
     * @param user user to change the vote for
     * @param post post to change vote
     */
    void changeVote(IDiscussionUser user, Post post);
}
