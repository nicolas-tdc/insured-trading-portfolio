import { computed, Injectable, resource } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';
import { Policy } from '../model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserPoliciesService {

  // Properties

  private apiUrl = '/api/policy';

  // Reactive list

  private userPoliciesResource = this.createUserPoliciesResource();
  public userPolicies = computed(() => this.userPoliciesResource.value());

  public reloadUserPolicies(): void {
    this.userPoliciesResource.reload();
  }

  public clearUserPolicies(): void {
    this.userPoliciesResource.set([]);
  }

  private createUserPoliciesResource(): any {
    return resource({
      params: () => ({ }),
      loader: async ({ }) => {
        return await firstValueFrom(this.getUserList());
      },
    });
  }

  // Lifecycle

  constructor(
    private http: HttpClient,
  ) { }

  // API

  getUserList(): Observable<Policy[]> {
    return this.http.get<Policy[]>(`${this.apiUrl}`);
  }
}