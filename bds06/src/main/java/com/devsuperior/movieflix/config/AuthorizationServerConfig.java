package com.devsuperior.movieflix.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.devsuperior.movieflix.components.JwtTokenEnhancer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    //aula 03-24 0509
    @Value("${security.oauth2.client.client-id}") //aula 03-24 variável definida na classe application.properties
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    //'jwt.duration' is an unknown property.    
    //@Value("${jwt.duration}") //06m14s //
    //private Integer jwtDuration;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private JwtTokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    //aula 0325 07m38s
    @Autowired
    private JwtTokenEnhancer tokenEnhancer;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
        .withClient(clientId) //06m22s "dscatalog"
        .secret(passwordEncoder.encode(clientSecret)) //"dscatalog123"
        .scopes("read","write")
        .authorizedGrantTypes("password","refresh_token")
        .accessTokenValiditySeconds(86400) //24h = 86400segundos
        //.accessTokenValiditySeconds(jwtDuration);
        .refreshTokenValiditySeconds(86400);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        //aula 0325 08m21s
        TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Arrays.asList(accessTokenConverter, tokenEnhancer));

        endpoints.authenticationManager(authenticationManager)
        .tokenStore(tokenStore)
        .accessTokenConverter(accessTokenConverter)
        .tokenEnhancer(chain) //aula 0325 09m17s
        .userDetailsService(userDetailsService);
    }
    
    
}
