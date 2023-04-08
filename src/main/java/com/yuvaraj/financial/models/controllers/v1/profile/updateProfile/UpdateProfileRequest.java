package com.yuvaraj.financial.models.controllers.v1.profile.updateProfile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuvaraj.financial.helpers.ValidationHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Yuvaraj
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateProfileRequest {

    @JsonProperty("fullName")
    @NotBlank(message = "Full name is mandatory")
    @Pattern(
            regexp = ValidationHelper.FullNameSpecs.REGEX_PATTERN,
            message = ValidationHelper.FullNameSpecs.REGEX_PATTERN_ERROR_MESSAGE
    )
    private String fullName;

    @JsonProperty("preferredName")
    @NotBlank(message = "Preferred name is mandatory")
    @Pattern(
            regexp = ValidationHelper.PreferredNameSpecs.REGEX_PATTERN,
            message = ValidationHelper.PreferredNameSpecs.REGEX_PATTERN_ERROR_MESSAGE
    )
    private String preferredName;
}
