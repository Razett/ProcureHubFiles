package com.glkids.procurehubfiles.service;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class FTPService {

    @Value("${ftp.server}")
    private String server;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String user;

    @Value("${ftp.password}")
    private String pass;

    public byte[] downloadFile(String filePath) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            boolean success = ftpClient.retrieveFile(filePath, outputStream);
            if (success) {
                return outputStream.toByteArray();
            } else {
                throw new IOException("Failed to download file from FTP server.");
            }
        } finally {
            outputStream.close();
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }
}
