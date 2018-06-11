package lf65.ams.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

public class AmsPage<T> {
    private List<T> data;
    private Pagination pages;

    public AmsPage(Page<T> currentPage, Pageable pageable) {
        this.data = currentPage.getContent();
        Link selfLink = formatSelfLink(currentPage, pageable);
        Link previousLink = formatPreviousLink(currentPage, pageable);
        Link nextLink = formatNextLink(currentPage);
        Link firstLink = formatFirstLink(currentPage);
        Link lastLink = formatLastLink(currentPage);

        this.pages = new Pagination(selfLink, previousLink, nextLink, firstLink, lastLink, this.data.size(), currentPage.getTotalElements());
    }


    private Link formatSelfLink(Page<T> page, Pageable pageable) {
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

    private Link buildPageLink(String pageParam,int page,String sizeParam,int size,String rel) {
        String path = createBuilder()
                .queryParam(pageParam,page)
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
