package ma.enset.comparaison.web;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
public class ZipComparator {

    @PostMapping("/extract-zip")
    public ResponseEntity<List<String>> extractZip(
            @RequestParam String fileName,
            @RequestBody byte[] fileContents,
            @RequestParam String tempDir) {

        List<String> extractedFiles = new ArrayList<>();
        String dataDir = removeExt(fileName);

        try (ByteArrayInputStream bis = new ByteArrayInputStream(fileContents);
             ZipInputStream zipStream = new ZipInputStream(bis)) {

            File tempDirectory = new File(tempDir + "/" + dataDir);
            tempDirectory.mkdirs();

            ZipEntry entry;
            while ((entry = zipStream.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    File file = new File(tempDirectory, entry.getName());
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        FileCopyUtils.copy(zipStream, fos);
                        extractedFiles.add(file.getAbsolutePath());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(extractedFiles);
    }

    @PostMapping("/compare-repos")
    public ResponseEntity<Map<String, Object>> compareRepos(
            @RequestBody Map<String, Object> request) {
        String oldRepoName = (String) request.get("oldRepoName");
        List<String> oldFiles = (List<String>) request.get("oldFiles");
        String newRepoName = (String) request.get("newRepoName");
        List<String> newFiles = (List<String>) request.get("newFiles");

        Map<String, Object> response = new HashMap<>();

        Map<String, Object> filesState = getFilesState(oldRepoName, oldFiles, newRepoName, newFiles);
        response.put("filesState", filesState);

        List<Map<String, String>> states = getStates(filesState, oldRepoName, newRepoName);
        response.put("states", states);

        Map<String, Integer> repoChartData = getRepoChartData(filesState);
        response.put("repoChartData", repoChartData);

        return ResponseEntity.ok(response);
    }

    private String removeExt(String fileName) {
        return fileName.replace(".zip", "");
    }

    private List<String> listFiles(String directory) {
        List<String> files = new ArrayList<>();
        File dir = new File(directory);
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    files.add(file.getName());
                }
            }
        }
        return files;
    }

    private boolean isChanged(String oldFile, String newFile) {
        // Implement your comparison logic here
        return true;
    }

    private List<String> getChangedFiles(String oldRepo, String newRepo, List<String> commonFiles) {
        List<String> changedFiles = new ArrayList<>();
        for (String file : commonFiles) {
            if (isChanged(oldRepo + "/" + file, newRepo + "/" + file)) {
                changedFiles.add(file);
            }
        }
        return changedFiles;
    }

    private Map<String, Object> getFilesState(String oldRepoName, List<String> oldFiles, String newRepoName, List<String> newFiles) {
        Map<String, Object> filesState = new HashMap<>();
        List<String> deleted = new ArrayList<>(oldFiles);
        deleted.removeAll(newFiles);

        List<String> added = new ArrayList<>(newFiles);
        added.removeAll(oldFiles);

        List<String> common = new ArrayList<>(oldFiles);
        common.retainAll(newFiles);

        List<String> changed = getChangedFiles(oldRepoName, newRepoName, common);

        List<String> intact = new ArrayList<>(common);
        intact.removeAll(changed);

        filesState.put("deleted", deleted);
        filesState.put("added", added);
        filesState.put("changed", changed);
        filesState.put("intact", intact);

        return filesState;
    }

    private List<Map<String, String>> getStates(Map<String, Object> filesState, String oldRepoName, String newRepoName) {
        List<Map<String, String>> states = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filesState.entrySet()) {
            String status = entry.getKey();
            List<String> files = (List<String>) entry.getValue();
            for (String file : files) {
                Map<String, String> state = new HashMap<>();
                state.put("file", file);
                state.put("status", status);
                state.put("repo", status.equals("deleted") ? oldRepoName : newRepoName);
                states.add(state);
            }
        }
        return states;
    }

    private Map<String, Integer> getRepoChartData(Map<String, File > fileState) {
        Map<String, Integer> repoChartData = new HashMap<>();
        repoChartData.put("added", ((List<String>) fileState.get("added")).size());
        repoChartData.put("deleted", ((List<String>) fileState.get("deleted")).size());
        repoChartData.put("changed", ((List<String>) fileState.get("changed")).size());
        repoChartData.put("intact", ((List<String>) fileState.get("intact")).size());
        return repoChartData;
    }


}

