package jahind.controller;

import jahind.entity.Advertise;
import jahind.service.AdvertiseService;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Blob;
import java.util.Date;

/**
 * Created by Gaurav on 14-12-2015.
 */
@RestController
@RequestMapping("/api/adds")
public class AdvertiseController {

    @Autowired
    private AdvertiseService advertiseService;

    // Insert Image
    @RequestMapping(value = "/upload",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String imageUpload(@RequestParam("file") MultipartFile file, Date unpublishedDate,
                              HttpServletRequest req, HttpServletResponse response) throws Exception {

        Advertise advertise = new Advertise();
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        advertise.setAdd_blob(blob);
        advertise.setPublishedDate(new Date());
        advertise.setUnpublishedDate(unpublishedDate);
        advertise.setAdd_name(file.getOriginalFilename());

        Advertise savedAdd = advertiseService.save(advertise);

        JSONObject outputJsonObj = new JSONObject();

        String IP = req.getServerName();
        int Port = req.getServerPort();

        outputJsonObj.put("image_id", savedAdd.getAdd_id());
        outputJsonObj.put("link", "http://" + IP + ":" + Port + "/Jaihind/api/adds/" + savedAdd.getAdd_id());

        return outputJsonObj.toString();

    }

    @RequestMapping(value = "/{add_id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable("add_id") Long add_id) throws Exception {
        Advertise image = advertiseService.findOne(add_id);
        Blob blob = image.getAdd_blob();
        int length = (int) blob.length();
        byte[] bytes = blob.getBytes(1, length);

        return bytes;

    }
}
