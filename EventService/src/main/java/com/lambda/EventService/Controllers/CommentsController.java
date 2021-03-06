package com.lambda.EventService.Controllers;


import com.lambda.EventService.ExceptionHandling.CustomEventException;
import com.lambda.EventService.Helpers.UserServiceHelper;
import com.lambda.EventService.Models.Entity.Event;
import com.lambda.EventService.Models.Entity.EventComments;
import com.lambda.EventService.Services.IEventCommentsService;
import com.lambda.EventService.Services.IEventService;
import com.lambda.EventService.Grpc.GRPCEventServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("comment")
public class CommentsController {
    @Autowired
    IEventCommentsService commentService;
    @Autowired
    IEventService eventService;
    @Autowired
    UserServiceHelper userServiceHelper;

    @Autowired
    GRPCEventServiceClient grpcEventServiceClient;

    @GetMapping(path = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public EventComments getEventComment(@PathVariable long id)throws  Exception{
        grpcEventServiceClient.createSystemEvent(null);
        return commentService.findById(id);
    }

    @GetMapping(path = "/user/{userId}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<EventComments> getEventCommentByUserId(@PathVariable Long userId, @RequestHeader(value = "Authorization") String authorizationToken) throws Exception{
        if(userServiceHelper.CheckUserAuthorised(userId.toString(), authorizationToken)) {
            return commentService.findByUserId(userId);
        }
        throw new CustomEventException("403: User with ID="+userId.toString()+" is unauthorized.");
    }

    @GetMapping(path = "/containing/{string}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<EventComments> getEventCommentsByContainingStringInComment(@PathVariable String string) throws Exception{
        return commentService.findByTextContainingIgnoreCase(string);
    }

    @GetMapping(path = "/event/{eventId}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<EventComments> getEventComentsByEventId(@PathVariable long eventId) throws Exception{
        var event = eventService.findById(eventId);
        return event.getEventCommentsList();
    }

    @PostMapping(path = "/post-comment/{eventId}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public Event postCommentOnEvent(@PathVariable long eventId, @RequestBody  @org.jetbrains.annotations.NotNull EventComments comment, @RequestHeader(value = "Authorization") String authorizationToken) throws Exception{
        if(userServiceHelper.CheckUserAuthorised(comment.getUserId().toString(), authorizationToken)) {
            var event = eventService.findById(eventId);
            comment.setEvent(event);
            commentService.createEventComments(comment);
            return eventService.findById(eventId);
        }
        throw new CustomEventException("500: Unexpected outcome of method postCommentOnEvent.");
    }

}
