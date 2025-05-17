export interface ActivityRecommendation {
  id: string;
  activityId: string;
  userId: string;
  activityType: string;
  recommendation: {
    analysis: {
      overall: string;
      pace: string;
      heartRate: string;
      caloriesBurned: string;
    };
    improvements: Array<{
      area: string;
      recommendation: string;
    }>;
    suggestions: Array<{
      workout: string;
      description: string;
    }>;
    safety: string[];
  };
  createdAt: string; // ISO date string
  updatedAt: string; // ISO date string
}
