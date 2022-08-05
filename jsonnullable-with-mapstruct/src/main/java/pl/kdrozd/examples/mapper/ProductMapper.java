package pl.kdrozd.examples.mapper;

import org.mapstruct.*;
import pl.kdrozd.examples.dto.ProductDTO;
import pl.kdrozd.examples.model.Product;

@Mapper(uses = JsonNullableMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product map(ProductDTO entity);

    ProductDTO map(Product entity);

    @InheritConfiguration
    void update(ProductDTO update, @MappingTarget Product destination);

}


