package com.yuvaraj.financialManagement.models.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

import javax.validation.constraints.*;
import java.util.Arrays;

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

    private Sort sort = new Sort();

    public void setSearch(String search) {
        if (null != search) {
            this.search = search;
            return;
        }
        this.search = "";
    }

    public void setSort(Sort sort) {
        if (null != sort) {
            this.sort = sort;
            return;
        }
        this.sort = new Sort();
    }

    public Integer getPageNo() {
        return this.pageNo - 1;
    }

    public PageRequest getPageableRequest(@NotBlank String defaultSortingField, @NotNull org.springframework.data.domain.Sort.Direction defaultSortingDirection, @NotNull @NotEmpty String[] allowedSortingFields) {
        if (null == this.getSort().getDirection()) {
            this.getSort().setDirection(defaultSortingDirection);
        }

        if (null == this.getSort().getField() || this.getSort().getField().isEmpty()) {
            this.getSort().setField(defaultSortingField);
        }

        if (Arrays.stream(allowedSortingFields).noneMatch(s -> s.equals(this.getSort().getField()))) {
            this.getSort().setField(defaultSortingField);
        }

        return PageRequest.of(this.getPageNo(), this.getPageSize(), this.getSort().getDirection(), this.getSort().getField());
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Sort {

        @Pattern(regexp = "asc|desc", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid sort direction")
        private org.springframework.data.domain.Sort.Direction direction;

        private String field;

    }
}
