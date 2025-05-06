import { Box } from "@mui/material";
import ActivityForm from "../../components/ActivityForm";
import ActivityList from "../../components/ActivityList";

function Activities() {
  return (
    <Box component="section" sx={{ p: 2, border: "1px dashed grey" }}>
      <ActivityForm />
      <ActivityList />
    </Box>
  );
}

export default Activities;
