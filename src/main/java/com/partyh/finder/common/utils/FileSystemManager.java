package com.partyh.finder.common.utils;

import com.partyh.finder.common.exception.impl.PFOperationFailedException;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;

public class FileSystemManager {
    public static void createDirectory(String path) {
        //create directory
        try {
            File newFolder = new File(path);
            if (!newFolder.exists() && !newFolder.mkdir()) {
                throw new PFOperationFailedException("could not create the directory");
            }
        } catch (SecurityException e) {
            throw new PFOperationFailedException("could not create the directory");
        }
    }


    /**
     * elimina la directory
     *
     * @param path la directory da eliminare
     */
    public static void delete(String path) {
        String errorMessage = "could not delete the directory";
        try {
            if (!FileSystemUtils.deleteRecursively(Paths.get(path))) {
                throw new PFOperationFailedException(errorMessage);
            }
        } catch (IOException e) {
            throw new PFOperationFailedException(errorMessage);
        }
    }

    /**
     * Crea la directory e trasferisce un file.
     *
     * @param multipartFile Il file ricevuto in ingresso dagli endpoint
     * @param folderPath    cartella dove salvare il file
     */
    public static void save(MultipartFile multipartFile, String folderPath) {
        try {
            if (StringUtils.hasText(folderPath)) {
                createDirectory(folderPath);
            }
            multipartFile.transferTo(new File(folderPath + File.separatorChar + multipartFile.getOriginalFilename()));
        } catch (IOException | IllegalStateException e) {
            throw new PFOperationFailedException("could not save the file");
        }
    }

    public static void createDirectoryAndSave(MultipartFile multipartFile, String folderPath) {
        try {
            createDirectory(folderPath);
            save(multipartFile, folderPath);
        } catch (PFOperationFailedException e) {
            throw e; // re-throw exception if directory creation or file saving fails
        } catch (Exception e) {
            throw new PFOperationFailedException("could not create directory and save the file");
        }
    }

}