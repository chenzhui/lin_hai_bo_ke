package com.example.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
    private String privateKey = "fdasfgdsagaxgsregdfdjyghjfhebfdgwe45ygrfbsdfshfdsag";

    public String GetToken(int id, String name, String avatar) {
        String token = JWT.create().withClaim("id", "" + id).withClaim("name", name).withClaim("avatar", avatar).withClaim("time", Long.valueOf(System.currentTimeMillis())).sign(Algorithm.HMAC256(this.privateKey));
        return token;
    }

    public Map<String, String> parseToken(String token) {
        HashMap<String, String> map = new HashMap<>();
        DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256(this.privateKey)).build().verify(token);
        Claim id = decodedjwt.getClaim("id");
        Claim name = decodedjwt.getClaim("name");
        Claim avatar = decodedjwt.getClaim("avatar");
        Claim time = decodedjwt.getClaim("time");
        map.put("id", id.asString());
        map.put("time", time.asLong().toString());
        map.put("name", name.asString());
        map.put("avatar", avatar.asString());
        return map;
    }
}
