package com.cmb.lf65.ams.domain;

import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;

public class AmsResourceSupport {
    private List<Link> links = new ArrayList<>();

    public AmsResourceSupport add(Link link) {
        this.links.add(link);
        return this;
    }

    public void add(Link... links) {
        this.links.addAll(asList(links));
    }

    public boolean hasLinks() {
        return !this.links.isEmpty();
    }

    public boolean hasLink(String rel) {
        return this.getLink(rel) != null;
    }

    public List<Link> getLinks() {
        return this.links;
    }

    public void removeLinks() {
        this.links.clear();
    }

    public Link getLink(String rel) {
        Iterator var2 = this.links.iterator();

        Link link;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            link = (Link)var2.next();
        } while(!link.getRel().equals(rel));

        return link;
    }

    public List<Link> getLinks(String rel) {
        List<Link> relatedLinks = new ArrayList();

        for (Link link : this.links) {
            if (link.getRel().equals(rel)) {
                relatedLinks.add(link);
            }
        }

        return relatedLinks;
    }

}
