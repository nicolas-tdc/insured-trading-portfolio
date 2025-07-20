import { computed, Injectable, resource, ResourceRef, Signal, signal, WritableSignal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';
import { Policy, PolicyRequest } from '../model';
import { PolicyType } from '../model/policy-type.model';

/**
 * Policy service
 * 
 * @export
 */
@Injectable({ providedIn: 'root' })
export class PolicyService {

  /**
   * API URL
   */
  private apiUrl: string = '/api/policy';

  /**
   * Selected policy id
   */
  private selectedPolicyId: WritableSignal<string | null> = signal<string | null>(null);

  /**
   * Get selected policy id
   */
  public get selectedPolicyIdValue(): string | null { return this.selectedPolicyId(); }

  /**
   * Select policy
   * 
   * @param id Policy id
   * @returns void
   */
  public selectPolicy(id: string | null): void { this.selectedPolicyId.set(id); }

  /**
   * Clear selected policy
   * 
   * @returns void
   */
  public clearSelectedPolicy(): void { this.selectedPolicyId.set(null); }

  /**
   * Policy resource reactive params
   */
  private params: Signal<{ policyId: string } | null> = computed(() => {
    const id = this.selectedPolicyId();
    return id ? { policyId: id } : null;
  })

  /**
   * Policy resource
   */
  private userPolicyResource: ResourceRef<Policy | null | undefined> = resource<Policy | null, { policyId: string }>({
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

  /**
   * User policy
   *  
   * @returns Policy
   */
  public userPolicy: Signal<Policy | null | undefined> = computed(() => this.userPolicyResource.value());

  /**
   * Initialize the service
   * Inject the services required for the http client
   * 
   * @param http HTTP client
   */
  constructor(
    private readonly http: HttpClient,
  ) { }

  /**
   * Creates a policy
   * 
   * @param request Policy creation request
   * @returns Policy
   */
  create(request: PolicyRequest): Observable<Policy> {
    return this.http.post<Policy>(`${this.apiUrl}`, request);
  }

  /**
   * Gets a policy
   * 
   * @param policyId Policy id
   * @returns Policy
   */
  getItem(policyId: string): Observable<Policy> {
    return this.http.get<Policy>(`${this.apiUrl}/${policyId}`);
  }

  /**
   * Gets policy types
   * 
   * @returns Policy types
   */
  getTypes(): Observable<PolicyType[]> {
    return this.http.get<PolicyType[]>(`${this.apiUrl}/type`);
  }
}
