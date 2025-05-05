import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.tsx";

import { Provider } from "react-redux";
import { store } from "./store/store.ts";
import { AuthProvider } from "react-oauth2-code-pkce";
import { authConfig } from "./config/authConfig.ts";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <AuthProvider authConfig={authConfig}>
      <Provider store={store}>
        <App />
      </Provider>
    </AuthProvider>
  </StrictMode>
);
