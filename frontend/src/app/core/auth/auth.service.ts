import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest, SignupRequest, JwtResponse, User } from './model';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {

  // Properties

  private apiUrl = '/api/auth';
  // Signal for logged-in user

  private _authUser = signal<User | null>(this.getUser());
  public readonly authUser = this._authUser.asReadonly();

  // Lifecycle

  constructor(
    private http: HttpClient,
  ) { }

  // API

  public register(signupRequest: SignupRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, signupRequest);
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
    this._authUser.set(null);
  }

  public saveToken(token: string): void {
    localStorage.setItem('auth-token', token);
  }

  public getToken(): string | null {
    return localStorage.getItem('auth-token');
  }

  public saveUser(user: User): void {
    localStorage.setItem('user', JSON.stringify(user));
    this._authUser.set(user);
  }

  private getUser(): User | null {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr) : null;
  }

  public isLoggedIn(): boolean {
    return !!this.getToken();
  }

}
