package com.dinesh.app.dm.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobItem;
import com.dinesh.app.dm.config.AzureFileUploadConfig;
import com.dinesh.app.dm.constants.ThermaxBlobStorageMessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadService {

    private final AzureFileUploadConfig azureFileUploadConfig;

    public String uploadBlob(String fileName, InputStream fileInputStream, long fileSize, RedirectAttributes redirectAttributes) {
        String message = "";

        log.info("Inside upload of FileUploadService.. ");
        log.info("File Upload Started..");
        BlobClient blobClient = azureFileUploadConfig.blobContainerClient().getBlobClient("dinesh/" + fileName);
        if (blobClient.exists()) {
            log.info(ThermaxBlobStorageMessageConstants.FILE_ALREADY_EXIST + " {} ", fileName);
            message = ThermaxBlobStorageMessageConstants.FILE_ALREADY_EXIST;
            ;
        } else {
            blobClient.upload(fileInputStream, fileSize);
            log.info(ThermaxBlobStorageMessageConstants.FILE_UPLOAD_SUCCESS);
            message = ThermaxBlobStorageMessageConstants.FILE_UPLOAD_SUCCESS;
        }

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/api/v1/files/";
    }

    /**
     * This method will return all the blob files stored at the Azure BlobStorage container.
     *
     * @return
     */
    public List<String> listFiles() {
        List<String> blobFiles = new ArrayList<>();
        BlobContainerClient blobContainerClient = azureFileUploadConfig.blobContainerClient();
        for (BlobItem blobItem : blobContainerClient.listBlobs()) {
            blobFiles.add(blobItem.getName());
        }
        return blobFiles;
    }
}
