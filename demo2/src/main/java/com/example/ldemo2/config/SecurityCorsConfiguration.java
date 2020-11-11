package com.example.ldemo2.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 跨域设置
 * @projectName:
 * @package:        cn.rayeye.micro.smoke.config
 * @ClassName:      SecurityCorsConfiguration
 * @description:    跨域设置
 * @author:         Neil.Zhou
 * @createDate:     2018-12-25 15:38
 * @UpdateUser:     Neil.Zhou
 * @updateDate:     2018-12-25 15:38
 * @updateRemark:   The modified content
 * @version:        1.0
 * <p>Copyright: Copyright (c) 2018-12-25</p>
 *
 */
@Configuration
public class SecurityCorsConfiguration {

    @SuppressWarnings("unchecked")
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList(CorsConfiguration.ALL));
        corsConfiguration.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
        corsConfiguration.setAllowedMethods(Arrays.asList(CorsConfiguration.ALL));
        source.registerCorsConfiguration("/**", corsConfiguration);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
