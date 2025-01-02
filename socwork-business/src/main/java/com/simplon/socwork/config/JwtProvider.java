package com.simplon.socwork.config;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.simplon.socwork.entities.Role;

public class JwtProvider {

    private final Algorithm algorithm;
    
    private final Optional<Long> exp;
    
    private final String issuer;
    
    JwtProvider(Algorithm algorithm, Optional<Long> exp, String issuer) {
    	this.algorithm = algorithm;
    	this.exp = exp;
    	this.issuer = issuer;
    }

    public String create(String subject, Set<Role> roles) {
        
        Instant issuedAt = Instant.now();
        
//        List<Role> roleList = new ArrayList<Role>();
//        roleList.addAll(roles);
        
        Builder builder = JWT.create()
        		.withIssuedAt(issuedAt)
				.withSubject(subject)
				.withIssuer(issuer)
				.withClaim("roles", roles.stream().map(role -> role.getCode()).toList());
        
        exp.ifPresent(expires -> 
            builder.withExpiresAt(Instant.ofEpochSecond(expires))
            //alt: issuedAt.plusSeconds(expires);
        );
        return builder.sign(algorithm);
    }

}