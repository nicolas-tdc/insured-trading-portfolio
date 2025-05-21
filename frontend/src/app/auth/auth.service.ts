import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { LoginRequest, SignupRequest, JwtResponse } from '../models/auth-response.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = '/api/auth';

  constructor(private http: HttpClient) {}

  login(credentials: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.apiUrl}/signin`, credentials);
  }

  register(signupRequest: SignupRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, signupRequest);
  }

  logout(): void {
    localStorage.removeItem('auth-token');
    localStorage.removeItem('user');
  }

  public saveToken(token: string): void {
    localStorage.setItem('auth-token', token);
  }

  public getToken(): string | null {
    return localStorage.getItem('auth-token');
  }

  public saveUser(user: any): void {
    localStorage.setItem('user', JSON.stringify(user));
  }

  public getUser(): any {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      return JSON.parse(userStr);
    }
    return null;
  }

  public isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
