package org.danekja.discussment.core.accesscontrol.service;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.*;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.mock.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class PermissionManagementServiceTest {

    private final Long NULL_ITEM_ID = 0L;

    @Mock
    private PermissionDao permissionDao;
    @Mock
    private DiscussionUserService userService;

    private PermissionManagementService pms;

    private static User testUser;
    private List<AbstractPermission> testPermissions = new ArrayList<>();

    @BeforeAll
    public static void setUpGlobal() throws Exception {
        testUser = new User("john.doe", "John Doe");
    }

    @BeforeEach
    public void setUp() throws Exception {
        pms = new PermissionService(permissionDao, userService);
        testPermissions.clear();

        lenient().when(userService.getCurrentlyLoggedUser()).thenReturn(testUser);
        lenient().when(permissionDao.save(any(AbstractPermission.class))).then(invocationOnMock -> {
            AbstractPermission newPerm = invocationOnMock.getArgument(0, AbstractPermission.class);
            testPermissions.add(newPerm);
            return newPerm;
        });
    }

    @Test
    public void testConfigurePostPermissionsForDiscussion() throws Exception {
        Discussion item = new Discussion(-10L, "PMS Test Discussion");
        PermissionData data = new PermissionData(true, false, true, false);
        pms.configurePostPermissions(testUser, item, data);

        testPermission(item.getId(), PermissionLevel.DISCUSSION, PostPermission.class, data);
    }

    @Test
    public void testConfigurePostPermissionsForTopic() throws Exception {
        Topic item = new Topic(-10L, "PMS Test Topic", "PMS Test Topic Description");
        PermissionData data = new PermissionData(true, false, true, false);
        pms.configurePostPermissions(testUser, item, data);

        testPermission(item.getId(), PermissionLevel.TOPIC, PostPermission.class, data);
    }

    @Test
    public void testConfigurePostPermissionsForCategory() throws Exception {
        Category item = new Category(-10L, "PMS Test Category");
        PermissionData data = new PermissionData(true, false, true, false);
        pms.configurePostPermissions(testUser, item, data);

        testPermission(item.getId(), PermissionLevel.CATEGORY, PostPermission.class, data);
    }

    @Test
    public void testConfigurePostPermissionsGlobal() throws Exception {
        PermissionData data = new PermissionData(true, false, true, false);
        pms.configurePostPermissions(testUser, data);

        testPermission(NULL_ITEM_ID, PermissionLevel.GLOBAL, PostPermission.class, data);
    }

    @Test
    public void testConfigureDiscussionPermissionsForTopic() throws Exception {
        Topic item = new Topic(-10L, "PMS Test Topic", "PMS Test Topic Description");
        PermissionData data = new PermissionData(true, false, true, false);
        pms.configureDiscussionPermissions(testUser, item, data);

        testPermission(item.getId(), PermissionLevel.TOPIC, DiscussionPermission.class, data);
    }

    @Test
    public void testConfigureDiscussionPermissionsForCategory() throws Exception {
        Category item = new Category(-10L, "PMS Test Category");
        PermissionData data = new PermissionData(true, false, true, false);
        pms.configureDiscussionPermissions(testUser, item, data);

        testPermission(item.getId(), PermissionLevel.CATEGORY, DiscussionPermission.class, data);
    }

    @Test
    public void testConfigureDiscussionPermissionsGlobal() throws Exception {
        PermissionData data = new PermissionData(true, false, true, false);
        pms.configureDiscussionPermissions(testUser, data);

        testPermission(NULL_ITEM_ID, PermissionLevel.GLOBAL, DiscussionPermission.class, data);
    }

    @Test
    public void testConfigureTopicPermissionsForCategory() throws Exception {
        Category item = new Category(-10L, "PMS Test Category");
        PermissionData data = new PermissionData(true, false, true, false);
        pms.configureTopicPermissions(testUser, item, data);

        testPermission(item.getId(), PermissionLevel.CATEGORY, TopicPermission.class, data);
    }

    @Test
    public void testConfigureTopicPermissionsGlobal() throws Exception {
        PermissionData data = new PermissionData(true, false, true, false);
        pms.configureTopicPermissions(testUser, data);

        testPermission(NULL_ITEM_ID, PermissionLevel.GLOBAL, TopicPermission.class, data);
    }

    @Test
    public void testConfigureCategoryPermissions() throws Exception {
        PermissionData data = new PermissionData(true, false, true, false);
        pms.configureCategoryPermissions(testUser, data);

        testPermission(NULL_ITEM_ID, PermissionLevel.GLOBAL, CategoryPermission.class, data);
    }

    protected void testPermission(Long expectedItemId, PermissionLevel expectedPermissionLevel, Class<? extends AbstractPermission> expectedType, PermissionData expectedData) {
        //one permission added
        assertEquals(1, testPermissions.size());
        AbstractPermission ap = testPermissions.get(0);
        //post permission added
        assertEquals(expectedType, ap.getClass());

        //correct ID created
        assertEquals(expectedItemId, ap.getId().getItemId());
        assertEquals(expectedPermissionLevel, ap.getId().getLevel());
        assertEquals(testUser.getDiscussionUserId(), ap.getId().getUserId());

        assertEquals(expectedData, ap.getData());
    }
}
