package com.lambda.NotificationService.Grpc;

import com.lambda.grpc.systemevent.SystemEventMessage;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@NoArgsConstructor
public class CustomInterceptor extends HandlerInterceptorAdapter {
    GRPCNotificationServiceClient grpcNotificationServiceClient;
    public CustomInterceptor(GRPCNotificationServiceClient client){
        this.grpcNotificationServiceClient =  client;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception
    {
        String authToken = request.getHeader("Authorization");
        if (authToken == null) {
            authToken = "";
        }
        var systemEventMessage = SystemEventMessage.newBuilder().setUserAuthToken(authToken).
                setTimeStamp(LocalDateTime.now().toString()).setActionResult(Integer.toString(response.getStatus())).
                setActionType(request.getMethod()).setServiceName("NotificationService").setResourceObject(request.getRequestURI()).build();

        grpcNotificationServiceClient.createSystemEvent(systemEventMessage);
        System.out.println("MINIMAL: INTERCEPTOR AFTERCOMPLETION CALLED");
    }
}
