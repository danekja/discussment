package org.danekja.discussment.core.accesscontrol.domain;

/**
 * Possible types of permissions. Part of permission key id.
 * Ordering should go from the lowest to the highest type of permission:
 * POST &lt; DISCUSSION &lt; TOPIC &lt; CATEGORY
 */
public enum PermissionType {

    POST,
    DISCUSSION,
    TOPIC,
    CATEGORY
}
