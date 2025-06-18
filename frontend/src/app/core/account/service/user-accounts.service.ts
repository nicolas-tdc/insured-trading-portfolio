import { HttpClient } from '@angular/common/http';
import { computed, Injectable, resource } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';
import { Account } from '../model';
import { AuthService } from '../../auth/service';

@Injectable({
  providedIn: 'root'
})
export class UserAccountsService {

  // Properties

  private apiUrl = '/api/account';

  // Reactive list of accounts

  private userAccountsResource = resource<Account[], {}>({
    params: () => ({}),
    loader: async () => {
      return await firstValueFrom(this.getUserList());
    }
  });

  public userAccounts = computed(() => this.userAccountsResource.value());
  public reloadUserAccounts(): void { this.userAccountsResource.reload(); }
  public clearUserAccounts(): void { this.userAccountsResource.set([]); }

  // Lifecycle

  constructor(
    private http: HttpClient,
    private authService: AuthService,
  ) { }

  // API
  getUserList(): Observable<Account[]> {
    if (!this.authService.isLoggedIn()) {
      return new Observable(observer => {
        observer.next([]);
        observer.complete();
      });
    }

    return this.http.get<Account[]>(`${this.apiUrl}`);
  }
}
