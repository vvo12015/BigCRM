package net.vrakin.user_service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.vrakin.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueValidator implements ConstraintValidator<UniqueValidation, String> {

    @Autowired
    private UserRepository userRepository;

    private String fieldName;

    @Override
    public void initialize(UniqueValidation constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()){
            return true;
        }

        switch (fieldName){
            case "email":
                return !userRepository.existsByEmail(value);
            case "name":
                return !userRepository.existsByName(value);
            default:
                throw new IllegalArgumentException("Field not supported for validation: " + fieldName);
        }
    }
}
