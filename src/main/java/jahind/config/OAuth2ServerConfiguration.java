package jahind.config;

import jahind.serviceBeans.UserDetailServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Created by Gaurav on 18/11/15.
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2ServerConfiguration {

    private static final String RESOURCE_ID = "restservice";

    @Configuration
    @EnableResourceServer

    protected static class ResourceServerConfiguration extends
            ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            // @formatter:off
            resources.resourceId(RESOURCE_ID);
            // @formatter:on
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/api/greeting").authenticated();
            http.authorizeRequests().antMatchers("/oauth/token").permitAll();
            //http.authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/oauth/token").fullyAuthenticated();
            // @formatter:off
//            http.authorizeRequests()
//                    .antMatchers("/api/greeting").authenticated();
            // http.authorizeRequests().antMatchers("/oauth/token").permitAll().anyRequest().authenticated();
            //http.httpBasic().and().formLogin().and().authorizeRequests().antMatchers("/oauth/token").permitAll().anyRequest().authenticated();
            //http.antMatcher("/oauth/token").p

            // @formatter:on
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        private TokenStore tokenStore = new InMemoryTokenStore();

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        private UserDetailServiceBean userDetailsService;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            // @formatter:off

            /*endpoints.addInterceptor(new HandlerInterceptorAdapter() {
                @Override
                public boolean preHandle(HttpServletRequest hsr, HttpServletResponse rs, Object o) throws Exception {
                    rs.setHeader("Access-Control-Allow-Origin", "*");
                    rs.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
                    rs.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept," +
                            "Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
                    return true;
                }
            });*/
            endpoints.tokenStore(this.tokenStore)
                    .authenticationManager(this.authenticationManager)
                    .userDetailsService(userDetailsService);
            // @formatter:on
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // @formatter:off
            clients
                    .inMemory()
                    .withClient("clientapp")
                    .authorizedGrantTypes("password", "refresh_token")
                    .authorities("USER")
                    .scopes("read", "write")
                    .resourceIds(RESOURCE_ID)
                    .secret("123456");
            // @formatter:on
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(this.tokenStore);
            return tokenServices;
        }

    }
}
