package com.example.demo.controller;

import com.example.demo.dto.DriveFileDto;
import com.example.demo.entity.Backup;
import com.example.demo.repository.BackupRepository;
import com.example.demo.service.BackupService;
import com.example.demo.util.GoogleDriveUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/backup")
public class BackupController {

    private final BackupService backupService;

    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }

    @Autowired
    private BackupRepository backupRepository;

    @Autowired
    private GoogleDriveUploader googleDriveUploader;

    @PostMapping("/database/h2")
    public ResponseEntity<Map<String, String>> backupDatabase() {
        try {

            // Ruta del archivo de backup
            String backupDirectory = "backup";  // Directorio donde se guardará el backup
            String backupFilePath = "backup/backup_" + UUID.randomUUID().toString() + ".zip";
            //String backupFilePath = "backup/backup.zip";  // Ruta completa para el archivo de backup

            // Crear la carpeta si no existe
            File directory = new File(backupDirectory);
            if (!directory.exists()) {
                directory.mkdirs();  // Crea la carpeta "backup" si no existe
            }

            // Comando de backup
            String backupCommand = "BACKUP TO '" + backupFilePath + "';";

            // Ejecutar el comando para realizar el backup
            Connection conn = DriverManager.getConnection("jdbc:h2:~/obra_civil", "sa", "");
            Statement stmt = conn.createStatement();
            stmt.execute(backupCommand);
            stmt.close();
            conn.close();

            // Guardar el backup en la base de datos
            Backup backup = new Backup();
            backup.setRuta(backupFilePath);
            backup.setFechahora(LocalDateTime.now());  // Registrar la fecha y hora actual
            backupRepository.save(backup);  // Guardar el backup en la base de datos

            // Responder con un mapa en formato JSON
            Map<String, String> response = new HashMap<>();
            response.put("message", "Backup realizado correctamente.");
            return ResponseEntity.ok(response); // Devolver una respuesta con código 200 y un cuerpo JSON
            //return ResponseEntity.ok("Backup realizado correctamente.");
        } catch (SQLException e) {
            // Manejo de error
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al realizar el backup: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            /*return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al realizar el backup: " + e.getMessage());*/
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Backup>> getBackups() {
        List<Backup> backups = backupRepository.findAll();  // Obtener todos los backups
        return ResponseEntity.ok(backups);  // Retornar la lista de backups
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadBackup(String backupFilePath) {
        File file = new File(backupFilePath);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Crear un Resource a partir del archivo
        Path path = file.toPath();
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            // Manejo de la excepción en caso de que la URL sea inválida
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Retornar el archivo como respuesta de descarga
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

    // Google Driver List OK
    @GetMapping("/api/backups")
    public List<DriveFileDto> listarBackups() throws IOException {
        return googleDriveUploader.listBackupFiles();
    }

    /*@PostMapping("/drive")
    public ResponseEntity<Map<String, String>> backupToGoogleDrive() {
        try {
            // Crear backup local (mismo que usas en /database/h2)
            String backupDirectory = "backup";
            String backupFilePath = "backup/backup_" + UUID.randomUUID().toString() + ".zip";

            File directory = new File(backupDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String backupCommand = "BACKUP TO '" + backupFilePath + "';";
            Connection conn = DriverManager.getConnection("jdbc:h2:~/obra_civil", "sa", "");
            Statement stmt = conn.createStatement();
            stmt.execute(backupCommand);
            stmt.close();
            conn.close();

            // Subir a Google Drive
            File backupFile = new File(backupFilePath);
            String fileId = googleDriveUploader.uploadFile(backupFile);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Backup subido a Google Drive correctamente.");
            response.put("fileId", fileId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al subir el backup: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }*/

    @PostMapping("/drive")
    public ResponseEntity<Map<String, String>> backupToGoogleDrive() {
        try {
            backupService.ejecutarBackupManual();

            Map<String, String> response = new HashMap<>();
            response.put("message", "Backup ejecutado y subido a Google Drive correctamente.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al ejecutar el backup: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/drive/download/{fileId}")
    public ResponseEntity<Resource> downloadDriveBackup(@PathVariable String fileId) {
        try {
            // Descargar archivo a un archivo temporal
            File tempFile = File.createTempFile("drive_backup_", ".zip");
            try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                googleDriveUploader.getDriveService()
                        .files()
                        .get(fileId)
                        .executeMediaAndDownloadTo(outputStream);
            }

            Resource resource = new UrlResource(tempFile.toURI());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tempFile.getName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
