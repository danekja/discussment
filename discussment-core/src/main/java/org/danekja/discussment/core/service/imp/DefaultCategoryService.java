package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.Action;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.PermissionType;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.CategoryDao;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class DefaultCategoryService implements CategoryService {

    private CategoryDao categoryDao;
    private AccessControlService accessControlService;
    private DiscussionUserService discussionUserService;

    public DefaultCategoryService(CategoryDao categoryDao, AccessControlService accessControlService, DiscussionUserService discussionUserService) {
        this.categoryDao = categoryDao;
        this.accessControlService = accessControlService;
        this.discussionUserService = discussionUserService;
    }

    @Override
    public Category createCategory(Category entity) throws AccessDeniedException {
        if(accessControlService.canAddCategory()) {
            return categoryDao.save(entity);
        } else {
            throw new AccessDeniedException(Action.CREATE, getCurrentUserId(), 0, PermissionType.CATEGORY);
        }
    }

    @Override
    public Category getCategoryById(long categoryId) throws AccessDeniedException {
        if (accessControlService.canViewCategories()) {
            return categoryDao.getById(categoryId);
        } else {
            throw new AccessDeniedException(Action.VIEW, getCurrentUserId(), categoryId, PermissionType.CATEGORY);
        }
    }

    @Override
    public List<Category> getCategories() throws AccessDeniedException {
        if (accessControlService.canViewCategories()) {
            return categoryDao.getCategories();
        } else {
            throw new AccessDeniedException(Action.VIEW, getCurrentUserId(), 0, PermissionType.CATEGORY);
        }
    }

    @Override
    public void removeCategory(Category entity) throws AccessDeniedException {
        if (accessControlService.canRemoveCategory(entity)) {
            categoryDao.remove(entity);
        } else {
            throw new AccessDeniedException(Action.DELETE, getCurrentUserId(), entity.getId(), PermissionType.CATEGORY);
        }
    }

    public Category getDefaultCategory(){
        Category category = categoryDao.getById(Category.DEFAULT_CATEGORY_ID);
        if(category == null) {
            category = new Category(Category.DEFAULT_CATEGORY_ID, "default category");
            categoryDao.save(category);
        }
        return category;
    }

    /**
     * Returns the id of the currently logged user. Used when throwing access denied exception.
     * @return Id of the currently logged user or null if no user is logged.
     */
    private String getCurrentUserId() {
        IDiscussionUser user = discussionUserService.getCurrentlyLoggedUser();
        return user == null ? null : user.getDiscussionUserId();
    }
}
