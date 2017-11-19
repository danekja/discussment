package org.danekja.discussment.core.accesscontrol.domain;

/**
 * Enumeration for distinguishing on which level certain permission applies.
 *
 * @author Jakub Danek
 */
public enum PermissionLevel {

    /**
     * Permission applies to all items of particular type (all topics, categories, etc)
     */
    GLOBAL,
    /**
     * Permission applies only to items within the given category (the items may be topics, discussions or posts).
     */
    CATEGORY,
    /**
     * Permission applies only to items within the given topic (the items may be discussions or posts).
     */
    TOPIC,
    /**
     * Permission applies only to items within the given discussion (the items must be posts)
     */
    DISCUSSION;

}
