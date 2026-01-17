package uce.edu.web.api.matricula.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.matricula.domain.Estudiante;
import uce.edu.web.api.matricula.infraestructure.EstudianteRepository;

@ApplicationScoped
public class EstudianteService {

    @Inject
    private EstudianteRepository estudianteRepository;

    public List<Estudiante> listarTodos(){
        return this.estudianteRepository.listAll();
    }

    public Estudiante consultarPorId(Integer id){
        return this.estudianteRepository.findById(id.longValue());
    }

    @Transactional
    public void crear(Estudiante estu){
        // Lógica para crear un nuevo estudiante
        this.estudianteRepository.persist(estu);
    }

    @Transactional
    public void actualizar(Integer id, Estudiante est){
        Estudiante estu = this.consultarPorId(id);
        estu.apellido = est.apellido;
        estu.nombre = est.nombre;
        estu.fechaNacimiento = est.fechaNacimiento;
        //se actualiza automaticamente por dirty checking
    }

    @Transactional
    public void actualizarParcial(Integer id, Estudiante est){
        Estudiante estu = this.consultarPorId(id);
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

    


}
