package org.danekja.discussment.ui.wicket.session;

import org.apache.wicket.Session;

import java.io.Serializable;

/**
 * Simple library class to interact with the Session object.
 *
 * Created by Zdenek Vales on 3.8.2017.
 */
public class SessionUtil implements Serializable {

    /**
     * Name of the attribute which will store error.
     */
    public static final String ERROR_ATTRIBUTE = "error";

    /**
     * Name of the attribute which will store access value.
     */
    public static final String ACCESS_ATTRIBUTE = "access";

    /**
     * Name of the attribute which will store id of a discussion.
     */
    public static final String DISCUSSION_ID_ATTRIBUTE = "discussionId";

    public static String getError() {
        return (String) Session.get().getAttribute(ERROR_ATTRIBUTE);
    }

    public static void setError(String errorName) {
        Session.get().setAttribute(ERROR_ATTRIBUTE, errorName);
    }

    public static Boolean getAccess() {
        return (Boolean) Session.get().getAttribute(ACCESS_ATTRIBUTE);
    }

    public static void setAccess(boolean value) {
        Session.get().setAttribute(ACCESS_ATTRIBUTE, value);
    }

    public static Long getDiscussionId() {
        return (Long) Session.get().getAttribute(DISCUSSION_ID_ATTRIBUTE);
    }

    public static void setDiscussionId(Long discussionId) {
        Session.get().setAttribute(DISCUSSION_ID_ATTRIBUTE, discussionId);
    }
}
