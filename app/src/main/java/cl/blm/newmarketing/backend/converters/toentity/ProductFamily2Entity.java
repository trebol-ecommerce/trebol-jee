package cl.blm.newmarketing.backend.converters.toentity;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import cl.blm.newmarketing.backend.api.pojo.ProductFamilyPojo;
import cl.blm.newmarketing.backend.model.entities.ProductFamily;

@Component
public class ProductFamily2Entity
    implements Converter<ProductFamilyPojo, ProductFamily> {

  @Override
  public ProductFamily convert(ProductFamilyPojo source) {
    ProductFamily target = new ProductFamily(source.getId());
    target.setName(source.getName());
    return target;
  }
}
