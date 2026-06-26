package tech.config.springdoc;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "security_authorization",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                authorizationUrl = "${sistema.springdoc-auth-flow-authorization-url}",
                tokenUrl = "${sistema.springdoc-auth-flow-token-url}",
                scopes = {
                        @OAuthScope(name = "openid", description = "OPENID")
                }
        )))
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TECH API")
                        .version("1.0")
                        .description("TECH API - ROTAS")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.com")
                        )

                );
    }
}
