import {
  Box,
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";

function ActivityForm({ onActivitiesAdded }: any) {
  const onSubmit = (formData: FormData) => {
    console.log("Form submitted");
    console.log("Form data:", formData.get("activityType"));
    try {
      //   await addActivity(formData.get("activityType"));
      //   setAcitvity({ type: "", duration: "", caloriesBurned: "" });
    } catch (error) {
      console.error("Error adding activity:", error);
    }
  };

  function addActivity(activity: any) {
    // Simulate an API call to add the activity
  }

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
          <MenuItem value="RUNNING">Running</MenuItem>
          <MenuItem value="CYCLING">Cycling</MenuItem>
          <MenuItem value="SWIMMING">Swimming</MenuItem>
          <MenuItem value="WALKING">Walking</MenuItem>
          <MenuItem value="WEIGHT_TRAINING">Weight Training</MenuItem>
          <MenuItem value="YOGA">Yoga</MenuItem>
          <MenuItem value="HIIT_TRAINING">HIIT Training</MenuItem>
          <MenuItem value="MOBILITY_TRAINING">Mobility Training</MenuItem>
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
