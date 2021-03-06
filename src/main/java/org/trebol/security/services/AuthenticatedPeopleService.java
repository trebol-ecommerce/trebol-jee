package org.trebol.security.services;

import org.trebol.api.pojo.PersonPojo;

/**
 * Service interface that can find a Person profile behind an authenticated user by reading auth information
 *
 * @author Benjamin La Madrid <bg.lamadrid@gmail.com>
 */
public interface AuthenticatedPeopleService {
  PersonPojo fetchAuthenticatedUserPersonProfile(String authorizationHeader);
}
