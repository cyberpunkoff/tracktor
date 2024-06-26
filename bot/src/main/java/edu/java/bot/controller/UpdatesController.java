package edu.java.bot.controller;

import edu.java.ApiErrorResponse;
import edu.java.LinkUpdateRequest;
import edu.java.bot.service.LinkUpdater;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Tag(name = "updates", description = "the updates API")
@RestController
@RequestMapping("/updates")
@RequiredArgsConstructor
@Slf4j
public class UpdatesController {
    private final LinkUpdater linkUpdaterService;

    @Operation(
        operationId = "updatesPost",
        summary = "Отправить обновление",
        responses = {
            @ApiResponse(responseCode = "200", description = "Обновление обработано"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @PostMapping
    // TODO: get rid of ResponseEntities
    public ResponseEntity<Void> sendUpdates(
        @Parameter(name = "LinkUpdateRequest", required = true)
        @Valid
        @RequestBody
        LinkUpdateRequest linkUpdateRequest
    ) {
        linkUpdaterService.update(linkUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
