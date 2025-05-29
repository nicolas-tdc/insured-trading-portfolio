import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Policy, PolicyRequest, PolicyType } from './model';

@Injectable({ providedIn: 'root' })
export class PolicyService {
  private apiUrl = '/api/policy';

  constructor(private http: HttpClient) {}

  create(request: PolicyRequest): Observable<Policy> {
    return this.http.post<Policy>(`${this.apiUrl}`, request);
  }

  getItem(policyId: string): Observable<Policy> {
    return this.http.get<Policy>(`${this.apiUrl}/${policyId}`);
  }

  getList(): Observable<Policy[]> {
    return this.http.get<Policy[]>(`${this.apiUrl}`);
  }

  getTypes(): Observable<PolicyType[]> {
    return this.http.get<PolicyType[]>(`${this.apiUrl}/type`);
  }
}
