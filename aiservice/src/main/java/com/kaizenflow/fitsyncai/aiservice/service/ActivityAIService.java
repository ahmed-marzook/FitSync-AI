package com.kaizenflow.fitsyncai.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaizenflow.fitsyncai.aiservice.model.document.Recommendation;
import com.kaizenflow.fitsyncai.aiservice.model.dto.Activity;
import com.kaizenflow.fitsyncai.aiservice.model.dto.Analysis;
import com.kaizenflow.fitsyncai.aiservice.model.dto.Improvement;
import com.kaizenflow.fitsyncai.aiservice.model.dto.RunningAnalysis;
import com.kaizenflow.fitsyncai.aiservice.model.dto.Suggestion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {

    private final GeminiAIService geminiAIService;

    public Recommendation generateRecommendation(Activity activity) {
        try {
            String prompt = createPromptForActivity(activity);
            String aiResponse = geminiAIService.generateRecommendation(prompt);
            RunningAnalysis geminiResponse = processGeminiResponseWithEmbeddedJson(aiResponse);
            log.info("AI response: {}", geminiResponse);
            return Recommendation.builder()
                    .activityId(activity.id())
                    .userId(activity.userId())
                    .activityType(activity.type())
                    .recommendation(geminiResponse)
                    .build();
        } catch (Exception e) {
            log.error("Error generating recommendation for activity: {}", e.getMessage(), e);
            return createDefaultRecommendation(activity);
        }
    }

    private Recommendation createDefaultRecommendation(Activity activity) {
        Analysis analysis = new Analysis("Unable to generate detailed analysis", null, null, null);
        Improvement improvement = new Improvement("general", "Continue with your current routine");
        Suggestion suggestion = new Suggestion("general", "Consider consulting a fitness professional");
        List<String> safetyPoints = List.of("Always warm up before exercise", "Stay hydrated", "Listen to your body");
        RunningAnalysis geminiResponse =
                new RunningAnalysis(analysis, List.of(improvement), List.of(suggestion), safetyPoints);
        return Recommendation.builder()
                .activityId(activity.id())
                .userId(activity.userId())
                .activityType(activity.type())
                .recommendation(geminiResponse)
                .build();
    }

    private String extractTextFromGeminiResponse(String responseJson) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseJson);

        return rootNode.path("candidates")
                .path(0)
                .path("content")
                .path("parts")
                .path(0)
                .path("text")
                .asText("")
                .replaceAll("```json\\n", "")
                .replaceAll("\\n```", "");
    }

    private RunningAnalysis processGeminiResponseWithEmbeddedJson(String responseJson) throws IOException {
        String text = extractTextFromGeminiResponse(responseJson);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(text, RunningAnalysis.class);
    }

    private String createPromptForActivity(Activity activity) {
        return String.format(
                """
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
                activity.type(), activity.duration(), activity.caloriesBurned(), activity.additionalMetrics());
    }
}
