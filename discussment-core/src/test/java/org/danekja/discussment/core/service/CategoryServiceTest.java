package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.CategoryDao;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.mock.User;
import org.danekja.discussment.core.service.imp.DefaultCategoryService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {


    private static User testUser;

    @Mock
    private CategoryDao categoryDao;

    @Mock
    private AccessControlService accessControlService;

    @Mock
    private DiscussionUserService discussionUserService;

    private CategoryService categoryService;

    @BeforeClass
    public static void setUpGlobally() {
        testUser = new User("john.doe", "John Doe");
    }

    @Before
    public void setUp() throws DiscussionUserNotFoundException {
        MockitoAnnotations.initMocks(CategoryServiceTest.class);

        when(discussionUserService.getCurrentlyLoggedUser()).then(invocationOnMock -> testUser);
        when(discussionUserService.getUserById(anyString())).then(invocationOnMock -> testUser);
        when(accessControlService.canAddCategory()).then(invocationOnMock -> true);
        when(accessControlService.canEditCategory(any(Category.class))).then(invocationOnMock -> true);
        when(accessControlService.canRemoveCategory(any(Category.class))).then(invocationOnMock -> true);
        when(accessControlService.canViewCategories()).then(invocationOnMock -> true);

        categoryService = new DefaultCategoryService(categoryDao, accessControlService, discussionUserService);
    }

    @Test
    public void testCreateCategory() throws AccessDeniedException {
        // prepare data
        Category category = new Category(-87L, "Test category");
        when(categoryDao.save(any(Category.class))).then(invocationOnMock -> invocationOnMock.getArguments()[0]);

        Category c = categoryService.createCategory(category);
        assertNotNull("Category is null!", c);
        assertEquals("Category has wrong id!", c.getId().longValue(), category.getId().longValue());
    }

    @Test
    public void testGetCategoryById() throws AccessDeniedException {
        final Category category = new Category(-87L, "Test category");
        when(categoryDao.getById(any(Long.class))).then(invocationOnMock -> category);

        Category c = categoryService.getCategoryById(-87L);
        assertNotNull("Null category returned!" , c);
        assertEquals("Wrong category returned!", category, c);
    }

    @Test
    public void testGetCategories() throws AccessDeniedException {
        final Category category = new Category(-87L, "Test category");
        when(categoryDao.getCategories()).then(invocationOnMock -> Arrays.asList(category));

        List<Category> categories = categoryService.getCategories();
        assertNotNull("Null returned!", categories);
        assertEquals("Wrong number of categories returned!", 1, categories.size());
        assertEquals("Wrong category returned!", category, categories.get(0));
    }

    @Test
    public void testRemoveCateogry() throws AccessDeniedException {
        final List<Category> categoryRepository = new ArrayList<>();

        // mock get, save and remove methods
        when(categoryDao.getById(any(Long.class))).then(invocationOnMock -> {
            Long id = (Long) invocationOnMock.getArguments()[0];
            for(Category c : categoryRepository) {
                if(c.getId().equals(id)) {
                    return c;
                }
            }

            return null;
        });
        when(categoryDao.save(any(Category.class))).then(invocationOnMock -> {
            Category category = (Category) invocationOnMock.getArguments()[0];
            categoryRepository.add(category);
            return category;
        });
        doAnswer(invocationOnMock -> {
            Category toBeRemoved = (Category) invocationOnMock.getArguments()[0];
            for(Category c : categoryRepository) {
                if(c.getId().equals(toBeRemoved.getId())) {
                    categoryRepository.remove(c);
                    break;
                }
            }

            return invocationOnMock;
        }).when(categoryDao).remove(any(Category.class));

        // create category to be removed
        Category category = new Category(55L, "Test category");
        categoryService.createCategory(category);

        // test category removing
        Category toBeRemoved = categoryService.getCategoryById(category.getId());
        assertNotNull("Category to be removed not found!", toBeRemoved);
        categoryService.removeCategory(toBeRemoved);
        assertNull("Cateogry not removed!", categoryService.getCategoryById(toBeRemoved.getId()));
    }
}
