package org.trebol.jpa.services.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import org.trebol.jpa.entities.QProductFamily;

import org.trebol.api.pojo.ProductFamilyPojo;
import org.trebol.jpa.entities.ProductFamily;
import org.trebol.jpa.repositories.ProductFamiliesRepository;
import org.trebol.jpa.services.GenericCrudService;

/**
 *
 * @author Benjamin La Madrid <bg.lamadrid at gmail.com>
 */
@Transactional
@Service
public class ProductFamilyCrudServiceImpl
    extends GenericCrudService<ProductFamilyPojo, ProductFamily, Integer> {
  private static final Logger LOG = LoggerFactory.getLogger(ProductFamilyCrudServiceImpl.class);

  private final ConversionService conversion;

  @Autowired
  public ProductFamilyCrudServiceImpl(ProductFamiliesRepository repository, ConversionService conversion) {
    super(repository);
    this.conversion = conversion;
  }

  @Override
  public ProductFamilyPojo entity2Pojo(ProductFamily source) {
    return conversion.convert(source, ProductFamilyPojo.class);
  }

  @Override
  public ProductFamily pojo2Entity(ProductFamilyPojo source) {
    return conversion.convert(source, ProductFamily.class);
  }

  @Override
  public Predicate queryParamsMapToPredicate(Map<String, String> queryParamsMap) {
    QProductFamily qProductFamily = QProductFamily.productFamily;
    BooleanBuilder predicate = new BooleanBuilder();
    for (String paramName : queryParamsMap.keySet()) {
      String stringValue = queryParamsMap.get(paramName);
      try {
        Integer intValue;
        switch (paramName) {
          case "id":
            intValue = Integer.valueOf(stringValue);
            return predicate.and(qProductFamily.id.eq(intValue)); // match por id es único
          case "name":
            predicate.and(qProductFamily.name.likeIgnoreCase("%" + stringValue + "%"));
            break;
          default:
            break;
        }
      } catch (NumberFormatException exc) {
        LOG.warn("Param '{}' couldn't be parsed as number (value: '{}')", paramName, stringValue, exc);
      }
    }

    return predicate;
  }
}
