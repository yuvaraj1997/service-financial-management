package com.yuvaraj.financial.helpers;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationHelper {

    private ValidationHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static void checkNotNullAndNotEmpty(String value, String errorMessage) throws InvalidArgumentException {
        if (null == value || value.isEmpty()) {
            throw new InvalidArgumentException(errorMessage, ErrorCode.INVALID_ARGUMENT);
        }
    }

    public static class FullNameSpecs {

        public static final String REGEX_PATTERN = "^[a-zA-Z/.@'\\- ]*$";
        public static final String REGEX_PATTERN_ERROR_MESSAGE = "Full name should be valid";

        private FullNameSpecs() {
            throw new IllegalStateException("Utility class");
        }

    }

    public static class PreferredNameSpecs {

        public static final String REGEX_PATTERN = "^[a-zA-Z/.@'\\- ]*$";
        public static final String REGEX_PATTERN_ERROR_MESSAGE = "Preferred name should be valid";

        private PreferredNameSpecs() {
            throw new IllegalStateException("Utility class");
        }

    }

    public static class PasswordSpecs {

        public static final String REGEX_PATTERN = "^(?=.*[A-Z])(?=.*[$%!#&@])(?=.*[0-9])(?=.*[a-z]).{8,128}$";
        public static final String REGEX_PATTERN_ERROR_MESSAGE = "Please use a strong password. \n-Length should be at least 8.\n-Should have Uppercase\n-Should have Lowercase\n-Should have special character allowed ($%!#&@)";

        private PasswordSpecs() {
            throw new IllegalStateException("Utility class");
        }

    }
}
