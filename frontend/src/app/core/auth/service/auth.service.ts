import { computed, Injectable, resource, ResourceRef, Signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest, RegisterRequest, JwtResponse, User } from '../model';
import { Observable, tap } from 'rxjs';

/**
 * Service for authentication
 * 
 * @export
 */
@Injectable({ providedIn: 'root' })
export class AuthService {

  /**
   * API URL
   */
  private apiUrl: string = '/api/auth';

  /**
   * Authenticated user resource
   */
  private authUserResource: ResourceRef<User | null | undefined> = resource<User | null, { user: User | null }>({
    params: () => ({ user: this.getUser() }),
    loader: async ({ params: { user } }) => {
      if (!user || !this.isLoggedIn()) return Promise.resolve(null);
      return user;
    }
  });

  /**
   * Authenticated user
   */
  public authUser: Signal<User | null> = computed(() => this.authUserResource?.value() ?? null);

  /**
   * Clear the authenticated user
   * 
   * @returns void
   */
  clearAuthUser(): void { this.authUserResource?.set(null); }

  /**
   * Initializes the service
   * Injects the service for http handling
   * 
   * @param http HTTP client
   */
  constructor(
    private readonly http: HttpClient,
  ) { }

  /**
   * Registers a new user
   * 
   * @param registerRequest Register request
   * @returns Created user
   */
  public register(registerRequest: RegisterRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, registerRequest);
  }

  /**
   * Logs in a user
   * 
   * @param credentials Login credentials
   * @returns Logged in user
   */
  public login(credentials: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.apiUrl}/signin`, credentials).pipe(
      tap(data => {
        // Save token and user
        this.saveToken(data.token);
        this.saveUser(data.user);
      })
    );
  }

  /**
   * Logs out the user
   * 
   * @returns void
   */
  public logout(): void {
    // Clear local storage
    localStorage.removeItem('auth-token');
    localStorage.removeItem('user');
  }


  /**
   * Saves the token
   * 
   * @param token JWT token
   * @returns void
   */
  public saveToken(token: string): void {
    // Save token to local storage
    localStorage.setItem('auth-token', token);
  }

  /**
   * Gets the token
   * 
   * @returns JWT token
   */
  public getToken(): string | null {
    return localStorage.getItem('auth-token');
  }

  /**
   * Saves the user
   * 
   * @param user User
   * @returns void
   */
  public saveUser(user: User): void {
    // Save user to local storage
    localStorage.setItem('user', JSON.stringify(user));

    // Update authenticated user
    this.authUserResource?.set(user);
  }

  /**
   * Gets the user
   * 
   * @returns User
   */
  private getUser(): User | null {
    // Get user from local storage
    const userStr = localStorage.getItem('user');

    return userStr ? JSON.parse(userStr) : null;
  }

  /**
   * Checks if the user is logged in
   * 
   * @returns boolean
   */
  public isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
