package org.trebol.jpa.services.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import org.trebol.jpa.entities.QProductType;

import org.trebol.api.pojo.ProductTypePojo;
import org.trebol.jpa.entities.ProductType;
import org.trebol.jpa.repositories.ProductTypesRepository;
import org.trebol.jpa.services.GenericCrudService;

/**
 *
 * @author Benjamin La Madrid <bg.lamadrid at gmail.com>
 */
@Transactional
@Service
public class ProductTypeCrudServiceImpl
    extends GenericCrudService<ProductTypePojo, ProductType, Integer> {
  private static final Logger LOG = LoggerFactory.getLogger(ProductTypeCrudServiceImpl.class);

  private final ProductTypesRepository repository;
  private final ConversionService conversion;

  @Autowired
  public ProductTypeCrudServiceImpl(ProductTypesRepository repository, ConversionService conversion) {
    super(repository);
    this.repository = repository;
    this.conversion = conversion;
  }

  @Override
  public ProductTypePojo entity2Pojo(ProductType source) {
    return conversion.convert(source, ProductTypePojo.class);
  }

  @Override
  public ProductType pojo2Entity(ProductTypePojo source) {
    return conversion.convert(source, ProductType.class);
  }

  @Override
  public Page<ProductType> getAllEntities(Pageable paged, Predicate filters) {
    if (filters == null) {
      return repository.deepReadAll(paged);
    } else {
      return repository.findAll(filters, paged);
    }
  }

  @Override
  public Predicate queryParamsMapToPredicate(Map<String, String> queryParamsMap) {
    QProductType qProductType = QProductType.productType;
    BooleanBuilder predicate = new BooleanBuilder();
    for (String paramName : queryParamsMap.keySet()) {
      String stringValue = queryParamsMap.get(paramName);
      try {
        Integer intValue;
        switch (paramName) {
          case "id":
            intValue = Integer.valueOf(stringValue);
            return predicate.and(qProductType.id.eq(intValue)); // match por id es único
          case "name":
            predicate.and(qProductType.name.likeIgnoreCase("%" + stringValue + "%"));
            break;
          case "productFamily":
            intValue = Integer.valueOf(stringValue);
            predicate.and(qProductType.productFamily.id.eq(intValue));
            break;
          default:
            break;
        }
      } catch (NumberFormatException exc) {
        LOG.error("Param '{}' couldn't be parsed as number (value: '{}')", paramName, stringValue, exc);
      }
    }

    return predicate;
  }
}
