package cz.zcu.fav.kiv.discussion.gui.model;

import cz.zcu.fav.kiv.discussion.core.model.CategoryModel;
import cz.zcu.fav.kiv.discussion.core.service.CategoryService;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class CategoryWicketModel implements IModel<List<CategoryModel>> {

    public void detach() {
    }

    public List<CategoryModel> getObject() {
        return CategoryService.getCategories();
    }

    public void setObject(List<CategoryModel> object) {
    }
}
