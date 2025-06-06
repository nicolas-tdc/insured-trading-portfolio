import { computed, Injectable, resource } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest, RegisterRequest, JwtResponse, User } from './model';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {

  // Properties

  private apiUrl = '/api/auth';

  private authUserResource = this.createAuthUserResource();
  public authUser = computed(() => this.authUserResource?.value() ?? null);

  clearAuthUser(): void {
    this.authUserResource.set(null);
  }

  // Resources

  createAuthUserResource() {
    return resource({
      request: () => ({ user: this.getUser() }),
      loader: async ({ request }) => {
        if (!request.user) return Promise.resolve(null);

        return request.user;
      },
    });
  }

  // Lifecycle

  constructor(
    private http: HttpClient,
  ) { }

  // API

  public register(registerRequest: RegisterRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, registerRequest);
  }

  public login(credentials: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.apiUrl}/signin`, credentials).pipe(
      tap(data => {
        this.saveToken(data.token);
        this.saveUser(data.user);
      })
    );
  }

  // Local storage

  public logout(): void {
    localStorage.removeItem('auth-token');
    localStorage.removeItem('user');
  }


  public saveToken(token: string): void {
    localStorage.setItem('auth-token', token);
  }

  public getToken(): string | null {
    return localStorage.getItem('auth-token');
  }

  public saveUser(user: User): void {
    localStorage.setItem('user', JSON.stringify(user));

    this.authUserResource.set(user);
  }

  private getUser(): User | null {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr) : null;
  }

  public isLoggedIn(): boolean {
    return !!this.getToken();
  }

}
