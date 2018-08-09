package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class CategoryDaoIT extends GenericDaoIT<Long, Category> {

    protected final CategoryDao categoryDao;

    @Autowired
    public CategoryDaoIT(CategoryDao dao) {
        super(dao);
        this.categoryDao = dao;
    }

    @Override
    protected Category newTestInstance() {
        return new Category("CategoryDaoITInstance");
    }

    @Override
    protected Long getTestIdForSearch() {
        return -1L;
    }

    @Override
    protected Long getTestIdForSearch_notFound() {
        return -666L;
    }

    @Override
    protected Long getTestIdForRemove() {
        return -2L;
    }

    @Override
    protected String getSearchResultTestValue(Category item) {
        return item.getName();
    }

    @Test
    void getCategories() {
        List<Category> found = categoryDao.getCategories();
        internalTestSearchResults(TestData.TEST_CATEGORIES, found);
    }
}