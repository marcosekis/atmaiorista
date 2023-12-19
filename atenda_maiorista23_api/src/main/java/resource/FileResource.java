package resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;


@Path("/file")
public class FileResource {

    private final String UPLOADED_FILE_PATH = "c:\\ficheros\\rest\\";
    
    @POST
    @Path("/upload")
    @Consumes("multipart/form-data")
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadFile(MultipartFormDataInput input) {

        String fileName = "", fileNameDest="", fileNamePathDest="";
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadedFile");

        for (InputPart inputPart : inputParts) {
         try {
            MultivaluedMap<String, String> header = inputPart.getHeaders();
            fileName = getFileName(header);
            //convert the uploaded file to inputstream
            InputStream inputStream = inputPart.getBody(InputStream.class,null);
            byte [] bytes = IOUtils.toByteArray(inputStream);
            //construimos o nome do ficheiro destino
            fileNameDest = 
            	fileName+"__"+UUID.randomUUID().toString()+fileName.substring(fileName.indexOf('.'));
            fileNamePathDest = UPLOADED_FILE_PATH + fileNameDest;
            writeFile(bytes,fileNamePathDest);

          } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
          }

        }
        return Response
            .ok(fileNameDest).status(Status.OK).build();
    }
    
    /**
     * exemplo de cabeceira
     * {
     * 	Content-Type=[image/png], 
     * 	Content-Disposition=[form-data; name="uploadedFile"; filename="filename.extension"]
     * }
     **/
    //obten o  nome do ficheiro
    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    //grabación do ficheiro
    private void writeFile(byte[] content, String filename) throws IOException {

        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
    }
    
    
    
    
    
    
    
    
    @GET
    @Path("/download/{fileName}")
    @Produces({"application/pdf", "text/plain", "image/jpeg" })
    public Response getFileInTextFormat(@PathParam("fileName") String fileName) 
    {
        System.out.println("File requested is : " + fileName);
         
        //Validacións para nome de ficheiro valeiro ou null
        if(fileName == null || fileName.isEmpty())
        {
            ResponseBuilder response = Response.status(Status.BAD_REQUEST);
            return response.build();
        }
         
        //Prepare a file object with file to return
        File file = new File(UPLOADED_FILE_PATH+fileName);
        
        // indicaremos ao cliente o tipo de contido para que o abra sen problemas
        String fileType = fileName.substring(fileName.lastIndexOf('.'));
        
        ResponseBuilder response = Response.ok((Object) file);
        
        response.type(MediaType.WILDCARD_TYPE);
        // MediaType.WILDCARD_TYPE configura a cabeceria Content-Type="*/*" e o navegador decide 
        // como abrir cada arquivo
        response.header("Content-Disposition", "inline; filename=\""+fileName+"\"");
        // inline:  abre ficheiro con plugin de navegador
        // attachment: descarga ficheiro
        return response.build();
    }
    
}
