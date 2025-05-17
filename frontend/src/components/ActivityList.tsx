import { Card, CardContent, Grid, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { getActivities } from "../services/api";
import type { Activity } from "../types/Activity";
import { useNavigate } from "react-router";

function ActivityList() {
  const [activities, setActivities] = useState<Activity[]>([]);
  const navigation = useNavigate();

  useEffect(() => {
    const fetchActivities = async () => {
      try {
        const data = await getActivities();
        setActivities(data.content); // Update the state with fetched activities
      } catch (error) {
        console.error("Error fetching activities:", error);
      }
    };

    fetchActivities();
  }, []);

  return (
    <>
      <Grid container rowSpacing={1} columnSpacing={{ xs: 1, sm: 2, md: 3 }}>
        {activities.map((activity) => (
          <Grid
            container
            spacing={{ xs: 2, md: 3 }}
            columns={{ xs: 4, sm: 8, md: 12 }}
            key={activity.id}
          >
            <Card
              sx={{ cursor: "pointer", padding: 2, marginBottom: 2 }}
              onClick={() => navigation(`/activities/${activity.id}`)}
            >
              <CardContent>
                <Typography variant="h6">{activity.type}</Typography>
                <Typography>Duration: {activity.duration} minutes</Typography>
                <Typography>
                  Calories Burned: {activity.caloriesBurned}
                </Typography>
                <Typography>
                  Start Time: {new Date(activity.startTime).toLocaleString()}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </>
  );
}

export default ActivityList;
