package com.example.demo.service;

import com.example.demo.util.GoogleDriveUploader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import java.sql.*;

@Service
public class BackupService {

    private final GoogleDriveUploader driveUploader;

    // Datos de conexión a tu base H2
    private static final String DB_URL = "jdbc:h2:file:./data/obra_civil"; // sin .mv.db
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public BackupService(GoogleDriveUploader driveUploader) {
        this.driveUploader = driveUploader;
    }

    @Scheduled(cron = "0 30 22 * * *") // Cada día a medianoche ("seg, min, hora, dia_mes, mes, dia_semana")
    public void realizarBackup() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            //String backupPath = "backup/db_backup_" + timestamp + ".zip";
            String backupDir = "BackupsH2";
            String sqlPath = backupDir + "/backup_" + timestamp + ".sql";
            String zipPath = backupDir + "/backup_" + timestamp + ".zip";

            // Crear carpeta si no existe
            new File(backupDir).mkdirs();

            // Exportar SQL desde H2
            exportarSql(sqlPath);

            // Comprimir el archivo SQL
            zipFile(sqlPath, zipPath);

            // Subir a Google Drive
            String fileId = driveUploader.uploadFile(new File(zipPath));
            System.out.println("Backup subido a Google Drive con ID: " + fileId);

            // Limpiar archivos locales y en Drive
            limpiarBackupsLocales(7);
            driveUploader.deleteOldBackups(7);

        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println("Error durante el backup:");
            e.printStackTrace();
        }
    }

    private void exportarSql(String path) throws Exception {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {

            String script = "SCRIPT TO '" + path.replace("\\", "/") + "'";
            stmt.execute(script);
            System.out.println("Backup SQL generado en: " + path);
        }
    }

    private void zipFile(String sourcePath, String zipPath) throws IOException {
        try (FileInputStream fis = new FileInputStream(sourcePath);
             FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            ZipEntry zipEntry = new ZipEntry(new File(sourcePath).getName());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = fis.read(buffer)) >= 0) {
                zos.write(buffer, 0, length);
            }

            zos.closeEntry();
            System.out.println("Archivo comprimido: " + zipPath);
        }
    }

    private void limpiarBackupsLocales(int dias) {
        File backupDir = new File("BackupsH2");
        File[] files = backupDir.listFiles();
        if (files != null) {
            for (File file : files) {
                long diff = System.currentTimeMillis() - file.lastModified();
                if (diff > dias * 24L * 60 * 60 * 1000) {
                    boolean deleted = file.delete();
                    if (deleted) {
                        System.out.println("Backup local eliminado: " + file.getName());
                    }
                }
            }
        }
    }

    public void ejecutarBackupManual() {
        realizarBackup();
    }

}
