import { computed, Injectable, resource } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';
import { Policy } from '../model';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../auth/service';

@Injectable({
  providedIn: 'root'
})
export class UserPoliciesService {

  // Lifecycle

  constructor(
    private http: HttpClient,
    private authService: AuthService,
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

  public reverseUserPolicies(): void {
    this.userPoliciesResource.set(this.userPoliciesResource.value()?.reverse() ?? []);
  }

  public sortByPolicyNumber(direction: 'asc' | 'desc'): void {
    if (direction === 'asc') {
      this.userPoliciesResource.set(
        this.userPoliciesResource.value()?.sort((a, b) => a.policyNumber.localeCompare(b.policyNumber)) ?? []
      );
    } else {
      this.userPoliciesResource.set(
        this.userPoliciesResource.value()?.sort((a, b) => b.policyNumber.localeCompare(a.policyNumber)) ?? []
      );
    }
  }

  public sortByAccountNumber(direction: 'asc' | 'desc'): void {
    if (direction === 'asc') {
      this.userPoliciesResource.set(
        this.userPoliciesResource.value()?.sort((a, b) => a.accountNumber.localeCompare(b.accountNumber)) ?? []
      );
    } else {
      this.userPoliciesResource.set(
        this.userPoliciesResource.value()?.sort((a, b) => b.accountNumber.localeCompare(a.accountNumber)) ?? []
      );
    }
  }

  public sortByPolicyType(direction: 'asc' | 'desc'): void {
    if (direction === 'asc') {
      this.userPoliciesResource.set(
        this.userPoliciesResource.value()?.sort((a, b) => a.policyType.localeCompare(b.policyType)) ?? []
      );
    } else {
      this.userPoliciesResource.set(
        this.userPoliciesResource.value()?.sort((a, b) => b.policyType.localeCompare(a.policyType)) ?? []
      );
    }
  }
}