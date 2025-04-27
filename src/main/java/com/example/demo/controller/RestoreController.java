package com.example.demo.controller;

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

    @PostMapping("/restore/postgresql")
    public ResponseEntity<String> restoreDatabasePostgreSQL(@RequestParam("file") MultipartFile file) {
        try {
            // Guardar el archivo temporalmente
            File tempFile = File.createTempFile("backup", ".sql");
            file.transferTo(tempFile);

            // Comando para restaurar la base de datos PostgreSQL usando el archivo de backup
            String command = "psql -U usuario base_de_datos < " + tempFile.getAbsolutePath();
            ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", command);
            processBuilder.start();

            return ResponseEntity.ok("Restauración PostgreSQL iniciada correctamente.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al restaurar la base de datos PostgreSQL: " + e.getMessage());
        }
    }

    @PostMapping("/restore/mysql")
    public ResponseEntity<String> restoreDatabaseMySQL(@RequestParam("file") MultipartFile file) {
        try {
            // Guardar el archivo temporalmente
            File tempFile = File.createTempFile("backup", ".sql");
            file.transferTo(tempFile);

            // Comando para restaurar la base de datos MySQL usando el archivo de backup
            String command = "mysql -u usuario -pcontraseña base_de_datos < " + tempFile.getAbsolutePath();
            ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", command);
            processBuilder.start();

            return ResponseEntity.ok("Restauración MySQL iniciada correctamente.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al restaurar la base de datos MySQL: " + e.getMessage());
        }
    }

    /*@PostMapping("/restore/h2")
    public ResponseEntity<String> restoreDatabase(@RequestParam("file") MultipartFile file) {
        try {
            // Guardar el archivo temporalmente
            File tempFile = File.createTempFile("backup", ".zip");
            file.transferTo(tempFile);

            // Comando para restaurar la base de datos desde el archivo ZIP
            String restoreCommand = "RESTORE FROM '" + tempFile.getAbsolutePath() + "';";
            Connection conn = DriverManager.getConnection("jdbc:h2:~/obra_civil", "sa", "");
            Statement stmt = conn.createStatement();
            stmt.execute(restoreCommand);
            stmt.close();
            conn.close();

            return ResponseEntity.ok("Restauración iniciada correctamente.");
        } catch (SQLException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al restaurar la base de datos: " + e.getMessage());
        }
    }*/

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

}
