package uce.edu.web.api.matricula.application;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.matricula.application.representation.EstudianteRepresentation;
import uce.edu.web.api.matricula.domain.Estudiante;
import uce.edu.web.api.matricula.infraestructure.EstudianteRepository;

@ApplicationScoped
public class EstudianteService {

    @Inject
    private EstudianteRepository estudianteRepository;

    public List<EstudianteRepresentation> listarTodos(){
        List<EstudianteRepresentation> list = new ArrayList<>();
        for(Estudiante estu : this.estudianteRepository.listAll()){
            list.add(this.mappertoER(estu));
        }

        return list;
    }

    public EstudianteRepresentation consultarPorId(Integer id){
        return this.mappertoER(this.estudianteRepository.findById(id.longValue()));
    }

    @Transactional
    public void crear(EstudianteRepresentation estu){
        // Lógica para crear un nuevo estudiante
        this.estudianteRepository.persist(this.mappertoEstudiante(estu));
    }

    @Transactional
    public void actualizar(Integer id, EstudianteRepresentation est){
        Estudiante estu = this.estudianteRepository.findById(id.longValue());
        estu.apellido = est.apellido;
        estu.nombre = est.nombre;
        estu.fechaNacimiento = est.fechaNacimiento;
        //se actualiza automaticamente por dirty checking
    }

    @Transactional
    public void actualizarParcial(Integer id, EstudianteRepresentation est){
        Estudiante estu = this.mappertoEstudiante(this.consultarPorId(id)) ;
        if(est.apellido != null){
            estu.apellido = est.apellido;
        }
        if(est.nombre != null){
            estu.nombre =  est.nombre;
        }
        if(est.fechaNacimiento != null){
            estu.fechaNacimiento = est.fechaNacimiento;
        }

    }

    @Transactional
    public void eliminar(Integer id){
        this.estudianteRepository.deleteById(id.longValue());
    }

    public List<EstudianteRepresentation> buscarPorProvincia(String provincia, String genero) {
        List<EstudianteRepresentation> list = new ArrayList<>();
        for(Estudiante estu : this.estudianteRepository.find("provincia = ?1 and genero = ?2", provincia, genero).list()){
            list.add(this.mappertoER(estu));
        }
        return list;
    }

    

    private EstudianteRepresentation mappertoER(Estudiante est){
        EstudianteRepresentation estuR = new EstudianteRepresentation();
        estuR.id = est.id;
        estuR.nombre = est.nombre;
        estuR.apellido = est.apellido;
        estuR.fechaNacimiento = est.fechaNacimiento;
        estuR.provincia = est.provincia;
        estuR.genero = est.genero;
        return estuR;
    }

    private Estudiante mappertoEstudiante(EstudianteRepresentation est){
        Estudiante estuR = new Estudiante();
        estuR.id = est.id;
        estuR.nombre = est.nombre;
        estuR.apellido = est.apellido;
        estuR.fechaNacimiento = est.fechaNacimiento;
        estuR.provincia = est.provincia;
        estuR.genero = est.genero;
        return estuR;
    }
    

    


}
