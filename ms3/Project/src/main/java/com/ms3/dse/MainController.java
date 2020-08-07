package com.ms3.dse;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * This is a RESTful controller
 */
@RestController
public class MainController {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private UsersearchRepository usersearchRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String TOKEN_SECRET = "DSE";

    /**
     * This is a function to add new advertisements, it is used by MS2 to copy or update
     * her advertisements to my database
     */
    @PostMapping("/add/advertisement")
    public @ResponseBody
    ResponseEntity newAdvertisement(@RequestBody Advertisement newAdd) {
        try {
            advertisementRepository.save(newAdd);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * This is a function to add new users, it is used by MS1 to copy or update
     * his users to my database
     */
    @PostMapping("/add/user")
    public @ResponseBody
    ResponseEntity newUser(@RequestBody User newUser) {
        try {
            userRepository.save(newUser);
            Usersearch u = new Usersearch(newUser);
            usersearchRepository.save(u);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }


    /**
     * This is a function to delete advertisements, it is used by MS2 to delete advertisements
     * in my database synchronously to delete-function in MS2
     */
    @DeleteMapping("/delete/advertisement/{id}")
    public void deleteAd(@PathVariable Long id) {
        advertisementRepository.deleteById(id);
    }

    /**
     * This is a function to delete users, it is used by MS1 to delete users
     * in my database synchronously to delete-function in MS1
     */
    @DeleteMapping("/delete/user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        usersearchRepository.deleteById(id);
    }

    /**
     * This method returns a list of all advertisements, that we have
     */
    @GetMapping(path="/search/")
    public @ResponseBody Iterable<Advertisement> getAllAdvertisements() {
        // This returns a JSON with all produkts
        return advertisementRepository.findAll();
    }

    /**
     * Here we search a list of advertisements by a search criteria, and return JSON list. I search them in my database
     * of advertisements, which i copy from MS2, so i always have an actual list of advertisements.
     * The function saves the date and search criteria from the latest search of user for creating
     * the future notifications about relevant advertisements.
     */
    @GetMapping(value = "/search/{id}/{advertisement}")
    @ResponseBody
    public List<Advertisement> getResponseList(@PathVariable("advertisement") String searchProdukt, @PathVariable("id") Long uid)
    {
        boolean b = usersearchRepository.existsById(uid);
        if (b == false) {
            System.out.println("User doesn't exist");
            return null;
        }
        Optional<Usersearch> update2 = usersearchRepository.findById(uid);
        Usersearch update = update2.get();
        update.setLatestsearch(searchProdukt);
        Date today = new Date();
        java.sql.Date today2 = new java.sql.Date(today.getTime());
        update.setDatum(today2);
        usersearchRepository.save(update);
        List<Advertisement> choosen_advertisements = advertisementRepository.findBytitle(searchProdukt);
        return choosen_advertisements;
    }

    /**
     * This is a same function as above, but only for unregistered users. So it simply shows the list of advertisements
     * matching the search criteria
     */
    @GetMapping(value = "/search/{advertisement}")
    @ResponseBody
    public List<Advertisement> getResponseListWithouttoken(@PathVariable("advertisement") String searchProdukt)
    {
        List<Advertisement> choosen_produkts = advertisementRepository.findBytitle(searchProdukt);
        return choosen_produkts;
    }


    /**
     * Here is a notification service. It reads the latest search of a client with its date, and forms a new
     * notification with all new advertisements matching this criteria.
     */
    @GetMapping(value = "/notifications/{id}")
    @ResponseBody
    public List<Advertisement> getActuallyNotification(@PathVariable("id") Long uid){
        boolean b = usersearchRepository.existsById(uid);
        if (b == false) {
            System.out.println("User doesn't exist");
            return null;
        }
        Optional<Usersearch> update2 = usersearchRepository.findById(uid);
        Usersearch update = update2.get();
        String new_latest_search = update.getLatestsearch();
        java.sql.Date jsd = update.getDatum();
        List<Advertisement> choosen_produkts = advertisementRepository.findBytitle(new_latest_search);
        List<Advertisement> notification_possible = new ArrayList<Advertisement>(){};
        for(Advertisement example : choosen_produkts) {
            if(example.getPublishDate().after(jsd)){
                notification_possible.add(example);
            }
        }
        return notification_possible;
    }

    /**
     * This is a function, which is used by MS4 to proof the existence of new advertisements for the user, matching his
     * last search criteria. If they exist, then the function is "true", otherwise "false"
     */
    @GetMapping(value = "/proof/notifications/{id}")
    @ResponseBody
    public boolean proofNot(@PathVariable("id") Long uid){
        boolean b = usersearchRepository.existsById(uid);
        if (b == false) {
            System.out.println("User doesn't exist");
            return false;
        }
        Optional<Usersearch> update2 = usersearchRepository.findById(uid);
        Usersearch update = update2.get();
        String new_latest_search = update.getLatestsearch();
        java.sql.Date jsd = update.getDatum();
        List<Advertisement> choosen_produkts = advertisementRepository.findBytitle(new_latest_search);
        List<Advertisement> notification_possible = new ArrayList<Advertisement>(){};
        for(Advertisement example : choosen_produkts) {
            if(example.getPublishDate().after(jsd)){
                notification_possible.add(example);
            }
        }
        if (notification_possible.size() == 0) return false;
        else return true;
    }
}
