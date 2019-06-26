package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.PermissionData;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
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
    private DiscussionUserService userService;
    private AccessControlService accessControlService;
    private PermissionManagementService permissionService;

    private IModel<Category> categoryModel;

    /**
     * Constructor for creating a instance of the form for adding a new form
     *
     * @param id id of the element into which the panel is inserted
     * @param categoryService instance of the category service
     * @param categoryModel model contains the category for setting the form
     */
    public CategoryForm(String id,
                        CategoryService categoryService,
                        DiscussionUserService userService,
                        AccessControlService accessControlService,
                        PermissionManagementService permissionService,
                        IModel<Category> categoryModel) {
        super(id);

        this.categoryService = categoryService;
        this.userService = userService;
        this.accessControlService = accessControlService;
        this.permissionService = permissionService;

        this.categoryModel = categoryModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new CategoryFormComponent("categoryFormComponent", categoryModel));
    }

    @Override
    protected void onSubmit() {
        try {
            Category category = categoryService.createCategory(categoryModel.getObject());

            if (!accessControlService.canAddTopic(category)) {
                permissionService.configureTopicPermissions(userService.getCurrentlyLoggedUser(), category, new PermissionData(true, false, false, true));
            }

        } catch (AccessDeniedException e) {
            //todo: not yet implemented
        }

        categoryModel.setObject(new Category());
    }
}
