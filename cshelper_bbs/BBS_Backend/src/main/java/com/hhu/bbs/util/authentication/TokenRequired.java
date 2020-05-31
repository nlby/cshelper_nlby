package com.hhu.bbs.util.authentication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  在需要登录验证的Controller的方法上使用此注解
 * @name  TokenRequired
 * @author  nlby
 * @date  2020/5/14
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenRequired {
}
