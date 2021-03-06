package org.trebol.api.controllers.data;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.trebol.api.GenericCrudController;
import org.trebol.api.pojo.PersonPojo;
import org.trebol.config.CustomProperties;
import org.trebol.jpa.entities.Person;
import org.trebol.jpa.services.GenericCrudService;

/**
 * API point of entry for Person entities
 *
 * @author Benjamin La Madrid <bg.lamadrid at gmail.com>
 */
@RestController
@RequestMapping("/data/people")
public class PeopleDataController
    extends GenericCrudController<PersonPojo, Person, Integer> {

  @Autowired
  public PeopleDataController(CustomProperties globals,
      GenericCrudService<PersonPojo, Person, Integer> crudService) {
    super(globals, crudService);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('people:read')")
  public Collection<PersonPojo> readMany(@RequestParam Map<String, String> allRequestParams) {
    return super.readMany(null, null, allRequestParams);
  }
}
