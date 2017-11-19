package org.danekja.discussment.example.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Permission;
import org.danekja.discussment.core.service.PermissionService;
import org.danekja.discussment.example.core.User;
import org.danekja.discussment.example.core.UserService;
import org.danekja.discussment.example.form.registration.RegistrationFormComponent;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class RegistrationForm extends Form {

    private UserService userService;
    private PermissionService permissionService;

    private IModel<User> userModel;

    private IModel<Permission> permissionModel;

    private RegistrationFormComponent registrationFormComponent;

    public RegistrationForm(String id, UserService userService, IModel<User> userModel, PermissionService permissionService, IModel<Permission> permissionModel) {
        super(id);

        this.userService = userService;
        this.userModel = userModel;
        this.permissionService = permissionService;
        this.permissionModel = permissionModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        registrationFormComponent = new RegistrationFormComponent("registrationFormComponent", userModel, permissionModel);
        add(registrationFormComponent);
    }

    @Override
    protected void onSubmit() {

        Permission p = registrationFormComponent.getPermissionModel().getObject();
        User u = userService.addUser(userModel.getObject(),p);
        permissionService.addPermissionForUser(p, u);
        getSession().setAttribute("user", u);

        setResponsePage(getWebPage().getClass());
    }
}
