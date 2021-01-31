package pl.kdrozd.examples.mapper;

import org.mapstruct.*;
import pl.kdrozd.examples.dto.ProductDTO;
import pl.kdrozd.examples.model.Product;

@Mapper(uses = JsonNullableMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "description", target = "description", qualifiedByName = "unwrap")
    @Mapping(source = "manufacturer", target = "manufacturer", qualifiedByName = "unwrap")
    Product map(ProductDTO entity);

    @Mapping(source = "description", target = "description", qualifiedByName = "wrap")
    @Mapping(source = "manufacturer", target = "manufacturer", qualifiedByName = "wrap")
    ProductDTO map(Product entity);

    @InheritConfiguration
    @Mapping(target = "id", ignore = true)
    void update(ProductDTO update, @MappingTarget Product destination);

}


