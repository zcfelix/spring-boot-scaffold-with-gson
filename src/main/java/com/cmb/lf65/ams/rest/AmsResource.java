package com.cmb.lf65.ams.rest;

import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class AmsResource<T> {
    private T data;
    private List<Link> links = new ArrayList<>();

    public AmsResource(T data) {
        this.data = data;
    }

    public AmsResource(T data, Link... links) {
        this.data = data;
        this.links = asList(links);
    }

    public T getData() {
        return data;
    }

    public List<Link> getLinks() {
        return links;
    }
}
