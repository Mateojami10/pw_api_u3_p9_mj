package uce.edu.web.api.matricula.interfaces;
import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.matricula.application.EstudianteService;
import uce.edu.web.api.matricula.application.HijoService;
import uce.edu.web.api.matricula.application.representation.EstudianteRepresentation;
import uce.edu.web.api.matricula.application.representation.HijoRepresentation;
import uce.edu.web.api.matricula.application.representation.LinkDto;


@Path("/estudiantes")
public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;

    @Inject
    private HijoService hijoService;

    @Context
    private UriInfo uriInfo; //obtengo la url con la que estoy trabajando

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "user", "docente"})
    public List<EstudianteRepresentation> listarTodos(){
        System.out.println("Listando todos los estudiante XXXXXXXXXXXXXXXXXXXXXXX");
         //agregamos el link a cada uno de los elementos de la lista
         List<EstudianteRepresentation> estudiantes = this.estudianteService.listarTodos();
         estudiantes.forEach(this::construirLinks);
        return estudiantes;
       
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    //@PermitAll
    public EstudianteRepresentation consultarPorId(@PathParam("id") Integer id){
        return this.construirLinks(this.estudianteService.consultarPorId(id));
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    public Response guardar(EstudianteRepresentation estu){
        this.estudianteService.crear(estu);
        return Response.status(Response.Status.CREATED).entity(estu).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    public Response actualizar(@PathParam("id") Integer id, EstudianteRepresentation estu){
        this.estudianteService.actualizar(id, estu);
        return Response.status(209).entity(null).build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    public void actualizarParcial(@PathParam("id") Integer id, EstudianteRepresentation estu){
        this.estudianteService.actualizarParcial(id, estu);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"admin"})
    public void borrar(@PathParam("id") Integer id){
        this.estudianteService.eliminar(id);
    }

    @GET
    @Path("/provincia/genero") //se agrega /provincia para diferenciar la ruta del listar todos
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    public List<EstudianteRepresentation> buscarPorProvincia(@QueryParam("provincia") String provincia, @QueryParam("genero") String genero){
        System.out.println("Buscando estudiantes en provincia y gener XXXXXXXXXXXXXXX");
        return this.estudianteService.buscarPorProvincia(provincia, genero);
    }

    //endpoint para acceder a los hijos de un estudiante
    @GET
    @Path("/{id}/hijos")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    public List<HijoRepresentation> buscarPorIdEstudiante(@PathParam("id") Integer id){
        return this.hijoService.buscarPorIdEstudiante(id);
    }

    private EstudianteRepresentation construirLinks(EstudianteRepresentation er){
        String self = this.uriInfo.getBaseUriBuilder().path(EstudianteResource.class).path(String.valueOf(er.id))
                .build().toString();

                String hijos = this.uriInfo.getBaseUriBuilder().path(EstudianteResource.class).path(String.valueOf(er.id)).path("hijos")
                .build().toString();

        er.links = List.of(new LinkDto(self, "self"), new LinkDto(hijos, "hijos"));
        return er;
    }

}
//consultar todos y consultar por Id