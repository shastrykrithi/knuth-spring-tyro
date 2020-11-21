package ai.infrrd.training.config;

import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String DEFAULT_INCLUDE_PATTERN = "/v1/.*";
	public final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("ai.infrrd.training")).paths(PathSelectors.ant("/v1/**"))
				.build().apiInfo(info()).securitySchemes(Arrays.asList(apiKey()));
	}

	private ApiInfo info() {
		return new ApiInfo("TYRO Application", "A Medium clone application", "V1", "Terms Of Service",
				new Contact("Team Knuth", "https://tyro-team-knuth.herokuapp.com/", "team-knuth@tyro.com"),
				"Campus.Build License", "campus.build.ai", Collections.emptyList());
	}

	private ApiKey apiKey() {
		return new ApiKey("jwtToken", "Authorization", "header");
	}
}
