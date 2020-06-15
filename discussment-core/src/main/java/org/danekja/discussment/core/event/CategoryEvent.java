package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Category;
import org.springframework.context.ApplicationEvent;

/**
 * Abstract event with {@link Category} object as payload.
 */
public abstract class CategoryEvent {
    private final Category category;

    public CategoryEvent(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }
}
