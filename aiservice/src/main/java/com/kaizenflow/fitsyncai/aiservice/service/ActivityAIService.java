package com.kaizenflow.fitsyncai.aiservice.service;

import com.kaizenflow.fitsyncai.aiservice.model.dto.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {

    private final GeminiAIService geminiAIService;

    public String generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiAIService.generateRecommendation(prompt);
        log.info("AI response: {}", aiResponse);
        return aiResponse;
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
