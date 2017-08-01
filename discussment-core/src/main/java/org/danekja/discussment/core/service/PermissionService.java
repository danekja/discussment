package org.danekja.discussment.core.service;

import net.bytebuddy.agent.builder.AgentBuilder;
import org.danekja.discussment.core.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Permission;

import java.io.Serializable;
import java.util.List;

/**
 * Interface containing methods for working with user's permissions.
 *
 * Created by Zdenek Vales on 1.8.2017.
 */
public interface PermissionService extends Serializable {

    /**
     * Get user's permissions.
     * @param user User object. If null, null is returned.
     * @return
     */
    Permission getUsersPermissions(IDiscussionUser user);
}
