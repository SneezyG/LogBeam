package com.example.logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.services.timestreamwrite.TimestreamWriteClient;
import software.amazon.awssdk.services.timestreamwrite.model.*;

import java.util.*;

public class LoggerHandler implements RequestHandler<Map<String, Object>, String> {

    private final TimestreamWriteClient client = TimestreamWriteClient.builder().build();

    @Override
    public String handleRequest(Map<String, Object> event, Context context) {
        try {
            Map<String, Object> payload;

            // Determine if it came via API Gateway (direct) or IoT Core
            if (event.containsKey("deviceId")) {
                payload = event; // API Gateway
            } else if (event.containsKey("message")) {
                payload = (Map<String, Object>) event.get("message"); // IoT Core
            } else {
                return "Invalid input format";
            }

            String deviceId = (String) payload.get("deviceId");
            Double temperature = Double.parseDouble(payload.get("temperature").toString());
            Double humidity = Double.parseDouble(payload.get("humidity").toString());
            String timestamp = (String) payload.get("timestamp");

            List<Dimension> dimensions = List.of(
                Dimension.builder().name("deviceId").value(deviceId).build()
            );

            List<Record> records = List.of(
                Record.builder()
                        .dimensions(dimensions)
                        .measureName("temperature")
                        .measureValue(String.valueOf(temperature))
                        .measureValueType(MeasureValueType.DOUBLE)
                        .time(timestamp)
                        .build(),
                Record.builder()
                        .dimensions(dimensions)
                        .measureName("humidity")
                        .measureValue(String.valueOf(humidity))
                        .measureValueType(MeasureValueType.DOUBLE)
                        .time(timestamp)
                        .build()
            );

            WriteRecordsRequest request = WriteRecordsRequest.builder()
                    .databaseName("IoTLogsDB")
                    .tableName("SensorData")
                    .records(records)
                    .build();

            client.writeRecords(request);
            return "Log written to Timestream";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
