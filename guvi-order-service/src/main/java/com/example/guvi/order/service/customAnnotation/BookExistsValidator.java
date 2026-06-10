package com.example.guvi.order.service.customAnnotation;

import com.example.guvi.order.service.dto.response.OrderResponseDto;
import com.example.guvi.order.service.feignClient.BookClient;
import feign.FeignException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookExistsValidator implements ConstraintValidator<BookExists, Long> {

    private final BookClient bookClient;

    @Override
    public boolean isValid(Long bookId, ConstraintValidatorContext constraintValidatorContext) {

        if (bookId == null) {
            return true;
        }

        try{
            bookClient.getBookById(bookId.intValue());
            return true;
        }catch (FeignException e){
            return false;
        }
    }
}
