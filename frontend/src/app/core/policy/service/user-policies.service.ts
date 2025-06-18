import { computed, Injectable, resource } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';
import { Policy } from '../model';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../auth/service';

@Injectable({
  providedIn: 'root'
})
export class UserPoliciesService {

  // Properties

  private apiUrl = '/api/policy';

  // Reactive list
  private userPoliciesResource = resource<Policy[], {}>({
    params: () => ({}),
    loader: async () => {
      return await firstValueFrom(this.getUserList());
    }
  });
  
  public userPolicies = computed(() => this.userPoliciesResource.value());
  public reloadUserPolicies(): void { this.userPoliciesResource?.reload(); }
  public clearUserPolicies(): void { this.userPoliciesResource.set([]); }

  // Lifecycle

  constructor(
    private http: HttpClient,
    private authService: AuthService,
  ) { }

  // API

  getUserList(): Observable<Policy[]> {
    if (!this.authService.isLoggedIn()) {
      return new Observable(observer => {
        observer.next([]);
        observer.complete();
      });
    }

    return this.http.get<Policy[]>(`${this.apiUrl}`);
  }
}