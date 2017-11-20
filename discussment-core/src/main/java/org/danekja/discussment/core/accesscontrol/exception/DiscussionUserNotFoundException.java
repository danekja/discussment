package org.danekja.discussment.core.accesscontrol.exception;

/**
 * An exception thrown whenever user which should exist (has posts or discussions assigned) is not found
 * by DiscussionUserService.
 *
 * Created by Zdenek Vales on 3.8.2017.
 */
public class DiscussionUserNotFoundException extends Exception {

    public final Long userId;

    public DiscussionUserNotFoundException(Long userId) {
        super("Discussion user with id "+userId+" not found!");
        this.userId = userId;
    }

}
