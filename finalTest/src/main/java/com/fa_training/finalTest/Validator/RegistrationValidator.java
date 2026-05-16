package com.fa_training.finalTest.Validator;

import com.fa_training.finalTest.Dto.RegistrationDTO;
import org.springframework.validation.Errors;

public class RegistrationValidator {
    public static void validate(RegistrationDTO dto, Errors errors) {
        if (dto == null) return;

        if (dto.getFullName() == null || dto.getFullName().isBlank()) {
            errors.rejectValue("fullName", "fullName.empty", "Full name is required.");
        }

        if (dto.getNumLessons() == null || dto.getNumLessons() <= 0) {
            errors.rejectValue("numLessons", "numLessons.invalid",
                    "Number of lessons must be greater than 0.");
        }

        if (dto.getEffectiveDate() != null && dto.getExpirationDate() != null) {
            if (!dto.getEffectiveDate().isBefore(dto.getExpirationDate())) {
                errors.rejectValue("effectiveDate", "effectiveDate.invalid",
                        "Effective date must be earlier than expiration date.");
            }
        }

        if (dto.getCountryID() == null || dto.getCountryID().isBlank()) {
            errors.rejectValue("countryID", "countryID.empty", "Please select a country.");
        }

        if (dto.getPackageID() == null || dto.getPackageID().isBlank()) {
            errors.rejectValue("packageID", "packageID.empty", "Please select a package.");
        }
    }
}
