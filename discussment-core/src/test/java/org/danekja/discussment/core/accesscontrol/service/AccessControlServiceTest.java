package org.danekja.discussment.core.accesscontrol.service;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.AbstractPermission;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.PermissionData;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.mock.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
// TODO: test global permissions as well
public class AccessControlServiceTest {

    @Mock
    private PermissionDao permissionDao;
    @Mock
    private DiscussionUserService userService;

    private PermissionManagementService pms;

    private AccessControlService accessControlService;

    private static User testUser;
    private List<AbstractPermission> testPermissions = new ArrayList<>();

    // basic test data
    private Category category;
    private Topic topic;
    private Discussion discussion;
    private Post post;

    @BeforeAll
    public static void setUpGlobal() throws Exception {
        testUser = new User("john.doe", "John Doe");
    }

    @BeforeEach
    public void setUp() throws Exception {
        pms = new PermissionService(permissionDao, userService);
        accessControlService = (AccessControlService) pms;
        testPermissions.clear();

        // mock
        lenient().when(userService.getCurrentlyLoggedUser()).thenReturn(testUser);
        lenient().when(permissionDao.save(any(AbstractPermission.class))).then(invocationOnMock -> {
            AbstractPermission newPerm = invocationOnMock.getArgument(0, AbstractPermission.class);
            testPermissions.add(newPerm);
            return newPerm;
        });
        lenient().when(permissionDao.findForUser(any(IDiscussionUser.class), anyLong(), anyLong(), anyLong())).then(invocationOnMock -> testPermissions);
        lenient().when(permissionDao.findForUser(any(IDiscussionUser.class), anyLong(), anyLong())).then(invocationOnMock -> testPermissions);
        lenient().when(permissionDao.findForUser(any(IDiscussionUser.class), anyLong())).then(invocationOnMock -> testPermissions);
        lenient().when(permissionDao.findForUser(any(IDiscussionUser.class))).then(invocationOnMock -> testPermissions);

        // initialize data
        category = new Category(-10L, "test category");
        topic = new Topic(-10L, "test topic", "test topic description");
        topic.setCategory(category);
        discussion = new Discussion(-10L,"test discussion");
        discussion.setTopic(topic);
        post = new Post("test post");
        post.setDiscussion(discussion);
        post.setUserId(UUID.randomUUID().toString());
    }

    /**
     * Basic post access control test.
     */
    @Test
    public void testPostAccessControl() {
        // item posted by user himself
        Post usersPost = new Post("Post created by test user.");
        usersPost.setDiscussion(discussion);
        usersPost.setUserId(testUser.getDiscussionUserId());

        // set permissions
        PermissionData data = new PermissionData(true, false, false, true);
        pms.configurePostPermissions(testUser, discussion, data);

        // test access control
        assertTrue(accessControlService.canViewPosts(discussion), "Test user should be able to view posts in discussion!");
        assertTrue(accessControlService.canAddPost(discussion), "Test user should be able to add posts in discussion!");
        assertFalse(accessControlService.canEditPost(post), "Test user should not be able to edit posts in discussion!");
        assertFalse(accessControlService.canRemovePost(post), "Test user should not be able to delete posts from discussion!");

        // check permissions for user's own post
        assertTrue(accessControlService.canEditPost(usersPost), "Test user should be able to edit his post!");
        assertTrue(accessControlService.canRemovePost(usersPost), "Test user should be able to remove his post!");
    }

    /**
     * Basic discussion access control test.
     */
    @Test
    public void testDiscussionAccessControl() {
        // set permissions
        PermissionData data = new PermissionData(true, false, false, true);
        pms.configureDiscussionPermissions(testUser, category, data);

        // test access control
        assertTrue(accessControlService.canViewDiscussions(topic), "Test user should be able to view discussion in topic!");
        assertTrue(accessControlService.canAddDiscussion(topic), "Test user should be able to create discussion in topic!");
        assertFalse(accessControlService.canEditDiscussion(discussion), "Test user should not be able to edit discussion in topic!");
        assertFalse(accessControlService.canRemoveDiscussion(discussion), "Test user should not be able to remove discussion from topic!");
    }

    /**
     * Basic topic access control test.
     */
    @Test
    public void testTopicAccessControl() {
        // set permissions
        PermissionData data = new PermissionData(true, false, false, true);
        pms.configureTopicPermissions(testUser, category, data);

        // test access control
        assertTrue(accessControlService.canViewTopics(category), "Test user should be able to view topic in category!");
        assertTrue(accessControlService.canAddTopic(category), "Test user should be able to create topics in category!");
        assertFalse(accessControlService.canEditTopic(topic), "Test user should not be able to edit topics in category!");
        assertFalse(accessControlService.canRemoveTopic(topic), "Test user should not be able to remove topics in category!");
    }

