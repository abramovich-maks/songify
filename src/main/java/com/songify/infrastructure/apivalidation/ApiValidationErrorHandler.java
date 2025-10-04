package com.songify.infrastructure.apivalidation;

import com.songify.domain.crud.AlbumNotEmptyException;
import com.songify.domain.crud.AlbumNotFoundException;
import com.songify.infrastructure.crud.album.DeleteAlbumResponseDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
class ApiValidationErrorHandler {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiValidationErrorResponseDto handleValidationException(MethodArgumentNotValidException exception) {
        List<String> messages = getErrorsFromExceptions(exception);
        return new ApiValidationErrorResponseDto(messages, HttpStatus.BAD_REQUEST);
    }

    private List<String> getErrorsFromExceptions(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(AlbumNotEmptyException.class)
    public ResponseEntity<DeleteAlbumResponseDto> handleAlbumNotEmpty(AlbumNotEmptyException ex) {
        DeleteAlbumResponseDto body = new DeleteAlbumResponseDto(
                ex.getMessage(),
                HttpStatus.CONFLICT
        );
        return ResponseEntity.status(body.status()).body(body);
    }

    @ExceptionHandler(AlbumNotFoundException.class)
    public ResponseEntity<DeleteAlbumResponseDto> handleAlbumNotFound(AlbumNotFoundException ex) {
        DeleteAlbumResponseDto body = new DeleteAlbumResponseDto(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
        return ResponseEntity.status(body.status()).body(body);
    }
}
