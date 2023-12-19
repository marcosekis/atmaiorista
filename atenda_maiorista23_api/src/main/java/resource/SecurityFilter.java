package resource;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import atendaservices.dao.UsuarioDAO;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
		
	}

//	private UsuarioDAO userDAO = new UsuarioDAO();
//	
//	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
//	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
//	private static final String SECURED_URL_PREFIX= "secured"; // prefixo no que escoita o recurso
//	@Override
//	public void filter(ContainerRequestContext requestContext) throws IOException {
//		if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
//			List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
//			if (authHeader != null  && authHeader.size() > 0) {
//				String authToken = authHeader.get(0);
//				authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");				
//				String decodedString = new String (Base64.getDecoder().decode(authToken));
//				StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
//				String username = tokenizer.nextToken();
//				String password = tokenizer.nextToken();
//				if (userDAO.autentica(username, password)) {
//                                        // se se autentica ben, pasa o filtro
//					return;
//				}
//			}
//                        // se falla a autenticación, devolverá HTTP 401 Unauthorized
//			Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).build();
//			requestContext.abortWith(unauthorizedStatus);
//		}
//	}
	
}
