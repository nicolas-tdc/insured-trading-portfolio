import { User } from "./user.model";

/**
 * JWT response model
 * 
 * @export
 */
export interface JwtResponse {
  token: string;
  type: string;
  user: User;
}
