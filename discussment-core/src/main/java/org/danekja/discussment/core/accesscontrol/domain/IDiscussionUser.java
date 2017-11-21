package org.danekja.discussment.core.accesscontrol.domain;

import java.io.Serializable;

/**
 * This interface represents the user entity in the context of this library.
 * Application which will use this library will have to implement this interface.
 *
 * Created by Zdenek Vales on 1.8.2017.
 */
public interface IDiscussionUser extends Serializable {

    /**
     * @return unique identifier of the user within the hosting application
     */
    String getDiscussionUserId();

    /**
     * @return presentable name of the user to be displayed in the UI
     */
    String getDisplayName();

}
