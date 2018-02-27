package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.UserPostReputation;

/**
 * The interface contains the service methods for working with the post reputation.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public interface PostReputationService {

    /**
     * Adds a like to post reputaion.
     *
     * @param post to add like
     */
    void addLike (Post post);

    /**
     * Adds a dislike to post reputaion.
     *
     * @param post to add dislike
     */
    void addDislike (Post post);

    /**
     * Checks if user already voted in post reputation.
     *
     * @param post to check
     * @return true, if user already voted
     */
    boolean userVotedOn(IDiscussionUser user, Post post);

    /**
     * Checks if user liked the post
     *
     * @param user user to get the vote for
     * @param post to get vote
     * @return true, if user liked the post
     */
    boolean userLiked(IDiscussionUser user, Post post);

    /**
     * Gets user's vote on the post
     *
     * @param user user to get the vote for
     * @param post to get vote
     * @return UserPostReputation
     */
    UserPostReputation getVote(IDiscussionUser user, Post post);

    /**
     * Checks if user has voted on the post before.
     * If he did it then changes his vote.
     *
     * @param post to change vote
     */
    void changeVote(Post post);
}
