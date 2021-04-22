package com.digitax.controller;

import com.digitax.constants.Constants;
import com.digitax.constants.HttpStatusCode;
import com.digitax.model.Relation;
import com.digitax.payload.ApiRes;
import com.digitax.repository.RelationRepository;
import com.digitax.service.RelationService;
import org.json.simple.JSONObject;
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

    @SuppressWarnings("unchecked")
    @GetMapping("/get/relations")
    public ResponseEntity<?> getRelations() {
        ArrayList<Relation> relations = new ArrayList<>(relationService.getRelation());
        // status object
        JSONObject statusObj = new JSONObject();
        statusObj.put(Constants.MESSAGE, HttpStatusCode.SUCCESS);
        statusObj.put(Constants.STATUS_CODE, HttpStatusCode.SUCCESS.code());
        // return response entity
        return new ResponseEntity<>(ApiRes.success(relations, statusObj), HttpStatus.OK);
    }
}
