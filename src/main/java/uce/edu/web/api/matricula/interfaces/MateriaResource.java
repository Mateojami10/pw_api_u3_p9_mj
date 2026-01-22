package uce.edu.web.api.matricula.interfaces;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import uce.edu.web.api.matricula.application.MateriaService;
import uce.edu.web.api.matricula.domain.Materia;

@Path("/materias")
public class MateriaResource {

    @Inject
    private MateriaService materiaService;

    @GET
    @Path("")
    public List<Materia> listarTodos(){
        return this.materiaService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Materia consultarPorId(@PathParam("id") Integer id){
        return this.materiaService.consultarPorId(id);
    }

    @POST
    @Path("")
    public void guardar(Materia mat){
        this.materiaService.crear(mat);
    }

    @PUT
    @Path("/{id}")
    public void actualizar(@PathParam("id") Integer id, Materia mat){
        this.materiaService.actualizar(id, mat);
    }

    @PATCH
    @Path("/{id}")
    public void actualizarParcial(@PathParam("id") Integer id, Materia mat){
        this.materiaService.actualizarParcial(id, mat);
    }

    @DELETE
    @Path("/{id}")
    public void borrar(@PathParam("id") Integer id){
        this.materiaService.eliminar(id);
    }

    // Endpoints adicionales, sin Panache (usando EntityManager)
    @GET
    @Path("/nombre")
    public List<Materia> buscarPorNombre(@QueryParam("nombre") String nombre){
        return this.materiaService.buscarPorNombre(nombre);
    }

    @GET
    @Path("/codigo")
    public Materia buscarPorCodigo(@PathParam("codigo") String codigo){
        return this.materiaService.buscarPorCodigo(codigo);
    }
    

}
