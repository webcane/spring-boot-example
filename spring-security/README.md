# Filters
```http.csrf().disable().authorizeRequests().antMatchers("/", "/favicon.ico").permitAll().anyRequest().authenticated()
.and().exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
.and().addFilter(new JwtAuthenticationFilter(authenticationManager()))
.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
```

=======================
0. characterEncodingFilter, filterClass=org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter
1. formContentFilter, filterClass=org.springframework.boot.web.servlet.filter.OrderedFormContentFilter
2. requestContextFilter, filterClass=org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter
3. springSecurityFilterChain, filterClass=org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean$1
	0. WebAsyncManagerIntegrationFilter
	1. SecurityContextPersistenceFilter
	2. HeaderWriterFilter 
	3. LogoutFilter 
	4. JwtAuthenticationFilter
	5. RequestCacheAwareFilter
	6. SecurityContextHolderAwareRequestFilter
	7. AnonymousAuthenticationFilter 
	8. SessionManagementFilter
	9. ExceptionTranslationFilter
	10. FilterSecurityInterceptor
4. Tomcat WebSocket (JSR356) Filter, filterClass=org.apache.tomcat.websocket.server.WsFilter
