package com.cmb.lf65.ams.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

public class AmsPageResource<T> {
    private final List<T> data;
    private final Pagination pages;

    public AmsPageResource(Page<T> currentPage, Pageable pageable) {
        this.data = currentPage.getContent();
        Link selfLink = formatSelfLink(currentPage);
        Link previousLink = formatPreviousLink(currentPage, pageable);
        Link nextLink = formatNextLink(currentPage);
        Link firstLink = formatFirstLink(currentPage);
        Link lastLink = formatLastLink(currentPage);

        this.pages = new Pagination(selfLink, previousLink, nextLink, firstLink, lastLink, this.data.size(), currentPage.getTotalElements());
    }


    private Link formatSelfLink(Page<T> page) {
        return buildPageLink("page", page.getNumber(), "size", page.getSize(), Link.REL_SELF);
    }

    private Link formatPreviousLink(Page<T> page, Pageable pageable) {
        return pageable.hasPrevious() ?
                buildPageLink("page",page.getNumber()-1,"size",page.getSize(),Link.REL_PREVIOUS) : null;
    }

    private Link formatNextLink(Page<T> page) {
        return page.getNumber() + 1 < page.getTotalPages() ?
                buildPageLink("page",page.getNumber()+1,"size",page.getSize(),Link.REL_NEXT) : null;
    }

    private Link formatFirstLink(Page<T> page) {
        return buildPageLink("page", 0, "size", page.getSize(), Link.REL_FIRST);
    }

    private Link formatLastLink(Page<T> page) {
        return buildPageLink("page", page.getTotalPages()-1, "count", page.getSize(), Link.REL_LAST);
    }

    private Link buildPageLink(String pageParam, int pageIndex, String sizeParam, int size, String rel) {
        String path = createBuilder()
                .queryParam(pageParam, pageIndex + 1)
                .queryParam(sizeParam,size)
                .build()
                .toUriString();
        Link link = new Link(path,rel);
        return link;
    }

    private ServletUriComponentsBuilder createBuilder() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri();
    }
}
