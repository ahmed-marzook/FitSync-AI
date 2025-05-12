import type { ActivityType } from "./ActivityType";

export interface ActivityMetrics {
  // Add specific metrics properties here as needed
  [key: string]: unknown;
}

export interface Activity {
  id: string;
  userId: string;
  type: ActivityType;
  duration: number;
  caloriesBurned: number;
  startTime: string;
  additionalMetrics: ActivityMetrics | null;
  createdAt: string;
  updatedAt: string;
}
