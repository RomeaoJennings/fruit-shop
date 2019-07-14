package com.romeao.fruitshop.api.v1.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

public class BaseDto {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    final Map<String, String> links = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    final Map<String, String> actions = new HashMap<>();

    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public Map<String, String> getActions() {
        return actions;
    }
}
