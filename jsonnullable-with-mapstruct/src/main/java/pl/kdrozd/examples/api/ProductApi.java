package pl.kdrozd.examples.api;

import org.springframework.web.bind.annotation.*;
import pl.kdrozd.examples.dto.ProductDTO;
import pl.kdrozd.examples.service.ProductService;

@RestController
public class ProductApi {

    private final ProductService service;

    public ProductApi(ProductService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/product/{id}")
    public ProductDTO update(@PathVariable(name = "id") Long productId, @RequestBody ProductDTO update) {
        return service.update(productId, update);
    }
}
