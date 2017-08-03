package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.IDiscussionUser;

import java.io.Serializable;

/**
 * Library will use this interface to obtain needed user object from main application.
 *
 * Application is supposed to implement this interface.
 *
 * Created by Zdenek Vales on 3.8.2017.
 */
public interface DiscussionUserService extends Serializable {

    /**
     * Returns the user by his id.
     * @param userId Id of the user.
     * @return
     */
    IDiscussionUser getUserById(Long userId);

}
