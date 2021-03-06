package org.trebol.security.services.impl;

import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import org.trebol.config.SecurityProperties;
import org.trebol.security.services.AuthorizationHeaderParserService;

@Service
public class ClaimsAuthorizationHeaderParserServiceImpl
    implements AuthorizationHeaderParserService<Claims> {

  private final SecretKey secretKey;
  private final SecurityProperties jwtProperties;

  @Autowired
  public ClaimsAuthorizationHeaderParserServiceImpl(SecretKey secretKey, SecurityProperties jwtProperties) {
    this.secretKey = secretKey;
    this.jwtProperties = jwtProperties;
  }

  @Override
  public String extractAuthorizationHeaderFromRequest(HttpServletRequest request) {
    return request.getHeader(jwtProperties.getAuthorizationHeader());
  }

  @Override
  public Claims parseToken(String authorizationHeader) throws IllegalStateException {
    String token = authorizationHeader.replace(jwtProperties.getJwtTokenPrefix(), "");
    try {
      Jws<Claims> claimsJws = Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token);

      Claims body = claimsJws.getBody();
      return body;
    } catch (JwtException e) {
      throw new IllegalStateException(String.format("Token %s can't be trusted", token));
    }
  }

  @Nullable
  @Override
  public String extractAuthorizationHeader(HttpHeaders httpHeaders) {
    String authHeaderKey = jwtProperties.getAuthorizationHeader();
    if (httpHeaders.containsKey(authHeaderKey)) {
      String value = httpHeaders.getFirst(authHeaderKey);
      return value;
    } else {
      return null;
    }
  }

}
