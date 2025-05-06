import { Button } from "@mui/material";
import { AuthContext } from "react-oauth2-code-pkce";
import { useContext } from "react";
import { Outlet } from "react-router";
import { useNavigate } from "react-router";

function Home() {
  const { logIn, logOut, tokenData } = useContext(AuthContext);
  const navigate = useNavigate();
  return (
    <div>
      {tokenData ? (
        <>
          <Button
            variant="contained"
            sx={{ backgroundColor: "red", color: ":fff" }}
            onClick={() => {
              logOut();
              navigate("/");
            }}
          >
            LOGOUT
          </Button>
          <Outlet />
        </>
      ) : (
        <Button
          variant="contained"
          sx={{ backgroundColor: "#dc004e", color: "#fff" }}
          onClick={() => {
            logIn();
          }}
        >
          LOGIN
        </Button>
      )}
    </div>
  );
}

export default Home;
