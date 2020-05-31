package com.hhu.bbs.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hhu.bbs.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 *  验证 Service
 * @name  AuthenticationServiceImpl
 * @author  nlby
 * @date  2020/5/14
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    /**
     * 根据id 和密码生成token
     * @param id
     * @param password
     * @return
     */
    public String getToken(BigInteger id, String password) {
        String token = "";
        try {
            token = JWT.create()
                    .withAudience(id.toString())          // 将 user id 保存到 token 里面
                    .sign(Algorithm.HMAC256(password));   // 以 password 作为 token 的密钥
        } catch (UnsupportedEncodingException ignore) {
        }
        return token;
    }
}
