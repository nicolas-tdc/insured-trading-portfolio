import { User } from "./user.model";

export interface JwtResponse {
  token: string;
  type: string;
  user: User;
}
