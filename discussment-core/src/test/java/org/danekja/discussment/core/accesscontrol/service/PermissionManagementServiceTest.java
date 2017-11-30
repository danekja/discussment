package org.danekja.discussment.core.accesscontrol.service;

import org.danekja.discussment.core.accesscontrol.dao.NewPermissionDao;
import org.danekja.discussment.core.accesscontrol.dao.jpa.PermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.domain.PermissionData;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.dao.DiscussionDao;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.mock.NewUserService;
import org.danekja.discussment.core.mock.User;
import org.danekja.discussment.core.mock.UserDao;
import org.danekja.discussment.core.mock.UserDaoMock;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Zdenek Vales on 26.11.2017.
 */
public class PermissionManagementServiceTest {

    private PermissionManagementService permissionManagementService;
    private AccessControlService accessControlService;
    private UserDao userDao;

    private Discussion discussion;
    private User user;
    private User adminUser;

    @Before
    public void setUp() {
        NewPermissionDao newPermissionDao = new PermissionDaoJPA();
        userDao = new UserDaoMock();
        DiscussionUserService discussionUserService = new NewUserService(userDao);

        DiscussionDao discussionDao = new DiscussionDaoJPA();
        this.permissionManagementService = new PermissionService(newPermissionDao, discussionUserService);
        this.accessControlService = (AccessControlService)this.permissionManagementService;

        // test discussion
        discussion = new Discussion("test discussion");
        discussion = discussionDao.save(discussion);

        // test admin with all permissions = every action is allowed on GLOBAL level
        prepareAdminUser(-100L);

        // test user with some permissions
        prepareNormalUser(-101L);
    }

    /**
     * Creates and saves new user with all permission on GLOBAL level.
     * @param userId Database id which will be assigned to user.
     */
    private void prepareAdminUser(Long userId) {
        adminUser = new User("adminUser", "", "");
        adminUser.setId(userId);
        adminUser = userDao.save(adminUser);
        PermissionData allPermissions = new PermissionData(true, true, true, true);
        permissionManagementService.configureCategoryPermissions(adminUser,allPermissions );
        permissionManagementService.configureDiscussionPermissions(adminUser, allPermissions);
        permissionManagementService.configureTopicPermissions(adminUser, allPermissions);
        permissionManagementService.configurePostPermissions(adminUser,allPermissions);
    }

    /**
     * Creates and saves new user with permission to only view and create posts globaly.
     * @param userId Database id which will be assigned to user.
     */
    private void prepareNormalUser(Long userId) {
        user = new User("test", "", "");
        user.setId(userId);
        user = userDao.save(user);

        // user can view and create posts on global level
        PermissionData usersPostPermission = new PermissionData(true, false, false, true);
        permissionManagementService.configurePostPermissions(user, usersPostPermission);
    }

    /**
     * Try to add post to discussion without any permission.
     */
    @Test
    public void testAddPostNoPermission() {
        Post p = new Post("Test post");

    }
}
