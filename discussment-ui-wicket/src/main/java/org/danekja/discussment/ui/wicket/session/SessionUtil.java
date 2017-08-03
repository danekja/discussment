package org.danekja.discussment.ui.wicket.session;

import org.apache.wicket.Session;
import org.danekja.discussment.core.domain.IDiscussionUser;

import java.io.Serializable;

/**
 * Simple library class to interact with the Session object.
 *
 * Created by Zdenek Vales on 3.8.2017.
 */
public class SessionUtil implements Serializable {

    /**
     * Name of the attribute which will store the user object.
     */
    public static final String USER_ATTRIBUTE = "user";

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

    /**
     * Returns object from session.
     * @param attributeName
     * @return
     */
    public static Object getAttribute(String attributeName) {
        return Session.get().getAttribute(attributeName);
    }

    /**
     * Adds object to the session.
     * @param attributeName
     * @param value
     */
    public static void addAttribute(String attributeName, Serializable value) {
        Session.get().setAttribute(attributeName, value);
    }

    public static IDiscussionUser getUser() {
        return (IDiscussionUser) getAttribute(USER_ATTRIBUTE);
    }

    public static void setUser(IDiscussionUser user) {
        addAttribute(USER_ATTRIBUTE, user);
    }

    public static String getError() {
        return (String) getAttribute(ERROR_ATTRIBUTE);
    }

    public static void setError(String errorName) {
        addAttribute(ERROR_ATTRIBUTE, errorName);
    }

    public static void setAccess(boolean value) {addAttribute(ACCESS_ATTRIBUTE, new Boolean(value));}

    public static Boolean getAccess() {
        return (Boolean) getAttribute(ACCESS_ATTRIBUTE);
    }

    public static Long getDiscussionId() {
        return (Long) getAttribute(DISCUSSION_ID_ATTRIBUTE);
    }

    public static void setDiscussionId(Long discussionId) {
        addAttribute(DISCUSSION_ID_ATTRIBUTE, new Long(discussionId));
    }
}
