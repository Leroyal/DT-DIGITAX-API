package com.digitax.controller;

import com.digitax.constants.ResponseConstants;

import com.digitax.model.Relation;
import com.digitax.payload.ApiRes;
import com.digitax.repository.RelationRepository;
import com.digitax.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class RelationController {

    @Autowired
    RelationService relationService;

    @Autowired
    RelationRepository relationRepository;

    @GetMapping("/get/relations")
    public ResponseEntity<?> getRelations() {
        ArrayList<Relation> obj = new ArrayList<>(relationService.getRelation());
<<<<<<< Updated upstream
        return new ResponseEntity<>(ApiRes.success(obj).setMessage("List Relation"), HttpStatus.OK);
=======
        JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(obj, statusObj), HttpStatus.OK);
>>>>>>> Stashed changes
    }
}
