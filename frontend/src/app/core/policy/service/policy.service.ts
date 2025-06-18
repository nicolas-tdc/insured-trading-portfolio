import { computed, Injectable, resource, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';

import { Policy, PolicyRequest } from '../model';

@Injectable({ providedIn: 'root' })
export class PolicyService {

  // Properties

  private apiUrl = '/api/policy';

  // Selected policy
  private selectedPolicyId = signal<string | null>(null);
  public selectPolicy(id: string | null): void { this.selectedPolicyId.set(id); }
  public clearSelectedPolicy(): void { this.selectedPolicyId.set(null); }

  // Reactive params
  private params = computed(() => {
    const id = this.selectedPolicyId();
    return id ? { policyId: id } : null;
  })

  // Reactive resource
  private userPolicyResource = resource<Policy | null, { policyId: string }>({
    params: () => {
      const params = this.params();
      if (!params) {
        return { policyId: '' };
      }
      return params;
    },
    loader: async ({ params: { policyId } }) => {
      if (!policyId) return Promise.resolve(null);

      return await firstValueFrom(this.getItem(policyId));
    }
  })

  public userPolicy = computed(() => this.userPolicyResource.value());

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

  getTypes(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/type`);
  }
}
