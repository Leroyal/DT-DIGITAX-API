package com.digitax.service;

import com.digitax.model.Relation;

import java.util.List;

public interface RelationService {

    public Relation findUserByName(String name);

    public void saveRelation(Relation relation);

    public List<Relation> getRelation();
}