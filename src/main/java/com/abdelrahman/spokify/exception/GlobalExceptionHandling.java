package com.abdelrahman.spokify.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.abdelrahman.spokify.dto.ApiResponse;


@ControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {

    // 1. التعامل مع الأخطاء العامة (Runtime)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(false, ex.getMessage()));
    }

    // 2. خطأ الـ "Email already exists"
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handAlreadyExistsException(AlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(false, ex.getMessage()));
    }

    // 3. خطأ "Not Found" (سواء يوزر أو تسجيل)
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ApiResponse> handlingRecordNotFound(RecordNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(false, ex.getMessage()));
    }

    // 4. أخطاء الـ Validation (مثلاً إيميل غلط أو حقل ناقص)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
        // تجميع كل أخطاء الـ Validation في رسالة واحدة زي Node.js
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        
        return ResponseEntity.status(status)
                .body(new ApiResponse(false, errorMessage));
    }

    // 5. خطأ الـ Security (Unauthorized)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse(false, "Invalid email or password"));
    }

    // 6. الـ Catch-all لأي مصيبة غير متوقعة (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalException(Exception ex) {
        // بنبعت رسالة عامة وفي حقل الـ error بنبعت السبب الحقيقي للـ Debugging
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Internal server error", null, null, ex.getMessage()));
    }
}
