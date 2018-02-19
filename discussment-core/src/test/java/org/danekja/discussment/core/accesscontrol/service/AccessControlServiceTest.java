package org.danekja.discussment.core.accesscontrol.service;

import org.danekja.discussment.core.accesscontrol.dao.NewPermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.AbstractPermission;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.PermissionData;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.mock.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
// TODO: test global permissions as well
public class AccessControlServiceTest {

    @Mock
    private NewPermissionDao permissionDao;
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

    @BeforeClass
    public static void setUpGlobal() throws Exception {
        testUser = new User(-100L, "PMS Test User");
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(AccessControlServiceTest.class);
        pms = new PermissionService(permissionDao, userService);
        accessControlService = (AccessControlService) pms;
        testPermissions.clear();

        // mock
        when(userService.getCurrentlyLoggedUser()).thenReturn(testUser);
        when(permissionDao.save(any(AbstractPermission.class))).then(invocationOnMock -> {
            AbstractPermission newPerm = invocationOnMock.getArgumentAt(0, AbstractPermission.class);
            testPermissions.add(newPerm);
            return newPerm;
        });
        when(permissionDao.findForUser(any(IDiscussionUser.class), anyLong(), anyLong(), anyLong())).then(invocationOnMock -> testPermissions);
        when(permissionDao.findForUser(any(IDiscussionUser.class), anyLong(), anyLong())).then(invocationOnMock -> testPermissions);
        when(permissionDao.findForUser(any(IDiscussionUser.class), anyLong())).then(invocationOnMock -> testPermissions);
        when(permissionDao.findForUser(any(IDiscussionUser.class))).then(invocationOnMock -> testPermissions);

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
        assertTrue("Test user should be able to view posts in discussion!",accessControlService.canViewPosts(discussion));
        assertTrue("Test user should be able to add posts in discussion!", accessControlService.canAddPost(discussion));
        assertFalse("Test user should not be able to edit posts in discussion!", accessControlService.canEditPost(post));
        assertFalse("Test user should not be able to delete posts from discussion!", accessControlService.canRemovePost(post));

        // check permissions for user's own post
        assertTrue("Test user should be able to edit his post!", accessControlService.canEditPost(usersPost));
        assertTrue("Test user should be able to remove his post!", accessControlService.canRemovePost(usersPost));
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
        assertTrue("Test user should be able to view discussion in topic!", accessControlService.canViewDiscussions(topic));
        assertTrue("Test user should be able to create discussion in topic!", accessControlService.canAddDiscussion(topic));
        assertFalse("Test user should not be able to edit discussion in topic!", accessControlService.canEditDiscussion(discussion));
        assertFalse("Test user should not be able to remove discussion from topic!", accessControlService.canRemoveDiscussion(discussion));
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
        assertTrue("Test user should be able to view topic in category!", accessControlService.canViewTopics(category));
        assertTrue("Test user should be able to create topics in category!", accessControlService.canAddTopic(category));
        assertFalse("Test user should not be able to edit topics in category!", accessControlService.canEditTopic(topic));
        assertFalse("Test user should not be able to remove topics in category!", accessControlService.canRemoveTopic(topic));
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
        assertTrue("Test user should be able to view categories!", accessControlService.canViewCategories());
        assertTrue("Test user should be able to create categories!", accessControlService.canAddCategory());
        assertFalse("Test user should not be able to edit category!", accessControlService.canEditCategory(category));
        assertFalse("Test user should not be able to remove category!", accessControlService.canRemoveCategory(category));
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
        assertTrue("Test user should be able to view posts in discussion!",accessControlService.canViewPosts(discussion));
        assertTrue("Test user should be able to add posts in discussion!", accessControlService.canAddPost(discussion));
        assertFalse("Test user should not be able to edit posts in discussion!", accessControlService.canEditPost(post));
        assertFalse("Test user should not be able to delete posts from discussion!", accessControlService.canRemovePost(post));

        // test access in user's discussion
        assertTrue("Test user should be able to view posts in his discussion!",accessControlService.canViewPosts(usersDiscussion));
        assertTrue("Test user should be able to add posts in his discussion!", accessControlService.canAddPost(usersDiscussion));
        assertTrue("Test user should be able to edit post in his discussion!", accessControlService.canEditPost(p));
        assertTrue("Test user should be able to remove post from his discussion!", accessControlService.canRemovePost(p));
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
                return testPermissions.subList(0,1);
            }
        });

        // test access control in category
        assertTrue("Test user should be able to view posts in discussion!",accessControlService.canViewPosts(discussion));
        assertTrue("Test user should be able to add posts in discussion!", accessControlService.canAddPost(discussion));
        assertTrue("Test user should be able to edit posts in discussion!", accessControlService.canEditPost(post));
        assertTrue("Test user should be able to delete posts from discussion!", accessControlService.canRemovePost(post));

        // test access in user's discussion
        assertTrue("Test user should be able to view posts in limited discussion!",accessControlService.canViewPosts(limitedDiscussion));
        assertTrue("Test user should be able to add posts in limited discussion!", accessControlService.canAddPost(limitedDiscussion));
        assertFalse("Test user should not be able to edit post in limited discussion!", accessControlService.canEditPost(p));
        assertFalse("Test user should not be able to remove post from limited discussion!", accessControlService.canRemovePost(p));
    }
}
