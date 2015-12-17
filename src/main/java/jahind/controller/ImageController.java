package jahind.controller;

import jahind.entity.Image;
import jahind.entity.Media_Type;
import jahind.service.ImageService;
import jahind.service.MediaTypeService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Blob;

/**
 * Created by Gaurav on 30/11/15.
 */
@RestController
@RequestMapping(value = "/api/images")
public class ImageController {

    @Autowired
    ServletContext context;

    @Autowired
    private MediaTypeService mediaTypeService;

    @Autowired
    private ImageService imageService;

    // Return custom JsonObject
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getJson() throws JSONException {
        JSONObject outputJsonObj = new JSONObject();
        outputJsonObj.put("Key", "Value");

        return outputJsonObj.toString();
    }

    // Insert Image
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String imageUpload(@RequestParam("file") MultipartFile file,
                              HttpServletRequest req, HttpServletResponse response) throws Exception {

        if (mediaTypeService.findOne(1L) == null) {
            System.out.println("MediaType not found");
        }

        // Save in DB
        Image image = new Image();
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        image.setImage_name(file.getOriginalFilename());
        image.setImage_type(file.getContentType());
        image.setImage_blob(blob);

        Media_Type mt = mediaTypeService.findOne(1L);
        image.setMedia_type(mt);

        imageService.save(image);

        JSONObject outputJsonObj = new JSONObject();

        String IP = req.getServerName();
        int Port = req.getServerPort();

        outputJsonObj.put("image_id", image.getImage_id());
        outputJsonObj.put("link", "http://" + IP + ":" + Port + "/Jaihind/api/images/" + image.getImage_id());

        return outputJsonObj.toString();


        // image.add(linkTo(methodOn(ImageController.class).getPhotoURL(image.getImage_id())).withSelfRel());
        //return new ResponseEntity<Image>(image, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{image_id}/details", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Image> getPhotoURL(@PathVariable("image_id") Long image_id) {
        Image image = imageService.findOne(image_id);
        return new ResponseEntity<Image>(image, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{image_id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable("image_id") Long image_id) throws Exception {
        Image image = imageService.findOne(image_id);
        Blob blob = image.getImage_blob();
        int length = (int) blob.length();
        byte[] bytes = blob.getBytes(1, length);

        HttpHeaders headers = new HttpHeaders();

        return bytes;

    }

    // Delete Image

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
