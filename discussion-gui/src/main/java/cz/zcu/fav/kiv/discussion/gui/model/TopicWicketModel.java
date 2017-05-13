package cz.zcu.fav.kiv.discussion.gui.model;

import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;
import cz.zcu.fav.kiv.discussion.core.entity.TopicEntity;
import cz.zcu.fav.kiv.discussion.core.service.TopicService;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class TopicWicketModel implements IModel<List<TopicEntity>> {


    private List<TopicEntity> topics;

    private CategoryEntity categoryEntity;

    public TopicWicketModel() {

        this.categoryEntity = null;

    }

    public TopicWicketModel(CategoryEntity categoryEntity) {

        this.categoryEntity = categoryEntity;

    }

    public void detach() {
    }

    public List<TopicEntity> getObject() {

        if (categoryEntity == null) {
            return TopicService.getTopicsWithoutCategory();
        } else {
            return TopicService.getTopicsByCategory(categoryEntity);
        }

    }

    public void setObject(List<TopicEntity> object) {
    }
}
