import type { ActivityType } from "./ActivityType";

export interface CreateActivityRequest {
  userId: string;
  type: ActivityType;
  duration: number;
  caloriesBurned: number;
  startTime: string;
}
