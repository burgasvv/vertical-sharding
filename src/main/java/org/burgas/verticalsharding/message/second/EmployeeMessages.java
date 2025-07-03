package org.burgas.verticalsharding.message.second;

import lombok.Getter;

@Getter
public enum EmployeeMessages {

    EMPLOYEE_DELETED("Employee successfully deleted"),
    EMPLOYEE_NOT_FOUND("Employee not found"),
    EMPLOYEE_FIELD_NAME_EMPTY("Employee field name is empty"),
    EMPLOYEE_FIELD_SURNAME_EMPTY("Employee field surname is empty"),
    EMPLOYEE_FIELD_PATRONYMIC_EMPTY("Employee field patronymic is empty"),
    EMPLOYEE_FIELD_DEPARTMENT_EMPTY("Employee field department is empty");

    private final String message;

    EmployeeMessages(String message) {
        this.message = message;
    }
}
