import axios from "axios";

import type { CreateActivityRequest } from "../types/CreateActivityRequest ";

const API_URL = "http://localhost:8084/api/v1/";

const apiClient = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 10000,
});

apiClient.interceptors.request.use(
  (config) => {
    // Get token from localStorage
    const token = localStorage.getItem("token");

    // If token exists, add it to the Authorization header
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }

    // If user ID exists in localStorage, add it as a custom header
    const user = JSON.parse(localStorage.getItem("user") || "{}");
    if (user && user.sub) {
      config.headers["X-User-ID"] = user.sub;
    }

    return config;
  },
  (error) => {
    // Handle request errors
    return Promise.reject(error);
  }
);

export const getActivities = async () => {
  try {
    const response = await apiClient.get("/activities");
    return response.data;
  } catch (error) {
    console.error("Error fetching activities:", error);
    throw error;
  }
};

export const addActivity = async (activity: CreateActivityRequest) => {
  try {
    activity.userId = JSON.parse(localStorage.getItem("user") || "{}").sub;
    const response = await apiClient.post("/activities", activity);
    return response.data;
  } catch (error) {
    console.error("Error adding activity:", error);
    throw error;
  }
};

export const deleteActivity = async (activityId) => {
  try {
    const response = await apiClient.delete(`/activities/${activityId}`);
    return response.data;
  } catch (error) {
    console.error("Error deleting activity:", error);
    throw error;
  }
};

export const getActivityDetails = async (activityId) => {
  try {
    const response = await apiClient.get(`/activities/${activityId}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching activity details:", error);
    throw error;
  }
};
