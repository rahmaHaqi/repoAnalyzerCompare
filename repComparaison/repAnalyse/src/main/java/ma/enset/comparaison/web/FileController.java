package ma.enset.comparaison.web;

import ma.enset.comparaison.service.FileService;
import ma.enset.comparaison.entities.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping
    public File createFile(@RequestBody File file) {
        return fileService.saveFile(file);
    }

    @GetMapping
    public List<File> getAllFiles() {
        return fileService.getAllFiles();
    }
}