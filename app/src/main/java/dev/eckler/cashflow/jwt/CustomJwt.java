package dev.eckler.cashflow.jwt;

import dev.eckler.cashflow.config.Oauth2Properties;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class CustomJwt extends JwtAuthenticationToken {

  private String userID;

  private String firstname;

  private String lastname;

  public CustomJwt(Jwt jwt, Collection<? extends GrantedAuthority> authorities) {
    super(jwt, authorities);
  }

  public static String getUserId(String bearerToken, Oauth2Properties oauthProperties) {
    String issuer = oauthProperties.issuerUri();
    String jwtToken = bearerToken.substring(7);
    Jwt jwt = JwtDecoders.fromIssuerLocation(issuer).decode(jwtToken);
    return jwt.getClaimAsString("sub");
  }

  public String getId() {
    return userID;
  }

  public void setId(String id) {
    this.userID = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }
}
