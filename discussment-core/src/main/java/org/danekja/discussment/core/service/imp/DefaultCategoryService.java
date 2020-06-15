package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.Action;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.PermissionType;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.CategoryDao;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.event.CategoryCreatedEvent;
import org.danekja.discussment.core.event.CategoryEvent;
import org.danekja.discussment.core.event.CategoryRemovedEvent;
import org.danekja.discussment.core.service.CategoryService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class DefaultCategoryService implements CategoryService, ApplicationEventPublisherAware {

    private final CategoryDao categoryDao;
    private final AccessControlService accessControlService;
    private final DiscussionUserService discussionUserService;
    private ApplicationEventPublisher applicationEventPublisher;

    public DefaultCategoryService(CategoryDao categoryDao, AccessControlService accessControlService, DiscussionUserService discussionUserService) {
        this.categoryDao = categoryDao;
        this.accessControlService = accessControlService;
        this.discussionUserService = discussionUserService;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Category createCategory(Category entity) throws AccessDeniedException {
        if(accessControlService.canAddCategory()) {
            Category createdCategory = categoryDao.save(entity);
            publishEvent(new CategoryCreatedEvent(createdCategory));
            return createdCategory;
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
            publishEvent(new CategoryRemovedEvent(entity));
        } else {
            throw new AccessDeniedException(Action.DELETE, getCurrentUserId(), entity.getId(), PermissionType.CATEGORY);
        }
    }

    @Override
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

    /**
     * Publishes event if {@link #applicationEventPublisher} is not null.
     * @param event Event to be published.
     */
    private void publishEvent(CategoryEvent event) {
        if (applicationEventPublisher != null) {
            applicationEventPublisher.publishEvent(event);
        }
    }
}
