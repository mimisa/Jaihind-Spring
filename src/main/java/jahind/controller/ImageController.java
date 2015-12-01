package jahind.controller;

import jahind.entity.Image;
import jahind.entity.Media_Type;
import jahind.service.ImageService;
import jahind.service.MediaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;

/**
 * Created by Gaurav on 30/11/15.
 */
@RestController
@RequestMapping(value = "/api/images")
//Max uploaded file size (here it is 20 MB)
//@MultipartConfig(fileSizeThreshold = 20971520)
public class ImageController {

    @Autowired
    private MediaTypeService mediaTypeService;

    @Autowired
    private ImageService imageService;

    // Insert Image

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<Image> imageUpload(@RequestParam("file") MultipartFile file) throws Exception {

        if (mediaTypeService.findOne(1L) == null) {
            System.out.println("MediaType not found");
            return new ResponseEntity<Image>(HttpStatus.NO_CONTENT);
        }

        Image image = new Image();
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        image.setImage_name(file.getOriginalFilename());
        image.setImage_type(file.getContentType());
        image.setImage_blob(blob);


        Media_Type mt = mediaTypeService.findOne(1L);
        image.setMedia_type(mt);

        imageService.save(image);

        //image.add(linkTo(methodOn(ImageController.class).getMediaType(savedMediaType.getMedia_type_id())).withSelfRel());

        return new ResponseEntity<Image>(image, HttpStatus.CREATED);

    }

    //@RequestMapping(value = "/{image_id}", produces = MediaType.)
/*
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("uploadedFile") MultipartFile uploadedFileRef) {
        // Get name of uploaded file.
        String fileName = uploadedFileRef.getOriginalFilename();

        // Path where the uploaded file will be stored.
        String path = "D:/" + fileName;

        // This buffer will store the data read from 'uploadedFileRef'
        byte[] buffer = uploadedFileRef.getBytes();

        // Now create the output file on the server.
        File outputFile = new File(path);

        FileInputStream reader = null;
        FileOutputStream writer = null;
        int totalBytes = 0;
        try {
            outputFile.createNewFile();

            // Create the input stream to uploaded file to read data from it.
            reader = (FileInputStream) uploadedFileRef.getInputStream();

            // Create writer for 'outputFile' to write data read from
            // 'uploadedFileRef'
            writer = new FileOutputStream(outputFile);

            // Iteratively read data from 'uploadedFileRef' and write to
            // 'outputFile';
            int bytesRead = 0;
            while ((bytesRead = reader.read(buffer)) != -1) {
                writer.write(buffer);
                totalBytes += bytesRead;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "File uploaded successfully! Total Bytes Read=" + totalBytes;
    }
    */
}
