package org.danekja.discussment.core.accesscontrol.service;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;

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
     * @return user object representing the user with given id
     * @throws DiscussionUserNotFoundException is thrown whenever user with this id is not found.
     */
    IDiscussionUser getUserById(String userId) throws DiscussionUserNotFoundException;

    /**
     * Returns the user which is currently logged into application.
     *
     * @return Currently logged user and null if no user is logged in.
     */
    IDiscussionUser getCurrentlyLoggedUser();

}
