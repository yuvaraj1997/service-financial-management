package com.yuvaraj.financialManagement.models.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Yuvaraj
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class SearchRequest {

    @NotNull(message = "Page no is mandatory")
    @Min(value = 1, message = "Page no is invalid")
    private Integer pageNo;

    @NotNull(message = "Page size is mandatory")
    @Min(value = 5, message = "Minimum page size 5")
    @Max(value = 20, message = "Maximum page size 20")
    private Integer pageSize;

    private String search = "";

    private Sort sort;

    public void cleanRequest() {
        if (null == this.search) {
            this.search = "";
        }

        if (null == this.sort) {
            this.sort = new Sort();
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Sort {

        private org.springframework.data.domain.Sort.Direction direction;

        private String field;

    }
}
