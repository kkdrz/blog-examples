package pl.kdrozd.examples.dto;

import org.openapitools.jackson.nullable.JsonNullable;

import java.util.Objects;

public class ProductDTO {

    private Long id;
    private String name;
    private Integer quantity;
    private JsonNullable<String> description;
    private JsonNullable<String> manufacturer;

    public boolean hasDescription() {
        return description != null && description.isPresent();
    }

    public boolean hasManufacturer() {
        return manufacturer != null && manufacturer.isPresent();
    }

    // constructor, getters, setters...

    public ProductDTO(Long id, String name, Integer quantity, JsonNullable<String> description, JsonNullable<String> manufacturer) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.manufacturer = manufacturer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public JsonNullable<String> getDescription() {
        return description;
    }

    public void setDescription(JsonNullable<String> description) {
        this.description = description;
    }

    public JsonNullable<String> getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(JsonNullable<String> manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(description, that.description) &&
                Objects.equals(manufacturer, that.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, description, manufacturer);
    }
}
