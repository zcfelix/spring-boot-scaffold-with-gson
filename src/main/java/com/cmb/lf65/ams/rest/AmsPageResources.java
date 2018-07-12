package com.cmb.lf65.ams.rest;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

public class AmsPageResources<T> {

    private static final String PAGE_PARAM = "page";
    private static final String SIZE_PARAM = "size";
    private final List<AmsResource<T>> data;
    private final Pagination pages;

    public AmsPageResources(Page<AmsResource<T>> currentPage) {
        this.data = currentPage.getContent();
        Link selfLink = formatSelfLink(currentPage);
        Link previousLink = formatPreviousLink(currentPage);
        Link nextLink = formatNextLink(currentPage);
        Link firstLink = formatFirstLink(currentPage);
        Link lastLink = formatLastLink(currentPage);

        this.pages = new Pagination(selfLink, previousLink, nextLink, firstLink, lastLink, this.data.size(), currentPage.getTotalElements());
    }

    public List<AmsResource<T>> getData() {
        return data;
    }

    public Pagination getPages() {
        return pages;
    }

    private Link formatSelfLink(Page<AmsResource<T>> page) {
        return buildPageLink(PAGE_PARAM, page.getNumber(), SIZE_PARAM, page.getSize(), Link.REL_SELF);
    }

    private Link formatPreviousLink(Page<AmsResource<T>> page) {
        return page.getNumber() > 0 ?
                buildPageLink(PAGE_PARAM, page.getNumber() - 1, SIZE_PARAM, page.getSize(), Link.REL_PREVIOUS) : null;
    }

    private Link formatNextLink(Page<AmsResource<T>> page) {
        return page.getNumber() + 1 < page.getTotalPages() ?
                buildPageLink(PAGE_PARAM, page.getNumber() + 1, SIZE_PARAM, page.getSize(), Link.REL_NEXT) : null;
    }

    private Link formatFirstLink(Page<AmsResource<T>> page) {
        return buildPageLink(PAGE_PARAM, 0, SIZE_PARAM, page.getSize(), Link.REL_FIRST);
    }

    private Link formatLastLink(Page<AmsResource<T>> page) {
        return buildPageLink(PAGE_PARAM, page.getTotalPages() - 1, "count", page.getSize(), Link.REL_LAST);
    }

    private Link buildPageLink(String pageParam, int pageIndex, String sizeParam, int size, String rel) {
        String path = createBuilder()
                .queryParam(pageParam, pageIndex + 1)
                .queryParam(sizeParam, size)
                .build()
                .toUriString();
        Link link = new Link(path, rel);
        return link;
    }

    private ServletUriComponentsBuilder createBuilder() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri();
    }

}
