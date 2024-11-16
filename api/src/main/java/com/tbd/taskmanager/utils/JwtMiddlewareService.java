package com.tbd.taskmanager.utils;

import com.tbd.taskmanager.models.UsersModel;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Repository;

import javax.crypto.SecretKey;
import java.util.Date;

@Repository
public class JwtMiddlewareService{

    private final SecretKey secretKey;

    public JwtMiddlewareService(SecretKey secretKey){
        this.secretKey = secretKey;
    }

    public String generateToken(UsersModel userModel){
        Date expiration_date = new Date(System.currentTimeMillis() + 86400000);
        return Jwts
                .builder()
                .claim("user_id", userModel.getUser_id())
                .claim("name", userModel.getName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiration_date)
                .signWith(secretKey)
                .compact();
    }


    public Boolean validateToken(String token){
        try{
            Jws<Claims> jws = Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return !jws.getPayload().getExpiration().before(new Date());
        }catch(ExpiredJwtException e){
            e.printStackTrace();
            return false;
        }catch(MalformedJwtException e){
            e.printStackTrace();
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public UsersModel decodeJWT(String token){
        Claims claims = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Long id = claims.get("user_id", Long.class);
        String username = claims.get("name", String.class);

        UsersModel userModel = new UsersModel();
        userModel.setUser_id(id.intValue());
        userModel.setName(username);
        return userModel;
    }
}