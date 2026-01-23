package uce.edu.web.api.matricula.interfaces;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import uce.edu.web.api.matricula.application.EstudianteService;
import uce.edu.web.api.matricula.domain.Estudiante;

@Path("/estudiantes")
public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;

    @GET
    @Path("")
    public List<Estudiante> listarTodos(){
        System.out.println("Listando todos los estudiante XXXXXXXXXXXXXXXXXXXXXXX");
        return this.estudianteService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Estudiante consultarPorId(@PathParam("id") Integer id){
        return this.estudianteService.consultarPorId(id);
    }

    @POST
    @Path("")
    public Response guardar(Estudiante estu){
        this.estudianteService.crear(estu);
        return Response.status(Response.Status.CREATED).entity(estu).build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, Estudiante estu){
        this.estudianteService.actualizar(id, estu);
        return Response.status(209).entity(null).build();
    }

    @PATCH
    @Path("/{id}")
    public void actualizarParcial(@PathParam("id") Integer id, Estudiante estu){
        this.estudianteService.actualizarParcial(id, estu);
    }

    @DELETE
    @Path("/{id}")
    public void borrar(@PathParam("id") Integer id){
        this.estudianteService.eliminar(id);
    }

    @GET
    @Path("/provincia/genero") //se agrega /provincia para diferenciar la ruta del listar todos
    public List<Estudiante> buscarPorProvincia(@QueryParam("provincia") String provincia, @QueryParam("genero") String genero){
        System.out.println("Buscando estudiantes en provincia y gener XXXXXXXXXXXXXXX");
        return this.estudianteService.buscarPorProvincia(provincia, genero);
    }

}
