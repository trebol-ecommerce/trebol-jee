package org.trebol.api.controllers.data.management;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.trebol.api.GenericCrudController;
import org.trebol.api.pojo.ProductPojo;
import org.trebol.config.CustomProperties;
import org.trebol.jpa.entities.Product;
import org.trebol.jpa.services.GenericCrudService;

/**
 * API point of entry for Product entities
 *
 * @author Benjamin La Madrid <bg.lamadrid at gmail.com>
 */
@RestController
@RequestMapping("/data/products")
public class ProductsDataManagementController
    extends GenericCrudController<ProductPojo, Product, Integer> {

  @Autowired
  public ProductsDataManagementController(CustomProperties globals,
      GenericCrudService<ProductPojo, Product, Integer> crudService) {
    super(globals, crudService);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('products:read')")
  public Collection<ProductPojo> readMany(@RequestParam Map<String, String> allRequestParams) {
    return super.readMany(null, null, allRequestParams);
  }

  @Override
  @PostMapping
  @PreAuthorize("hasAuthority('products:create')")
  public Integer create(@RequestBody @Valid ProductPojo input) {
    return super.create(input);
  }

  @Override
  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('products:read')")
  public ProductPojo readOne(@PathVariable Integer id) {
    return super.readOne(id);
  }

  @Override
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('products:update')")
  public Integer update(@RequestBody @Valid ProductPojo input, @PathVariable Integer id) {
    return super.update(input, id);
  }

  @Override
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('products:delete')")
  public boolean delete(@PathVariable Integer id) {
    return super.delete(id);
  }

  @Override
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    return super.handleValidationExceptions(ex);
  }
}
