package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.PostReputation;

/**
 * The interface contains the service methods for working with the post reputation.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public interface PostReputationService {

    /**
     * Creates a new post reputation.
     *
     * @param entity new post reputation
     * @return new post reputation
     */
    PostReputation createPostReputation (PostReputation entity);

    /**
     * Get a post reputation based on its post.
     *
     * @param post to get post reputation for
     * @return post reputation for post
     */
    PostReputation getPostReputationByPost (Post post);

    /**
     * Adds a like to post reputaion.
     *
     * @param postReputation to add like
     */
    void addLike (PostReputation postReputation);

    /**
     * Adds a dislike to post reputaion.
     *
     * @param postReputation to add dislike
     */
    void addDislike (PostReputation postReputation);

    /**
     * Checks if user already voted in post reputation.
     *
     * @param postReputation to check
     * @return true, if user already voted
     */
    boolean userVotedOn(PostReputation postReputation);

    /**
     * Gets user's vote.
     *
     * @param postReputation to get vote
     * @return user's vote
     */
    boolean userLiked(PostReputation postReputation);

    /**
     * Remove a post reputation
     *
     * @param entity article to remove
     */
    void removePostReputation(PostReputation entity);
}
