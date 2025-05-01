package com.example.demo.controller;

import com.example.demo.entity.Backup;
import com.example.demo.repository.BackupRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/backup")
public class BackupController {

    @PostMapping("/database/postgresql")
    public ResponseEntity<String> backupDatabasePostgreSQL() {
        try {
            // Verificar si el directorio de backup existe, si no, crearlo
            File backupDir = new File("E:/backup/");
            if (!backupDir.exists()) {
                backupDir.mkdirs();  // Crea la carpeta si no existe
            }

            // Comando para realizar el backup de PostgreSQL usando pg_dump
            String command = "set PGPASSWORD=12345 && \"C:\\Program Files\\PostgreSQL\\16\\bin\\pg_dump\" -U postgres -h localhost -p 5432 db_obra_civil";

            // En Windows, debemos usar "cmd.exe" en lugar de "sh"
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command);
            processBuilder.redirectOutput(new File("E:/backup/backup.sql")); // Redirige la salida al archivo
            ////processBuilder.start();

            //ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", command);
            //processBuilder.start();

            ////return ResponseEntity.ok("Backup PostgreSQL realizado correctamente.");
            Process process = processBuilder.start();

            // Capturar la salida estándar (stdout)
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder errorOutput = new StringBuilder();
            String line;
            //StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }

            // Capturar la salida de error (stderr)
            /*BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorOutput = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }*/

            // Esperar a que el proceso termine
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return ResponseEntity.ok("Backup PostgreSQL realizado correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al realizar el backup de PostgreSQL: " + errorOutput.toString());
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al realizar el backup de PostgreSQL: " + e.getMessage());
        }
    }

    @PostMapping("/database/mysql")
    public ResponseEntity<String> backupDatabaseMySQL() {
        try {
            // Comando para realizar el backup de MySQL usando mysqldump
            String command = "mysqldump -u usuario -pcontraseña base_de_datos > /ruta/del/backup/backup.sql";
            ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", command);
            processBuilder.start();

            return ResponseEntity.ok("Backup MySQL realizado correctamente.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al realizar el backup de MySQL: " + e.getMessage());
        }
    }

    @Autowired
    private BackupRepository backupRepository;

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
}
