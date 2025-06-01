import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Policy, PolicyRequest } from './model';

@Injectable({ providedIn: 'root' })
export class PolicyService {

  // Properties

  // API
  private apiUrl = '/api/policy';

  // Lifecycle

  constructor(
    private http: HttpClient,
  ) { }

  // API

  create(request: PolicyRequest): Observable<Policy> {
    return this.http.post<Policy>(`${this.apiUrl}`, request);
  }

  getItem(policyId: string): Observable<Policy> {
    return this.http.get<Policy>(`${this.apiUrl}/${policyId}`);
  }

  getList(): Observable<Policy[]> {
    return this.http.get<Policy[]>(`${this.apiUrl}`);
  }

  getTypes(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/type`);
  }
}
