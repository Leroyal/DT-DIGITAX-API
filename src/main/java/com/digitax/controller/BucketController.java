package com.digitax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digitax.service.AmazonClient;

@RestController
@RequestMapping("/api/auth")
public class BucketController {
	@Autowired
    AmazonClient amazonClient;

	 /**##
	  * 
	  * @param file
	  * @return
	  * Used AWS s3 bucket to upload file And it's default library to upload
	  */
    @PostMapping("/upload-file")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }
    
    /**##
     * 
     * @param fileUrl
     * @return
     *  Used AWS s3 bucket to upload file And it's default library to upload
     */
    @DeleteMapping("/delete-file")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}