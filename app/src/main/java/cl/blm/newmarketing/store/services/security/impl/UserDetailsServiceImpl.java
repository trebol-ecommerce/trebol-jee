package cl.blm.newmarketing.store.services.security.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import cl.blm.newmarketing.store.jpa.entities.Permission;
import cl.blm.newmarketing.store.jpa.entities.QUserRolePermission;
import cl.blm.newmarketing.store.jpa.entities.User;
import cl.blm.newmarketing.store.jpa.entities.UserRole;
import cl.blm.newmarketing.store.jpa.entities.UserRolePermission;
import cl.blm.newmarketing.store.jpa.repositories.UserRolePermissionsRepository;
import cl.blm.newmarketing.store.jpa.repositories.UsersRepository;
import cl.blm.newmarketing.store.security.pojo.GranthedAuthorityPojo;
import cl.blm.newmarketing.store.security.pojo.UserDetailsPojo;

@Service
public class UserDetailsServiceImpl
    implements UserDetailsService {

  @Autowired
  private ConversionService conversionService;

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private UserRolePermissionsRepository userRolePermissionsRepository;

  private Collection<Permission> getAllUserRolePermissions(User source) {
    UserRole sourceUserRole = source.getUserRole();
    Predicate sourceUserRolePermissions = QUserRolePermission.userRolePermission.userRole.eq(sourceUserRole);

    List<Permission> targetList = new ArrayList<>();
    for (UserRolePermission rolePermission : userRolePermissionsRepository.findAll(sourceUserRolePermissions)) {
      Permission p = rolePermission.getPermission();
      targetList.add(p);
    }

    return targetList;
  }

  private List<GranthedAuthorityPojo> convertPermissionList(Collection<Permission> sourceList) {
    List<GranthedAuthorityPojo> targetList = new ArrayList<>();
    for (Permission source : sourceList) {
      GranthedAuthorityPojo target = conversionService.convert(source, GranthedAuthorityPojo.class);
      targetList.add(target);
    }
    return targetList;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> foundUser = usersRepository.findByName(username);
    if (foundUser.isPresent()) {
      User user = foundUser.get();
      Collection<Permission> permissions = getAllUserRolePermissions(user);
      List<GranthedAuthorityPojo> authorities = convertPermissionList(permissions);
      UserDetailsPojo userDetails = new UserDetailsPojo(authorities, username, null, false, false, false, true);
      return userDetails;
    } else {
      throw new UsernameNotFoundException(username);
    }
  }

}