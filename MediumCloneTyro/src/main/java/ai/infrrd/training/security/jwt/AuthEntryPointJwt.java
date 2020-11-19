package ai.infrrd.training.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.infrrd.training.payload.response.ErrorResponse;
import ai.infrrd.training.payload.response.MessageResponse;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("error: {}", new MessageResponse(authException.getMessage()));
	    String json = new ObjectMapper().writeValueAsString(new ErrorResponse(new MessageResponse(authException.getMessage())));
	    response.getWriter().write(json);
	}

}
