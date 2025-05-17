import { useParams } from "react-router";
import { useEffect, useState } from "react";
import type { ActivityRecommendation } from "../types/ActivityRecommendation";
import { getActivityRecommendation } from "../services/api";
import { Box, Card, CardContent, Divider, Typography } from "@mui/material";

function ActivityDetail() {
  const { id } = useParams<{ id: string }>();
  const [activityRecommendation, setActivityRecommendation] =
    useState<ActivityRecommendation | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (id) {
      getActivityRecommendation(id)
        .then((data) => {
          setActivityRecommendation(data);
          setLoading(false);
        })
        .catch((err) => {
          setError("Failed to fetch activity details.");
          setLoading(false);
        });
    }
  }, [id]);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  if (!activityRecommendation) {
    return <div>No activity found.</div>;
  }

  return (
    <Box sx={{ maxWidth: 800, mx: "auto", p: 2 }}>
      {/* <Card sx={{ mb: 2 }}>
        <CardContent>
          <Typography variant="h5" gutterBottom>
            Activity Details
          </Typography>
          <Typography>Type: {activity.type}</Typography>
          <Typography>Duration: {activity.duration} minutes</Typography>
          <Typography>Calories Burned: {activity.caloriesBurned}</Typography>
          <Typography>
            Date: {new Date(activity.createdAt).toLocaleString()}
          </Typography>
        </CardContent>
      </Card> */}

      {activityRecommendation && (
        <Card>
          <CardContent>
            <Typography variant="h5" gutterBottom>
              AI Recommendation
            </Typography>
            <Typography variant="h6">Analysis</Typography>
            <Typography paragraph>
              {activityRecommendation.recommendation.analysis.overall}
            </Typography>

            <Divider sx={{ my: 2 }} />

            <Typography variant="h6">Improvements</Typography>
            {activityRecommendation?.recommendation.improvements?.map(
              (improvement, index) => (
                <Typography key={index} paragraph>
                  • {improvement.recommendation}
                </Typography>
              )
            )}

            <Divider sx={{ my: 2 }} />

            <Typography variant="h6">Suggestions</Typography>
            {activityRecommendation?.recommendation.suggestions?.map(
              (suggestion, index) => (
                <Typography key={index} paragraph>
                  • {suggestion.description}
                </Typography>
              )
            )}

            <Divider sx={{ my: 2 }} />

            <Typography variant="h6">Safety Guidelines</Typography>
            {activityRecommendation?.recommendation.safety?.map(
              (safety, index) => (
                <Typography key={index} paragraph>
                  • {safety}
                </Typography>
              )
            )}
          </CardContent>
        </Card>
      )}
    </Box>
  );
}

export default ActivityDetail;