    /**
     * Basic category access control test.
     */
    @Test
    public void testCategoryAccessControl() {
        // set permissions
        PermissionData data = new PermissionData(true, false, false, true);
        pms.configureCategoryPermissions(testUser, data);

        // test access control
        assertTrue(accessControlService.canViewCategories(), "Test user should be able to view categories!");
        assertTrue(accessControlService.canAddCategory(), "Test user should be able to create categories!");
        assertFalse(accessControlService.canEditCategory(category), "Test user should not be able to edit category!");
        assertFalse(accessControlService.canRemoveCategory(category), "Test user should not be able to remove category!");
    }

    /**
     * User has a normal access to a category but also has extended permissions to one of the discussions in that category.
     * Test that he can access post inside his discussion and also other posts in category properly.
     */
    @Test
    public void testPostOverrideAccess() {
        // create user's discussion
        Discussion usersDiscussion = new Discussion(-11L, "Users own discussion");
        usersDiscussion.setTopic(topic);
        Post p = new Post("Post in user's discussion");
        p.setDiscussion(usersDiscussion);

        // set permissions
        // can create and view posts in category
        PermissionData categoryPostPerms = new PermissionData(true, false, false, true);
        // has complete control over posts in his discussion
        PermissionData discussionPostPerms = new PermissionData(true, true, true, true);
        pms.configurePostPermissions(testUser, usersDiscussion, discussionPostPerms);
        pms.configurePostPermissions(testUser, category, categoryPostPerms);

        // override mock so that correct permissions are returned
        when(permissionDao.findForUser(any(IDiscussionUser.class), anyLong(), anyLong(), anyLong())).then(invocationOnMock -> {
            Long discusionId = (Long) invocationOnMock.getArguments()[1];
            if(discusionId != null && discusionId == -11L) {
                return testPermissions;
            } else {
                return testPermissions.subList(1,2);
            }
        });

        // test access control in category
        assertTrue(accessControlService.canViewPosts(discussion), "Test user should be able to view posts in discussion!");
        assertTrue(accessControlService.canAddPost(discussion), "Test user should be able to add posts in discussion!");
        assertFalse(accessControlService.canEditPost(post), "Test user should not be able to edit posts in discussion!");
        assertFalse(accessControlService.canRemovePost(post), "Test user should not be able to delete posts from discussion!");

        // test access in user's discussion
        assertTrue(accessControlService.canViewPosts(usersDiscussion), "Test user should be able to view posts in his discussion!");
        assertTrue(accessControlService.canAddPost(usersDiscussion), "Test user should be able to add posts in his discussion!");
        assertTrue(accessControlService.canEditPost(p), "Test user should be able to edit post in his discussion!");
        assertTrue(accessControlService.canRemovePost(p), "Test user should be able to remove post from his discussion!");
    }

    /**
     * User has an extended access to a category but also has limied permissions to one of the discussions in that category.
     * Test that he can access post inside his discussion and also other posts in category properly.
     */
    @Test
    public void testPostOverrideAccess2() {
        // create discussion which will be limited for user
        Discussion limitedDiscussion = new Discussion(-11L, "Limited discussion");
        limitedDiscussion.setTopic(topic);
        Post p = new Post("Post in limited discussion");
        p.setDiscussion(limitedDiscussion);

        // set permissions
        // has full access to every post in category except the ones in the limited discussion
        // note that limitedDiscussion permission is added as the second one so that sorting in PermissionService.checkPermissions is tested also
        PermissionData categoryPostPerms = new PermissionData(true, true, true, true);
        PermissionData discussionPostPerms = new PermissionData(true, false, false, true);
        pms.configurePostPermissions(testUser, limitedDiscussion, discussionPostPerms);
        pms.configurePostPermissions(testUser, category, categoryPostPerms);

        // override mock so that correct permissions are returned
        when(permissionDao.findForUser(any(IDiscussionUser.class), anyLong(), anyLong(), anyLong())).then(invocationOnMock -> {
            Long discusionId = (Long) invocationOnMock.getArguments()[1];
            if(discusionId != null && discusionId == -11L) {
                return testPermissions;
            } else {
                return testPermissions.subList(1,2);
            }
        });

        // test access control in category
        assertTrue(accessControlService.canViewPosts(discussion), "Test user should be able to view posts in discussion!");
        assertTrue(accessControlService.canAddPost(discussion), "Test user should be able to add posts in discussion!");
        assertTrue(accessControlService.canEditPost(post), "Test user should be able to edit posts in discussion!");
        assertTrue(accessControlService.canRemovePost(post), "Test user should be able to delete posts from discussion!");

        // test access in user's discussion
        assertTrue(accessControlService.canViewPosts(limitedDiscussion), "Test user should be able to view posts in limited discussion!");
        assertTrue(accessControlService.canAddPost(limitedDiscussion), "Test user should be able to add posts in limited discussion!");
        assertFalse(accessControlService.canEditPost(p), "Test user should not be able to edit post in limited discussion!");
        assertFalse(accessControlService.canRemovePost(p), "Test user should not be able to remove post from limited discussion!");
    }
}
