/*package com.example.demo.util;

import com.example.demo.dto.DriveFileDto;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class GoogleDriveUploader {

    private final Drive driveService;

    private static final String BACKUP_FOLDER_ID = "1OiufIcaC1Nu3QWGIP6st6fIM2RXBMjoq"; // <-- tu ID aquí

    public GoogleDriveUploader() throws IOException {
        GoogleCredential credential = GoogleCredential.fromStream(
                new ClassPathResource("credentials.json").getInputStream()
        ).createScoped(Collections.singleton(DriveScopes.DRIVE_FILE));

        this.driveService = new Drive.Builder(
                credential.getTransport(),
                credential.getJsonFactory(),
                credential
        ).setApplicationName("BackupUploader").build();
    }

    public String uploadFile(java.io.File localFile) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(localFile.getName());
        fileMetadata.setParents(Collections.singletonList(BACKUP_FOLDER_ID)); // Destino

        File uploadedFile = driveService.files().create(fileMetadata,
                        new com.google.api.client.http.FileContent("application/zip", localFile))
                .setFields("id")
                .execute();

        return uploadedFile.getId(); // Devuelve el ID del archivo en Google Drive
    }

    public void deleteOldBackups(int daysThreshold) throws IOException {
        long millisThreshold = System.currentTimeMillis() - (daysThreshold * 24L * 60 * 60 * 1000);

        Drive.Files.List request = driveService.files().list()
                .setQ("'" + BACKUP_FOLDER_ID + "' in parents and mimeType='application/zip'")
                .setFields("files(id, name, createdTime)");

        for (File file : request.execute().getFiles()) {
            if (file.getCreatedTime().getValue() < millisThreshold) {
                driveService.files().delete(file.getId()).execute();
                System.out.println("Backup Drive eliminado: " + file.getName());
            }
        }
    }

    public List<DriveFileDto> listBackupFiles() throws IOException {
        List<DriveFileDto> filesDto = new ArrayList<>();

        Drive.Files.List request = driveService.files().list()
                .setQ("'" + BACKUP_FOLDER_ID + "' in parents and mimeType='application/zip'")
                .setFields("files(id, name, createdTime, size)");

        for (File file : request.execute().getFiles()) {
            filesDto.add(new DriveFileDto(file.getId(), file.getName(), file.getCreatedTime().toStringRfc3339(), file.getSize()));
        }

        return filesDto;
    }

    public void restoreBackup(String fileId) throws IOException {
        // 1. Descargar archivo ZIP
        java.io.File zipFile = new java.io.File("restore.zip");
        OutputStream outputStream = new FileOutputStream(zipFile);
        driveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);

        // 2. Descomprimir
        String sqlPath = unzip(zipFile);

        // 3. Ejecutar el SQL en H2
        try {
            Class.forName("org.h2.Driver");
            try (Connection conn = DriverManager.getConnection("jdbc:h2:file:./data/obra_civil", "sa", "")) {
                Statement stmt = conn.createStatement();
                stmt.execute("RUNSCRIPT FROM '" + sqlPath.replace("\\", "/") + "'");
                //stmt.execute("RUNSCRIPT FROM 'BackupsH2/backup_20240508.sql'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String unzip(java.io.File zipFile) throws IOException {
        new java.io.File("restore").mkdirs();
        String sqlFilePath = null;

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                java.io.File newFile = new java.io.File("restore", entry.getName());
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                if (entry.getName().endsWith(".sql")) {
                    sqlFilePath = newFile.getAbsolutePath();
                }
            }
        }

        if (sqlFilePath == null) {
            throw new IOException("No .sql file found in ZIP");
        }

        return sqlFilePath;
    }

    public Drive getDriveService() {
        return this.driveService;
    }


}
*/