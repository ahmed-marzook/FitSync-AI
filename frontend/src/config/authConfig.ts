import type {
  TAuthConfig,
  TRefreshTokenExpiredEvent,
} from "react-oauth2-code-pkce";

export const authConfig: TAuthConfig = {
  clientId: "oauth2-pkce-client",
  authorizationEndpoint:
    "http://localhost:8080/realms/fitsyncai-oauth2/protocol/openid-connect/auth",
  tokenEndpoint:
    "http://localhost:8080/realms/fitsyncai-oauth2/protocol/openid-connect/token",
  redirectUri: "http://localhost:5173/activities",
  scope: "profile email offline_access openid",
  onRefreshTokenExpire: (event: TRefreshTokenExpiredEvent) => event.logIn(),
};
