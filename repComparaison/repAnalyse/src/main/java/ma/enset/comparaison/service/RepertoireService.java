package ma.enset.comparaison.service;

import ma.enset.comparaison.entities.Repertoire;
import ma.enset.comparaison.repository.RepertoireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepertoireService {
    @Autowired
    private RepertoireRepository repertoireRepository;

    public Repertoire saveRepository(Repertoire repertoire){
        return repertoireRepository.save(repertoire);
    }

    public List<Repertoire> getAllRepositories() {
        return repertoireRepository.findAll();
    }
}
