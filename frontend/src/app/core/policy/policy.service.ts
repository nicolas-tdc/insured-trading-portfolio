import { computed, Injectable, resource, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';

import { Policy, PolicyRequest } from './model';

@Injectable({ providedIn: 'root' })
export class PolicyService {

  // Properties

  private apiUrl = '/api/policy';

  // Reactive list
  private userPoliciesResource = this.createUserPoliciesResource();
  public userPolicies = computed(() => this.userPoliciesResource.value());

  public reloadUserPolicies(): void {
    this.userPoliciesResource.reload();
  }

  private createUserPoliciesResource(): any {
    return resource({
      request: () => ({ }),
      loader: async ({ }) => {
        return await firstValueFrom(this.getUserList());
      },
    });
  }

  // Reactive item selected by ID
  private selectedPolicyId = signal<string | null>(null);

  public selectPolicy(id: string | null): void {
    this.selectedPolicyId.set(id);
  }

  private userPolicyResource = this.createPolicyResource();
  public userPolicy = computed(() => this.userPolicyResource.value());

  createPolicyResource(): any {
    return resource({
      request: () => ({ accountId: this.selectedPolicyId() }),
      loader: async ({ request }) => {
        if (!request.accountId) return Promise.resolve(null);

        return await firstValueFrom(
          this.getItem(request.accountId)
        );
      }
    })
  }

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

  getUserList(): Observable<Policy[]> {
    return this.http.get<Policy[]>(`${this.apiUrl}`);
  }

  getTypes(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/type`);
  }
}
