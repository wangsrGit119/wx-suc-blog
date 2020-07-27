package jwt;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.logging.Logger;

/**
 *  JWT
 * @author WJL
 */
public class JwtUtils {
    private static final String SUBJECT = "chatWJL";
    private static Long EXPIRE = 7 * 24 * 60 * 60 * 1000L;
    private static String SECRET = "chat1215618342";
    private static Logger logger = Logger.getLogger("my.jwt.utils");

    /**
     * jwt 生成
     * @param userName
     * @param avatarUrl
     * @return
     */
    public static  String generateJwt(String userName,String avatarUrl){
        Long now = System.currentTimeMillis();
        String token = Jwts.builder()
                //主题
                .setSubject(SUBJECT)
                //body
                .claim("name",userName)
                .claim("url",avatarUrl)
                //签发者
                .setIssuer("wjl")
                //签发时间
                .setIssuedAt(new Date(now))
                //过期时间
                .setExpiration(new Date(now+EXPIRE))
                //签名算法及其密钥
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .compact();
        return token;
    }

    /**
     * jwt 校验
     * @param token
     * @return
     */
    public static Claims validateJwt(String token){
        try {
            Claims claims= Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (ExpiredJwtException e) {
            logger.info("ExpiredJwtException  :"+e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.info("UnsupportedJwtException    :"+e.getMessage());
        } catch (MalformedJwtException e) {
            logger.info("MalformedJwtException      :"+e.getMessage());
        } catch (SignatureException e) {
            logger.info("SignatureException         :"+e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.info("IllegalArgumentException  :"+e.getMessage());
        }
       return null;
    }

}
