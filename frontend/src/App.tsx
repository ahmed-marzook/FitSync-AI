import { createBrowserRouter, RouterProvider } from "react-router";
import Home from "./pages/Home/Home";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import { useDispatch } from "react-redux";
import { setCredentials, logout } from "./store/authSlice";
import Activities from "./pages/Activities/Activities";
import ActivityDetail from "./components/ActivityDetail";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
    children: [
      {
        path: "/activities",
        element: <Activities />,
      },
      {
        path: "/activities/:id",
        element: <ActivityDetail />,
      },
    ],
  },
]);

function App() {
  const { token, tokenData } = useContext(AuthContext);
  const dispatch = useDispatch();
  const [authReady, setAuthReady] = useState(false);

  useEffect(() => {
    if (token) {
      dispatch(setCredentials({ token, user: tokenData }));
      setAuthReady(true);
    } else {
      localStorage.removeItem("user");
      localStorage.removeItem("token");
      dispatch(logout());
    }
  }, [token, tokenData, dispatch]);
  return <RouterProvider router={router} />;
}

export default App;
