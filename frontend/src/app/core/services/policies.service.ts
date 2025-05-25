import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Policy } from '../models/policies/policy.model';

@Injectable({ providedIn: 'root' })
export class PoliciesService {
  private apiUrl = '/api/insurance';

  constructor(private http: HttpClient) {}

  getPolicies(): Observable<Policy[]> {
    return this.http.get<Policy[]>(`${this.apiUrl}/policies`);
  }

  applyPolicy(type: string, coverage: number, accountId: string): Observable<Policy> {
    return this.http.post<Policy>(`${this.apiUrl}/apply-policy`, {}, {
      params: { type, coverageAmount: coverage.toString(), accountId }
    });
  }
}
