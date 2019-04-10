package com.cecula.util;

import com.cecula.ejb.UsersBean;
import com.cecula.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.mindrot.jbcrypt.BCrypt;

/*
 *
 * @author Segun Ogundipe <segun.ogundipe at cecula.com>
 */
public class AuthenticationUtility {
    
    @EJB
    UsersBean usersBean;

    private static final String ISSUER = "cecula.com";
    private static final String AUDIENCE = "devapp.com";
    private static final String SECRET = "secretkey";
    private static final ZonedDateTime NOW = ZonedDateTime.now();
    private static final ZonedDateTime EXP_DATE = NOW.plusHours(1);

    public static String issueToken(Users user) {
        return Jwts.builder()
                .setId(user.getId().toString())
                .setIssuer(ISSUER)
                .setAudience(AUDIENCE)
                .setSubject(user.getUsername())
                .setIssuedAt(Date.from(NOW.toInstant()))
                .setExpiration(Date.from(EXP_DATE.toInstant()))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
    
    public Users getUser(String token){
        Users user = usersBean.find(parseToken(token).getId());
        
        return user;
    }
    
    private Claims parseToken(String token) {

        try {

            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .requireAudience(AUDIENCE)
                    .parseClaimsJws(token)
                    .getBody();
            return claims;

        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Invalid token", e);
        } catch (ExpiredJwtException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Expired token", e);
        } catch (InvalidClaimException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Invalid value for claim \"" + e.getClaimName() + "\"", e);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Invalid token", e);
        }
        return null;
    }
    
    public static String encryptPassword(String password){
        String pass = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(pass);
        return pass;
    }
    
    public static boolean checkPassword(String candidate, String hashValue){
        return BCrypt.checkpw(candidate, hashValue);
    }

}
