package ma.enset.comparaison.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/directories")
public class DirectoryController {

    @PostMapping("/compare")
    public ResponseEntity<String> compareDirectories(@RequestParam("directory1") MultipartFile directory1,
                                                     @RequestParam("directory2") MultipartFile directory2) {
        // Handle directory uploads and comparison logic here
        return ResponseEntity.ok("Directories uploaded and compared successfully");
    }
}
