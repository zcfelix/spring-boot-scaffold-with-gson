package com.cmb.lf65.ams.rest;

import org.springframework.hateoas.Link;

import java.util.List;

import static java.util.Arrays.asList;

public class Pagination {
    private final long count;
    private final long total;
    private final List<Link> links;

    public Pagination(Link self, Link next, Link prev, Link first, Link last, long count, long total) {
        this.count = count;
        this.total = total;
        this.links = asList(self, next, prev, first, last);
    }
}
