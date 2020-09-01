package cl.blm.newmarketing.backend.converters.topojo;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import cl.blm.newmarketing.backend.api.pojo.ProductFamilyPojo;
import cl.blm.newmarketing.backend.api.pojo.ProductTypePojo;
import cl.blm.newmarketing.backend.model.entities.ProductType;

@Component
public class ProductType2Pojo
    implements Converter<ProductType, ProductTypePojo> {

  @Override
  public ProductTypePojo convert(ProductType source) {
    ProductTypePojo target = new ProductTypePojo();
    target.setId(source.getId());
    target.setName(source.getName());
    target.setProductFamily(new ProductFamilyPojo());
    target.getProductFamily().setId(source.getProductFamily().getId());
    target.getProductFamily().setName(source.getProductFamily().getName());
    return target;
  }
}
