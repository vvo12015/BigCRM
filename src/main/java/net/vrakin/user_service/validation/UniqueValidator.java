package net.vrakin.user_service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import net.vrakin.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
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

        if (fieldName == null || fieldName.isBlank()){
            fieldName = getFieldNameFromContext(context);
        }

        return switch (fieldName) {
            case "email" -> !userRepository.existsByEmail(value);
            case "name" -> !userRepository.existsByName(value);
            default -> throw new IllegalArgumentException("Field not supported for validation: " + fieldName);
        };
    }

    private String getFieldNameFromContext(ConstraintValidatorContext context) {
        try {
            var annotationFieldName = context.unwrap(jakarta.validation.Path.class).toString();
            log.debug("Annotation for validation of unique for field name: {}", annotationFieldName);
            return annotationFieldName;
        } catch (Exception e) {
            throw new RuntimeException("Cannot determine field name for validation", e);
        }
    }
}
