/**
 * Controller layer for REST server
 *
 * Handles HTTP interactions between the client and server
 *
 * @Author Ewan Lewis
 */

package com.example.MountainServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MountainController {

    /**
     * Mountain Service, that all methods within controller access
     * to update mountain information
     */
    @Autowired
    private final MountainService MOUNTAIN_SERVICE;

    /**
     * Constructor for MountainController
     * @param ms this
     */
    public MountainController(MountainService ms) {
        this.MOUNTAIN_SERVICE = ms;
    }

    /**
     * Adds mountains to list of mountains
     * @param mountains List of mountains
     * @return Response with status
     */
    @PostMapping("/")
    public ResponseEntity<String> addMountains(@RequestBody List<Mountain> mountains) {
        if (!MOUNTAIN_SERVICE.addMountains(mountains)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Updates mountain information
     * @param id ID of mountain
     * @param mountain New mountain information
     * @return Response with status
     */
    @PutMapping("/mountains/update/{id}")
    public ResponseEntity<String> updateMountains(@PathVariable int id, @RequestBody Mountain mountain){

        boolean success = MOUNTAIN_SERVICE.updateMountain(id, mountain);

        if (!success){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Deletes a mountain
     * @param id ID of mountain
     * @return Response with status
     */
    @PutMapping("/mountains/delete")
    public ResponseEntity<String> deleteMountain(@RequestBody int id){
        boolean success = MOUNTAIN_SERVICE.deleteMountain(id);

        if (success){
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Gets information on mountains
     * @param allParams Queries being passed through the URL
     * @return Response with status
     */
    @GetMapping("/mountains")
    public ResponseEntity<List<Mountain>> getMountain(@RequestParam Map<String, String> allParams) {
        List<Mountain> mountains = parseParamsAndFetch(allParams);
        return ResponseEntity.ok().body(mountains);
    }

    /**
     * Parses the arguments in the URL, and directs the flow to the
     * desired function
     * @param params Parameters in URL
     * @return List of Mountains being searched for
     */
    private List<Mountain> parseParamsAndFetch(Map<String, String> params) {
        // If looking for all mountains
        if (params.isEmpty()) {
            return MOUNTAIN_SERVICE.getAllMountains();
        }

        // If looking for just ID
        if (params.containsKey("id")) {
            int id = Integer.parseInt(params.get("id"));
            return MOUNTAIN_SERVICE.getById(id);
        }

        // If looking for country & range
        if (params.containsKey("country") && params.containsKey("range") && params.containsKey("name")) {
            return MOUNTAIN_SERVICE.getByName(params.get("country"), params.get("range"), params.get("name"));
        }

        // If looking for country & altitude
        if (params.containsKey("country") && params.containsKey("alt")) {
            int altitude = Integer.parseInt(params.get("alt"));
            return MOUNTAIN_SERVICE.getByCountryAltitude(params.get("country"), altitude);
        }

        // If looking for country & range & name
        if (params.containsKey("country") && params.containsKey("range")) {
            return MOUNTAIN_SERVICE.getByCountryAndRange(params.get("country"), params.get("range"));
        }

        // If looking for specific hemisphere
        if (params.containsKey("north")) {
            boolean isNorthern = Boolean.parseBoolean(params.get("north"));
            return MOUNTAIN_SERVICE.getByHemisphere(isNorthern);
        }

        // If looking for specific country
        if (params.containsKey("country")) {
            return MOUNTAIN_SERVICE.getMountainsByCountry(params.get("country"));
        }

        // Return empty ArrayList of Mountains if nothing found
        return new ArrayList<Mountain>();
    }
}

