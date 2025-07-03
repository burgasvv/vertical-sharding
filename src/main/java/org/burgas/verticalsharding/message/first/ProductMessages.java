package org.burgas.verticalsharding.message.first;

import lombok.Getter;

@Getter
public enum ProductMessages {

    PRODUCT_DELETED("Product successfully deleted"),
    PRODUCT_NOT_FOUND_EXCEPTION("Product not found"),
    PRODUCT_FIELD_MODEL_EMPTY("Product field model is empty"),
    PRODUCT_FIELD_CHARACTERISTICS_EMPTY("Product field characteristics is empty"),
    PRODUCT_FIELD_PRICE_EMPTY("Product field price is empty"),
    PRODUCT_FIELD_CATEGORY_EMPTY("Product field category is empty");

    private final String message;

    ProductMessages(String message) {
        this.message = message;
    }
}
