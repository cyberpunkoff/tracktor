package edu.java.controller;

import edu.java.ApiErrorResponse;
import edu.java.service.TelegramChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Tag(name = "tg-chat", description = "the tg-chat API")
@RestController
@RequestMapping("/tg-chat")
public class TelegramChatController {
    private final TelegramChatService telegramChatService;

    public TelegramChatController(TelegramChatService telegramChatService) {
        this.telegramChatService = telegramChatService;
    }

    @Operation(
        operationId = "tgChatIdPost",
        summary = "Зарегистрировать чат",
        responses = {
            @ApiResponse(responseCode = "200", description = "Чат зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @PostMapping("/{id}")
    public ResponseEntity<Void> addChat(
        @Parameter(name = "id", required = true, in = ParameterIn.PATH)
        @PathVariable
        Long id
    ) {
        telegramChatService.register(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH)
        @PathVariable
        Long id
    ) {
        telegramChatService.unregister(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
