import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

import { LoginRequest, SignupRequest, JwtResponse, User } from '../models';

@Injectable({ providedIn: 'root' })
export class AuthService {

  // Properties

  private apiUrl = '/api/auth';
  // Logged user state
  private authUserSubject = new BehaviorSubject<User | null>(this.getUser());
  private authUser$ = this.authUserSubject.asObservable();

  // Lifecycle

  constructor(
    private http: HttpClient,
  ) {}

  // Local

  public logout(): void {
    localStorage.removeItem('auth-token');
    localStorage.removeItem('user');
    this.authUserSubject.next(null);
  }

  public saveToken(token: string): void {
    localStorage.setItem('auth-token', token);
  }

  public getToken(): string | null {
    return localStorage.getItem('auth-token');
  }

  public saveUser(user: any): void {
    localStorage.setItem('user', JSON.stringify(user));
    this.authUserSubject.next(user);
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

  // API

  public login(credentials: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.apiUrl}/signin`, credentials);
  }

  public register(signupRequest: SignupRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, signupRequest);
  }

  // Getters

  get authUser(): Observable<User | null> { return this.authUser$; }
}
