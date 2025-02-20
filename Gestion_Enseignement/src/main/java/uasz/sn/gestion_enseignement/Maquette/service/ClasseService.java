package uasz.sn.gestion_enseignement.Maquette.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.gestion_enseignement.Maquette.model.Classe;
import uasz.sn.gestion_enseignement.Maquette.model.Formation;
import uasz.sn.gestion_enseignement.Maquette.model.Maquette;
import uasz.sn.gestion_enseignement.Maquette.model.UE;
import uasz.sn.gestion_enseignement.Maquette.repository.ClasseRepository;

import java.util.List;

@Service
@Transactional
public class ClasseService {
    @Autowired
    private ClasseRepository classeRepository;

    public void addClasse(Classe classe) {
        classeRepository.save(classe);
    }

    public List<Classe> findAllClasse() {
        return classeRepository.findAll();
    }

    public Classe getClasse(Long id) {
        return classeRepository.findById(id).get();
    }

    public void updateClasse(Classe ue) {
        classeRepository.save(ue);
    }

    public void deleteClasse(Long id) {
        classeRepository.deleteById(id);
    }
    public List<Classe> findClasseByFormation(Formation formation) {

        return classeRepository.findByFormation(formation);
    }


    public Classe findById(Long id) {
        return classeRepository.findById(id).get();
    }
}
