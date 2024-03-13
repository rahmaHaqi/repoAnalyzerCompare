package ma.enset.comparaison.web;

import ma.enset.comparaison.repository.RepertoireRepository;
import ma.enset.comparaison.entities.Repertoire;
import ma.enset.comparaison.service.RepertoireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repositories")
public class RepositoryController {
    @Autowired
    private RepertoireService repositoryService;

    @PostMapping
    public Repertoire createRepository(@RequestBody Repertoire repertoire) {
        return repositoryService.saveRepository(repertoire);
    }

    @GetMapping
    public List<Repertoire> getAllRepositories() {
        return repositoryService.getAllRepositories();
    }
}