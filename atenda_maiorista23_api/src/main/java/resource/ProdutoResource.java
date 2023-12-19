package resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import atendaservices.exceptions.DataException;
import atendaservices.model.Produto;
import atendaservices.service.impl.ProdutoServiceImpl;

@Path("/produtos") // encadearase despois do path da ApplicationClass
//@Path("secured/produtos")
public class ProdutoResource {
	private ProdutoServiceImpl produtoService = new ProdutoServiceImpl();
	
		@GET
		//@RolesAllowed({"BASIC", "ADMIN"})
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getProdutos() {
			
			ArrayList < Produto > produtos=null;
	       try {
	    	  produtos = produtoService.findAll();
		        
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       if (!produtos.isEmpty()) {
	            return Response.ok(produtos).build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND).build();
	        }
	    }
	 
	    @Path("/{idProduto}")
	    @GET
	    //@RolesAllowed({"BASIC", "ADMIN"})
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getProductById(@PathParam("idProduto") Long idProduto) {
	       try {
	    	   Produto produto = produtoService.findById(idProduto);
		        if (produto != null) {
		            return Response.ok(produto).build();
		        } else {
		            return Response.status(Response.Status.NOT_FOUND).build();
		        }   
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	    }
	    
	    @POST
	    //@RolesAllowed({"ADMIN"})
	    @Produces(MediaType.APPLICATION_JSON)
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response createProduct(Produto produto) { 	
	    	try {
	    		produtoService.create(produto);
	                // devolvemos o obxecto creado (serializado a JSON por RESTEasy)
	    		return Response.ok(produto).status(Response.Status.CREATED).build();
	    	}catch (Exception e) {
	    		return Response.notModified().build();
	    	}
	    }
	    
	    @PUT
	    //@RolesAllowed({"ADMIN"})
	    @Path("/{idProduto}")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response updateProduct(@PathParam("idProduto") Long idProduto, Produto produto) {
	    	produto.setId(idProduto);
	    	try {
	    		produtoService.update(produto);
	    		return Response.ok().status(Response.Status.NO_CONTENT).build();
	    	}catch (Exception e) {
	    		return Response.notModified().build();
	    	}	
	    }
	    
	    @Path("/{codigo}")
	    @DELETE
	    //@RolesAllowed({"ADMIN"})
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteProduct(@PathParam("codigo") Long idProduto) {
	        try {
	        	boolean result = produtoService.softDelete(idProduto);
	 	        if (result) {
	 	            return Response.ok().status(Response.Status.NO_CONTENT).build();
	 	        } else {
	 	            return Response.notModified().build();
	 	        }
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

	    }
}
