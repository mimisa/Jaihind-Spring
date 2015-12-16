package jahind.controller;

import jahind.entity.Advertise;
import jahind.entity.User;
import jahind.service.AdvertiseService;
import jahind.service.UserService;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Gaurav on 14-12-2015.
 */
@RestController
@RequestMapping("/api/adds")
public class AdvertiseController {

    @Autowired
    private AdvertiseService advertiseService;

    @Autowired
    private UserService userService;

    // Insert Image
    @RequestMapping(value = "/upload",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String imageUpload(@RequestParam("file") MultipartFile file, String name,
                              String placement, String link,
                              String publishedDate, String unpublishedDate,
                              HttpServletRequest req, HttpServletResponse response) throws Exception {

        User user = userService.findOne(1);

        Advertise advertise = new Advertise();
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        advertise.setAdd_blob(blob);

        DateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        Date date1 = formatter.parse(publishedDate);
        Date date2 = formatter.parse(unpublishedDate);
        advertise.setPublishedDate(date1);
        advertise.setUnpublishedDate(date2);
        advertise.setLink(link);
        advertise.setAdd_name(name);
        advertise.setPlacement(placement);

        List<Advertise> advertiseList = null;
        if (user.getPolls().size() == 0 || user.getPolls().isEmpty()) {
            advertiseList = new ArrayList<>();
        }
        advertiseList = user.getAdvertiseList();
        advertiseList.add(advertise);
        user.setAdvertiseList(advertiseList);

        Advertise savedAdd = advertiseService.save(advertise);
        userService.save(user);

        JSONObject outputJsonObj = new JSONObject();

        String IP = req.getServerName();
        int Port = req.getServerPort();

        outputJsonObj.put("image_id", savedAdd.getAdd_id());
        // outputJsonObj.put("username", savedAdd.getUser().getName());
        outputJsonObj.put("link", "http://" + IP + ":" + Port + "/Jaihind/api/adds/" + savedAdd.getAdd_id());

        return outputJsonObj.toString();

    }

    @RequestMapping(value = "/{add_id}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable("add_id") Long add_id) throws Exception {
        Advertise image = advertiseService.findOne(add_id);
        Blob blob = image.getAdd_blob();
        int length = (int) blob.length();
        byte[] bytes = blob.getBytes(1, length);

        return bytes;

    }

    @RequestMapping(value = "/{add_id}/details",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getDetails(@PathVariable("add_id") Long add_id, HttpServletRequest req) throws Exception {
        /*
        Advertise advertise = advertiseService.findOne(add_id);

      Resource<Advertise> resource = new Resource<>(advertise);
        resource.add(linkTo(methodOn(AdvertiseController.class).getPhoto(advertise.getAdd_id())).withSelfRel());

        return resource;*/

        JSONObject jsonObject = new JSONObject();
        Advertise advertise = advertiseService.findOne(add_id);
        String IP = req.getServerName();
        int Port = req.getServerPort();

        jsonObject.put("Advertise_Id", advertise.getAdd_id());
        jsonObject.put("Advertise_Name", advertise.getAdd_name());
        jsonObject.put("Published_Date", advertise.getPublishedDate());
        jsonObject.put("Unpublished_Date", advertise.getUnpublishedDate());
        jsonObject.put("Redirect_Link", advertise.getLink());
        jsonObject.put("Placement", advertise.getPlacement());
//        jsonObject.put("User_name", advertise.getUser().getName());
        jsonObject.put("Link", "http://" + IP + ":" + Port + "/Jaihind/api/adds/" + advertise.getAdd_id());

        return jsonObject.toString();

    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getArticles(Pageable pageable, PagedResourcesAssembler assembler, HttpServletRequest req)
            throws Exception {
        Page<Advertise> advertises = advertiseService.findAll(pageable);
        JSONArray jsonArray = new JSONArray();
        for (Advertise advertise : advertises) {
            JSONObject jsonObject = new JSONObject();
            String IP = req.getServerName();
            int Port = req.getServerPort();

            jsonObject.put("Advertise_Id", advertise.getAdd_id());
            jsonObject.put("Advertise_Name", advertise.getAdd_name());
            jsonObject.put("Published_Date", advertise.getPublishedDate());
            jsonObject.put("Unpublished_Date", advertise.getUnpublishedDate());
            jsonObject.put("Redirect_Link", advertise.getLink());
            jsonObject.put("Placement", advertise.getPlacement());
            jsonObject.put("Link", "http://" + IP + ":" + Port + "/Jaihind/api/adds/" + advertise.getAdd_id());

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    @RequestMapping(params = "placement", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getArticlesByPlacement(@RequestParam("placement") String placement,
                                         Pageable pageable, PagedResourcesAssembler assembler, HttpServletRequest req)
            throws Exception {
        Page<Advertise> advertises = advertiseService.findByPlacement(pageable, placement);
        JSONArray jsonArray = new JSONArray();
        for (Advertise advertise : advertises) {
            JSONObject jsonObject = new JSONObject();
            String IP = req.getServerName();
            int Port = req.getServerPort();

            jsonObject.put("Advertise_Id", advertise.getAdd_id());
            jsonObject.put("Advertise_Name", advertise.getAdd_name());
            jsonObject.put("Published_Date", advertise.getPublishedDate());
            jsonObject.put("Unpublished_Date", advertise.getUnpublishedDate());
            jsonObject.put("Redirect_Link", advertise.getLink());
            jsonObject.put("Placement", advertise.getPlacement());
            jsonObject.put("Link", "http://" + IP + ":" + Port + "/Jaihind/api/adds/" + advertise.getAdd_id());

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

}
