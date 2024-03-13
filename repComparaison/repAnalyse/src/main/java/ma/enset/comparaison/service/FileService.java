package ma.enset.comparaison.service;

import ma.enset.comparaison.entities.File;
import ma.enset.comparaison.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepertoire;

    public File saveFile(File file) {
        return fileRepertoire.save(file);
    }

    public List<File> getAllFiles() {
        return fileRepertoire.findAll();
    }
}