package com.kaizenflow.fitsyncai.aiservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaizenflow.fitsyncai.aiservice.model.dto.Activity;
import com.kaizenflow.fitsyncai.aiservice.model.dto.RunningAnalysis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {

    private final GeminiAIService geminiAIService;

    public RunningAnalysis generateRecommendation(Activity activity) throws IOException {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiAIService.generateRecommendation(prompt);
        RunningAnalysis geminiResponse = processGeminiResponseWithEmbeddedJson(aiResponse);
        log.info("AI response: {}", geminiResponse);
        return geminiResponse;
    }

    private String extractTextFromGeminiResponse(String responseJson) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseJson);

        return rootNode
                .path("candidates")
                .path(0)
                .path("content")
                .path("parts")
                .path(0)
                .path("text")
                .asText("").replaceAll("```json\\n", "").replaceAll("\\n```", "");
    }

    private RunningAnalysis processGeminiResponseWithEmbeddedJson(String responseJson) throws IOException {
        String text = extractTextFromGeminiResponse(responseJson);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(text, RunningAnalysis.class);
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
        {
          "analysis": {
            "overall": "Overall analysis here",
            "pace": "Pace analysis here",
            "heartRate": "Heart rate analysis here",
            "caloriesBurned": "Calories analysis here"
          },
          "improvements": [
            {
              "area": "Area name",
              "recommendation": "Detailed recommendation"
            }
          ],
          "suggestions": [
            {
              "workout": "Workout name",
              "description": "Detailed workout description"
            }
          ],
          "safety": [
            "Safety point 1",
            "Safety point 2"
          ]
        }

        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        Additional Metrics: %s
        
        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
        Ensure the response follows the EXACT JSON format shown above.
        """,
                activity.type(),
                activity.duration(),
                activity.caloriesBurned(),
                activity.additionalMetrics()
        );
    }
}
