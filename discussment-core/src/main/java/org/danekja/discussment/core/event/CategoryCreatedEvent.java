package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Category;

public class CategoryCreatedEvent extends CategoryEvent {
    public CategoryCreatedEvent(Category category) {
        super(category);
    }
}
