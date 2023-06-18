package helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handler for requests to Lambda function.
 */

public class App implements RequestHandler<SQSEvent, String>{

    @Override
    /*
     * Takes a String as input, and converts all characters to lowercase.
     */
    public String handleRequest(SQSEvent event, Context context) throws RuntimeException
    {
        LambdaLogger logger = context.getLogger();
//        try {
//            Thread.sleep(3000L);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        for(SQSEvent.SQSMessage msg : event.getRecords()){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                TestDlq testDlq = objectMapper.readValue(msg.getBody(), TestDlq.class);
                logger.log(testDlq.getColor());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        logger.log("hi");

        return "ho";
    }
}