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

import org.trebol.jpa.entities.QSellType;

import org.trebol.api.pojo.SellTypePojo;
import org.trebol.jpa.entities.SellType;
import org.trebol.jpa.repositories.SellTypesRepository;
import org.trebol.jpa.services.GenericCrudService;

/**
 *
 * @author Benjamin La Madrid <bg.lamadrid at gmail.com>
 */
@Transactional
@Service
public class SellTypeCrudServiceImpl
    extends GenericCrudService<SellTypePojo, SellType, Integer> {
  private static final Logger LOG = LoggerFactory.getLogger(SellTypeCrudServiceImpl.class);

  private final ConversionService conversion;

  @Autowired
  public SellTypeCrudServiceImpl(SellTypesRepository sellTypes, ConversionService conversion) {
    super(sellTypes);
    this.conversion = conversion;
  }

  @Override
  public SellTypePojo entity2Pojo(SellType source) {
    return conversion.convert(source, SellTypePojo.class);
  }

  @Override
  public SellType pojo2Entity(SellTypePojo source) {
    return conversion.convert(source, SellType.class);
  }

  @Override
  public Predicate queryParamsMapToPredicate(Map<String, String> queryParamsMap) {
    QSellType qSellType = QSellType.sellType;
    BooleanBuilder predicate = new BooleanBuilder();
    for (String paramName : queryParamsMap.keySet()) {
      String stringValue = queryParamsMap.get(paramName);
      try {
        Integer intValue;
        switch (paramName) {
          case "id":
            intValue = Integer.valueOf(stringValue);
            return predicate.and(qSellType.id.eq(intValue)); // match por id es único
          case "name":
            predicate.and(qSellType.name.likeIgnoreCase("%" + stringValue + "%"));
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
