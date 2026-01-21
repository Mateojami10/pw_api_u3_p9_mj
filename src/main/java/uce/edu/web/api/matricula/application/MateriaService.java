package uce.edu.web.api.matricula.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import uce.edu.web.api.matricula.domain.Materia;
import uce.edu.web.api.matricula.infraestructure.MateriaRepository;

@ApplicationScoped
public class MateriaService {
    @Inject
    private MateriaRepository materiaRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public List<Materia> listarTodos(){
        return this.materiaRepository.listAll();
    }

    public Materia consultarPorId(Integer id){
        return this.materiaRepository.findById(id.longValue());
    }

    @Transactional
    public void crear(Materia mat){
        // Lógica para crear una nueva materia
        this.materiaRepository.persist(mat);
    }

    @Transactional
    public void actualizar(Integer id, Materia mat){
        Materia mate = this.consultarPorId(id);
        mate.nombre = mat.nombre;
        mate.codigo = mat.codigo;
        mate.creditos = mat.creditos;
        mate.horasSemanales = mat.horasSemanales;
        //se actualiza automaticamente por dirty checking
    }

    @Transactional
    public void actualizarParcial(Integer id, Materia mat){
        Materia mate = this.consultarPorId(id);
        if(mat.nombre != null){
            mate.nombre = mat.nombre;
        }
        if(mat.codigo != null){
            mate.codigo =  mat.codigo;
        }
        if(mat.creditos != null){
            mate.creditos = mat.creditos;
        }
        if(mat.horasSemanales != null){
            mate.horasSemanales = mat.horasSemanales;
        }

    }

    @Transactional
    public void eliminar(Integer id){
        this.materiaRepository.deleteById(id.longValue());
    }

    //Endpoints adicionales

    public List<Materia> buscarPorNombre(String nombre) {
        return entityManager
            .createQuery(
                "SELECT m FROM Materia m WHERE m.nombre LIKE :nombre",
                Materia.class
            )
            .setParameter("nombre", "%" + nombre + "%")
            .getResultList();
    }
    
    public Materia buscarPorCodigo(String codigo){
        TypedQuery<Materia> query = entityManager.createQuery(
            "SELECT m FROM Materia m WHERE m.codigo = :codigo", Materia.class);
        query.setParameter("codigo", codigo);
        List<Materia> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
    
    
}
