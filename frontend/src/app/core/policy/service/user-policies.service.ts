import { computed, Injectable, resource, signal } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';
import { Policy } from '../model';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../auth/service';
import { SorterService } from '../../utils/service/sorter.service';
import { policyFieldTypes } from '../model/policy-field-types.model';

@Injectable({
  providedIn: 'root'
})
export class UserPoliciesService {

  // Lifecycle

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private sorterService: SorterService,
  ) { }

  // API

  private apiUrl = '/api/policy';

  getUserPoliciesList(): Observable<Policy[]> {
    if (!this.authService.isLoggedIn()) {
      return new Observable(observer => {
        observer.next([]);
        observer.complete();
      });
    }

    return this.http.get<Policy[]>(`${this.apiUrl}`);
  }


  // List of policies - reactive resource

  private userPoliciesResource = resource<Policy[], {}>({
    params: () => ({}),
    loader: async () => {
      return await firstValueFrom(this.getUserPoliciesList());
    }
  });

  // List of policies - basic handlers

  public userPolicies = computed(() => this.userPoliciesResource.value());

  public reloadUserPolicies(): void { this.userPoliciesResource?.reload(); }

  public clearUserPolicies(): void { this.userPoliciesResource.set([]); }

  // List of policies - sorting handlers

  private sortField = signal<keyof Policy>('policyNumber');
  public sortFieldValue = computed(() => this.sortField());

  private sortDirection = signal<'asc' | 'desc'>('asc');
  public sortDirectionValue = computed(() => this.sortDirection());

  public sortByField(field: keyof Policy): void {
    if (field === this.sortField()) {
      this.sortDirection.set(this.sortDirection() === 'asc' ? 'desc' : 'asc');
    } else {
      this.sortField.set(field);

      this.sortDirection.set('asc');
    }

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