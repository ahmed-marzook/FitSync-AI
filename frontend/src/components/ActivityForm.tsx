import {
  Box,
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
} from "@mui/material";

function ActivityForm() {
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
          labelId="activity-type-label"
          id="activity-type"
          name="activityType"
        >
          <MenuItem value="running">Running</MenuItem>
          <MenuItem value="cycling">Cycling</MenuItem>
          <MenuItem value="swimming">Swimming</MenuItem>
        </Select>
        <Button
          type="submit"
          variant="contained"
          color="primary"
          sx={{ mt: 2 }}
        >
          Submit
        </Button>
      </FormControl>
    </Box>
  );
}

export default ActivityForm;
