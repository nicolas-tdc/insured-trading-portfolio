export interface JwtResponse {
  token: string;
  type: string;
  id: string;
  email: string;
  roles: string[];
}
