package lf65.ams.rest;

import org.springframework.hateoas.Link;

import java.util.Arrays;
import java.util.List;

public class AmsResources<T> {
    private List<T> data;
    private List<Link> links;

    public AmsResources(List<T> data, Link... links) {
        this.data = data;
        this.links = Arrays.asList(links);
    }

    public List<T> getData() {
        return data;
    }

    public List<Link> getLinks() {
        return links;
    }
}
