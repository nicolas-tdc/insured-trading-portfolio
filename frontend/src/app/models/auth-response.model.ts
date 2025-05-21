export interface LoginRequest {
  email: string;
  password: string;
}

export interface SignupRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface JwtResponse {
  token: string;
  type: string;
  id: string;
  email: string;
  roles: string[];
}

export interface MessageResponse {
  message: string;
}
