import { Button } from "@mui/material";
import { AuthContext } from "react-oauth2-code-pkce";
import { useContext } from "react";

function Home() {
  const { logIn, logOut, token, tokenData } = useContext(AuthContext);
  return (
    <div>
      {token && JSON.stringify(tokenData)}
      <Button
        variant="contained"
        sx={{ backgroundColor: "#dc004e", color: "#fff" }}
        onClick={() => {
          logIn();
        }}
      >
        LOGIN
      </Button>
      <Button
        variant="contained"
        sx={{ backgroundColor: "red", color: ":fff" }}
        onClick={() => {
          logOut();
        }}
      >
        LOGOUT
      </Button>
    </div>
  );
}

export default Home;
