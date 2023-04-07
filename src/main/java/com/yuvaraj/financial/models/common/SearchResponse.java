package com.yuvaraj.financial.models.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Yuvaraj
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class SearchResponse<T> {

    @JsonProperty("pageNo")
    private Integer pageNo;

    @JsonProperty("data")
    private List<T> data;

    @JsonProperty("totalCount")
    private Long totalCount;

    @JsonProperty("totalPages")
    private Integer totalPages;

    public SearchResponse<T> computeData(Page<T> entityPage) {
        this.pageNo = entityPage.getNumber() + 1;
        this.data = entityPage.getContent();
        this.totalCount = entityPage.getTotalElements();
        this.totalPages = entityPage.getTotalPages();
        return this;
    }
}
