package org.burgas.verticalsharding.mapper;

import org.burgas.verticalsharding.dto.Request;
import org.burgas.verticalsharding.dto.Response;
import org.burgas.verticalsharding.entity.AbstractEntity;
import org.springframework.stereotype.Component;

@Component
public interface EntityMapper<T extends Request, S extends AbstractEntity, V extends Response> {

    default <D> D handleData(D requestData, D entityData) {
        return requestData == null || requestData == "" ? entityData : requestData;
    }

    default <D> D handleDataException(D requestData, String message) {
        if (requestData == null || requestData == "")
            throw new RuntimeException(message);
        return requestData;
    }

    S toEntity(T t);

    V toResponse(S s);
}
