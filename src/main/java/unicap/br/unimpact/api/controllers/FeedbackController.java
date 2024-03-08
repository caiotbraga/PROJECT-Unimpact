package unicap.br.unimpact.api.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicap.br.unimpact.api.config.documentation.swagger.ApiRoleAccessNotes;
import unicap.br.unimpact.api.dtos.request.FeedbackRequest;
import unicap.br.unimpact.api.dtos.response.FeedbackResponse;
import unicap.br.unimpact.domain.enums.MessageEnum;
import unicap.br.unimpact.service.services.feedback.FeedbackService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    @Autowired
    FeedbackService service;


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register/{statusId}")
    @ApiOperation(value = "register feedback", response = FeedbackResponse.class)
    public ResponseEntity<?> register(@PathVariable(value = "statusId") Long statusId, @RequestBody FeedbackRequest request) {

        return service.getResponseByEntity(service.register(statusId, request), MessageEnum.REGISTERED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{feedbackId}")
    @ApiOperation(value = "return feedback", response = FeedbackResponse.class)
    public ResponseEntity<?> get(@PathVariable("feedbackId") Long feedbackId) {

        return service.getResponseByEntity(service.get(feedbackId), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/get/by/status")
    @ApiOperation(value = "return all feedbacks by status", response = FeedbackResponse.class, responseContainer = "List")
    public ResponseEntity<?> getByStatus(@RequestParam(value = "statusId") Long statusId) {

        return service.getResponseByList(service.getAllByStatusId(statusId), MessageEnum.FOUND);
    }

}
