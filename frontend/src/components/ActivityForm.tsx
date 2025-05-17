import {
  Box,
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
import type { CreateActivityRequest } from "../types/CreateActivityRequest ";
import { ALL_ACTIVITY_TYPES, type ActivityType } from "../types/ActivityType";
import { addActivity } from "../services/api";

function ActivityForm({ onActivitiesAdded }: any) {
  const onSubmit = async (formData: FormData) => {
    console.log("Form submitted");
    console.log("Form data:", formData.get("activityType"));
    const newActivity: CreateActivityRequest = {
      userId: "12345",
      type: formData.get("activityType") as ActivityType,
      duration: parseInt(formData.get("duration") as string, 10),
      caloriesBurned: parseInt(formData.get("caloriesBurned") as string, 10),
      startTime: new Date().toISOString(),
    };
    console.log("New activity:", newActivity);
    try {
      await addActivity(newActivity);
      onActivitiesAdded();
    } catch (error) {
      console.error("Error adding activity:", error);
    }
  };

  return (
    <Box component="form" action={onSubmit} sx={{ mb: 4 }}>
      <FormControl fullWidth sx={{ mb: 2 }}>
        <InputLabel id="activity-type-label">Activity Type</InputLabel>
        <Select
          label="Activity Type"
          labelId="activity-type-label"
          id="activity-type"
          name="activityType"
        >
          {ALL_ACTIVITY_TYPES.map((activityType) => (
            <MenuItem key={activityType} value={activityType}>
              {activityType.replace(/_/g, " ").toLowerCase()}
            </MenuItem>
          ))}
        </Select>
        <TextField
          label="Duration (minutes)"
          name="duration"
          type="number"
          fullWidth
          sx={{ mt: 2 }}
        />
        <TextField
          label="Calories Burned"
          name="caloriesBurned"
          type="number"
          fullWidth
          sx={{ mt: 2 }}
        />
        <Button
          type="submit"
          variant="contained"
          color="primary"
          sx={{ mt: 2 }}
        >
          Add Activity
        </Button>
      </FormControl>
    </Box>
  );
}

export default ActivityForm;
