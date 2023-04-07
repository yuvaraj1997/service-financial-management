package com.yuvaraj.financial.models.controllers.v1.transaction.transactionCategory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Yuvaraj
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PostTransactionCategoryRequest {

    @JsonProperty("category")
    @NotBlank(message = "Category is mandatory")
    @Pattern(
            regexp = "^[a-zA-Z ]*$",
            message = "Category name should be valid"
    )
    String category;

}

