package edu.java.controller;

import edu.java.AddLinkRequest;
import edu.java.ApiErrorResponse;
import edu.java.LinkResponse;
import edu.java.ListLinksResponse;
import edu.java.RemoveLinkRequest;
import edu.java.dto.LinkDto;
import edu.java.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Tag(name = "links", description = "the links API")
@RestController
@RequestMapping("/links")
public class LinksController {
    private final LinkService linkService;

    public LinksController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Operation(
        operationId = "linksGet",
        summary = "Получить все отслеживаемые ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылки успешно получены", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ListLinksResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @GetMapping
    public ResponseEntity<ListLinksResponse> getLinks(
        @NotNull
        @Parameter(name = "Tg-Chat-Id", required = true, in = ParameterIn.HEADER)
        @RequestHeader("Tg-Chat-Id")
        Long tgChatId
    ) {

        List<LinkDto> linkDtos = linkService.listAll(tgChatId);
        ListLinksResponse response = new ListLinksResponse(linkDtos.stream()
            .map(e -> new LinkResponse(Integer.parseInt(e.getId().toString()), e.getUrl())).toList(), linkDtos.size());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
        operationId = "linksPost",
        summary = "Добавить отслеживание ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @PostMapping
    public ResponseEntity<LinkResponse> trackLink(
        @NotNull
        @Parameter(name = "Tg-Chat-Id", required = true, in = ParameterIn.HEADER)
        @RequestHeader("Tg-Chat-Id")
        Long tgChatId,
        @Parameter(name = "AddLinkRequest", required = true)
        @Valid
        @RequestBody
        AddLinkRequest addLinkRequest
    ) {
        // TODO: check how to use mappers
        LinkDto linkDto = linkService.add(tgChatId, addLinkRequest.link());
        LinkResponse response = new LinkResponse(Math.toIntExact(linkDto.getId()), linkDto.getUrl());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
        operationId = "linksDelete",
        summary = "Убрать отслеживание ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Ссылка не найдена", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @DeleteMapping
    public ResponseEntity<LinkResponse> untrackLink(
        @NotNull
        @Parameter(name = "Tg-Chat-Id", required = true, in = ParameterIn.HEADER)
        @RequestHeader(value = "Tg-Chat-Id")
        Long tgChatId,
        @Parameter(name = "RemoveLinkRequest", required = true)
        @Valid
        @RequestBody
        RemoveLinkRequest removeLinkRequest
    ) {
//        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        LinkDto linkDto = linkService.remove(tgChatId, removeLinkRequest.request());

        LinkResponse response = new LinkResponse(Math.toIntExact(linkDto.getId()), linkDto.getUrl());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
