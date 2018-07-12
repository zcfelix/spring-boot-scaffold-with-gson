package com.cmb.lf65.ams.rest;

import org.springframework.hateoas.Link;

import java.util.Arrays;
import java.util.List;

public class AmsResources<T> {
    private List<AmsResource<T>> resources;
    private List<Link> links;

    public AmsResources(List<AmsResource<T>> resources, List<Link> links) {
        this.resources = resources;
        this.links = links;
    }

    public List<AmsResource<T>> getResources() {
        return resources;
    }

    public List<Link> getLinks() {
        return links;
    }
}
