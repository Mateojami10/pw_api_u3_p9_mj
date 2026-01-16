package uce.edu.web.api.matricula.interfaces;
import jakarta.ws.rs.Path;
import jakarta.inject.Inject;
import uce.edu.web.api.matricula.application.EstudianteService;
import uce.edu.web.api.matricula.domain.Estudiante;
import java.util.List;
import jakarta.ws.rs.GET;

@Path("/estudiantes")
public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;

    @GET
    @Path("/todos")
    public List<Estudiante> listarTodos(){
        return this.estudianteService.listarTodos();
    }

}
