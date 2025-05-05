import { createSlice } from "@reduxjs/toolkit";

import type { AuthState } from "../types/AuthState";
import type { User } from "../types/User";

const authSlice = createSlice({
  name: "auth",
  initialState: {
    user: JSON.parse(localStorage.getItem("user") || "{}") as User,
    token: localStorage.getItem("token"),
    userGuid: localStorage.getItem("userGuid"),
  } as AuthState,
  reducers: {
    setCredentials(state, action) {
      state.user = action.payload.user;
      state.token = action.payload.token;
      state.userGuid = action.payload.user?.sub;
      localStorage.setItem("user", JSON.stringify(action.payload.user));
      localStorage.setItem("token", action.payload.token);
      localStorage.setItem("userGuid", action.payload.user?.sub);
    },
    logout(state) {
      state.user = null;
      state.token = null;
      state.userGuid = null;

      localStorage.removeItem("user");
      localStorage.removeItem("token");
      localStorage.removeItem("userGuid");
    },
  },
});

export const { setCredentials, logout } = authSlice.actions;
export default authSlice.reducer;
