package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Category;

public class CategoryRemovedEvent extends CategoryEvent {
    public CategoryRemovedEvent(Category category) {
        super(category);
    }
}
