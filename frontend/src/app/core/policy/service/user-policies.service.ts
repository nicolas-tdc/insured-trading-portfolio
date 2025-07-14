import { computed, Injectable, resource, ResourceRef, Signal, signal, WritableSignal } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';
import { Policy } from '../model';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../auth/service';
import { SorterService } from '../../shared/service/sorter.service';
import { policyFieldTypes } from '../model/policy-field-types.model';

/**
 * User policies service
 * 
 * @export
 */
@Injectable({
  providedIn: 'root'
})
export class UserPoliciesService {

  /**
   * Initializes the service
   * Injects required services for http client and authentication
   * 
   * @param http HTTP client
   * @param authService Service for authentication
   * @param sorterService Service for sorting
   */
  constructor(
    private readonly http: HttpClient,
    private readonly authService: AuthService,
    private readonly sorterService: SorterService,
  ) { }

  /**
   * API url
   */
  private apiUrl: string = '/api/policy';

  /**
   * Gets list of user policies
   * 
   * @returns Observable<Policy[]>
   */
  getUserPoliciesList(): Observable<Policy[]> {
    // Check if user is logged in
    if (!this.authService.isLoggedIn()) {
      return new Observable(observer => {
        observer.next([]);
        observer.complete();
      });
    }

    return this.http.get<Policy[]>(`${this.apiUrl}`);
  }

  /**
   * List of policies resource
   */
  private userPoliciesResource: ResourceRef<Policy[] | undefined> = resource<Policy[], {}>({
    params: () => ({}),
    loader: async () => {
      return await firstValueFrom(this.getUserPoliciesList());
    }
  });

  /**
   * List of policies
   * 
   * @returns Signal<Policy[]>
   */
  public userPolicies: Signal<Policy[] | undefined> = computed(() => this.userPoliciesResource.value());

  /**
   * Reloads list of policies
   * 
   * @returns void
   */
  public reloadUserPolicies(): void { this.userPoliciesResource?.reload(); }

  /**
   * Clears list of policies
   * 
   * @returns void
   */
  public clearUserPolicies(): void { this.userPoliciesResource.set([]); }

  /**
   * Field to sort by
   */
  private sortField: WritableSignal<keyof Policy> = signal<keyof Policy>('policyNumber');

  /**
   * Field to sort by
   * 
   * @returns Signal<keyof Policy>
   */
  public sortFieldValue: Signal<keyof Policy> = computed(() => this.sortField());

  /**
   * Direction to sort by
   */
  private sortDirection: WritableSignal<'asc' | 'desc'> = signal<'asc' | 'desc'>('asc');

  /**
   * Direction to sort by
   * 
   * @returns Signal<'asc' | 'desc'>
   */
  public sortDirectionValue: Signal<'asc' | 'desc'> = computed(() => this.sortDirection());

  /**
   * Sorts policies by field
   * 
   * @param field Field to sort by
   * @returns void
   */
  public sortByField(field: keyof Policy): void {
    // If the field is the same as the current field, toggle the direction
    if (field === this.sortField()) {
      this.sortDirection.set(this.sortDirection() === 'asc' ? 'desc' : 'asc');
    } else {
      this.sortField.set(field);

      this.sortDirection.set('asc');
    }

    // Sort the policies
    this.userPoliciesResource.set(
      this.sorterService.sortListByField(
        this.userPoliciesResource.value() ?? [],
        field,
        policyFieldTypes[field],
        this.sortDirection()
      )
    );
  }
}