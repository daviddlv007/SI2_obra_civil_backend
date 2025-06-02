package com.example.demo.controller;

import com.example.demo.util.GoogleDriveUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@RequestMapping("/backup")
public class RestoreController {

    @Autowired
    private GoogleDriveUploader googleDriveUploader;

    @PostMapping("/restore/h2")
    public ResponseEntity<String> restoreDatabase(@RequestParam("file") MultipartFile file) {
        File tempFile = null;
        try {
            // Guardar el archivo temporalmente
            tempFile = File.createTempFile("backup", ".zip");

            // Transferir el archivo recibido al archivo temporal
            file.transferTo(tempFile);

            // Comando para restaurar la base de datos desde el archivo ZIP
            String restoreCommand = "RESTORE FROM '" + tempFile.getAbsolutePath() + "';";

            // Establecer conexión a la base de datos
            try (Connection conn = DriverManager.getConnection("jdbc:h2:~/obra_civil", "sa", "");
                 Statement stmt = conn.createStatement()) {
                stmt.execute(restoreCommand);
            }

            // Eliminar el archivo temporal después de la restauración
            if (tempFile.exists()) {
                tempFile.delete();
            }

            return ResponseEntity.ok("Restauración iniciada correctamente.");
        } catch (SQLException | IOException e) {
            // Eliminar el archivo temporal en caso de error
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al restaurar la base de datos: " + e.getMessage());
        }
    }

    @PostMapping("/restore/{fileId}")
    public ResponseEntity<String> restoreBackup(@PathVariable String fileId) {
        try {
            googleDriveUploader.restoreBackup(fileId);
            return ResponseEntity.ok("Backup restaurado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al restaurar backup: " + e.getMessage());
        }
    }

}
