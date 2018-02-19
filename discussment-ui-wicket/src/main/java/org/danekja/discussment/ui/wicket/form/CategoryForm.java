package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.ui.wicket.form.category.CategoryFormComponent;

/**
 * Created by Martin Bláha on 25.01.17.
 *
 * The class creates the form for creating a new category
 */
public class CategoryForm extends Form {

    private CategoryService categoryService;

    private IModel<Category> categoryModel;

    /**
     * Constructor for creating a instance of the form for adding a new form
     *
     * @param id id of the element into which the panel is inserted
     * @param categoryModel model contains the category for setting the form
     */
    public CategoryForm(String id, IModel<Category> categoryModel) {
        this(id, null, categoryModel);
    }

    /**
     * Constructor for creating a instance of the form for adding a new form
     *
     * @param id id of the element into which the panel is inserted
     * @param categoryService instance of the category service
     * @param categoryModel model contains the category for setting the form
     */
    public CategoryForm(String id, CategoryService categoryService, IModel<Category> categoryModel) {
        super(id);

        this.categoryService = categoryService;
        this.categoryModel = categoryModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new CategoryFormComponent("categoryFormComponent", categoryModel));
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected void onSubmit() {

        if (categoryService != null) {
            try {
                categoryService.createCategory(categoryModel.getObject());
            } catch (AccessDeniedException e) {
                //todo: not yet implemented
            }

            categoryModel.setObject(new Category());
        }

    }
}
