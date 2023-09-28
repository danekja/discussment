package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.CategoryDao;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.mock.User;
import org.danekja.discussment.core.service.imp.DefaultCategoryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {


    private static User testUser;

    @Mock
    private CategoryDao categoryDao;

    @Mock
    private AccessControlService accessControlService;

    @Mock
    private DiscussionUserService discussionUserService;

    private CategoryService categoryService;

    @BeforeAll
    static void setUpGlobally() {
        testUser = new User("john.doe", "John Doe");
    }

    @BeforeEach
    void setUp() throws DiscussionUserNotFoundException {
        lenient().when(discussionUserService.getCurrentlyLoggedUser()).thenReturn(testUser);
        lenient().when(discussionUserService.getUserById(anyString())).thenReturn(testUser);
        lenient().when(accessControlService.canAddCategory()).thenReturn(true);
        lenient().when(accessControlService.canEditCategory(any(Category.class))).thenReturn(true);
        lenient().when(accessControlService.canRemoveCategory(any(Category.class))).thenReturn(true);
        lenient().when(accessControlService.canViewCategories()).thenReturn(true);

        categoryService = new DefaultCategoryService(categoryDao, accessControlService, discussionUserService);
    }

    @Test
    void createCategory() throws AccessDeniedException {
        // prepare data
        Category category = new Category(-87L, "Test category");
        when(categoryDao.save(any(Category.class))).then(invocationOnMock -> invocationOnMock.getArgument(0));

        Category c = categoryService.createCategory(category);
        assertNotNull(c, "Category is null!");
        assertEquals(c.getId().longValue(), category.getId().longValue(), "Category has wrong id!");
    }

    @Test
    void getCategoryById() throws AccessDeniedException {
        final Category category = new Category(-87L, "Test category");
        when(categoryDao.getById(any(Long.class))).then(invocationOnMock -> category);

        Category c = categoryService.getCategoryById(-87L);
        assertNotNull(c, "Null category returned!");
        assertEquals(category, c, "Wrong category returned!");
    }

    @Test
    void getCategories() throws AccessDeniedException {
        final Category category = new Category(-87L, "Test category");
        when(categoryDao.getCategories()).then(invocationOnMock -> Arrays.asList(category));

        List<Category> categories = categoryService.getCategories();
        assertNotNull(categories, "Null returned!");
        assertEquals(1, categories.size(), "Wrong number of categories returned!");
        assertEquals(category, categories.get(0), "Wrong category returned!");
    }

    @Test
    void removeCateogry() throws AccessDeniedException {
        final List<Category> categoryRepository = new ArrayList<>();

        // mock get, save and remove methods
        when(categoryDao.getById(any(Long.class))).then(invocationOnMock -> {
            Long id = (Long) invocationOnMock.getArgument(0);
            for(Category c : categoryRepository) {
                if(c.getId().equals(id)) {
                    return c;
                }
            }

            return null;
        });
        when(categoryDao.save(any(Category.class))).then(invocationOnMock -> {
            Category category = (Category) invocationOnMock.getArgument(0);
            categoryRepository.add(category);
            return category;
        });
        doAnswer(invocationOnMock -> {
            Category toBeRemoved = (Category) invocationOnMock.getArgument(0);
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
        assertNotNull(toBeRemoved, "Category to be removed not found!");
        categoryService.removeCategory(toBeRemoved);
        assertNull(categoryService.getCategoryById(toBeRemoved.getId()), "Category not removed!");
    }
}
