package pl.kdrozd.examples.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring")
public interface JsonNullableMapper {

    @Named("unwrap")
    default String unwrap(JsonNullable<String> nullable) {
        return nullable.orElse(null);
    }

    @Named("wrap")
    default JsonNullable<String> wrap(String entity) {
        return JsonNullable.of(entity);
    }

}
