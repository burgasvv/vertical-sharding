package org.burgas.replicashard.message.first;

import lombok.Getter;

@Getter
public enum CategoryMessages {

    CATEGORY_DELETED("Category was successfully deleted"),
    CATEGORY_NOT_FOUND("Category not found"),
    CATEGORY_FIELD_NAME_EMPTY("Category field name is empty"),
    CATEGORY_FIELD_DESCRIPTION_EMPTY("Category field description is empty");

    private final String message;

    CategoryMessages(String message) {
        this.message = message;
    }
}
