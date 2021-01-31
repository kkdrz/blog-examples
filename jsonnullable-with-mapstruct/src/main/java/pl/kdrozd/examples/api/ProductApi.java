package pl.kdrozd.examples.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kdrozd.examples.dto.ProductDTO;
import pl.kdrozd.examples.service.ProductService;

@RestController
public class ProductApi {

    private final ProductService service;

    public ProductApi(ProductService service) {
        this.service = service;
    }

    @RequestMapping("/product/{id}")
    public ProductDTO update(@RequestParam(name = "id") long productId, @RequestBody ProductDTO update) {
        return service.update(productId, update);
    }
}
