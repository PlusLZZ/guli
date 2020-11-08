package com.qtechweb.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * token管理
 * </p>
 *
 * @author lzz
 * @since 2020-10-08
 */
@Component
public class TokenManager {

    //默认过期时间 1天
    private long tokenExpiration = 24 * 60 * 60 * 1000;
    // token签名 key
    private String tokenSignKey = "guliSecurity";

    /*
     * 通过用户名返回token
     *  设置默认token过期时间为一天
     * 使用GZIP压缩算法
     * */
    public String createToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    /*
    解析token返回用户名
    * */
    public String getUserFromToken(String token) {
        String user = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return user;
    }

    public void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }

}
