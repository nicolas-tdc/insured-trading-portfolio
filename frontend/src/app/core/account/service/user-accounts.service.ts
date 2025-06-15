import { HttpClient } from '@angular/common/http';
import { computed, Injectable, resource } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';
import { Account } from '../model';

@Injectable({
  providedIn: 'root'
})
export class UserAccountsService {

  // Properties

  private apiUrl = '/api/account';

  // Reactive list of accounts

  private userAccountsResource = this.createUserAccountsResource();
  public userAccounts = computed(() => this.userAccountsResource.value());

  public reloadUserAccounts(): void {
    this.userAccountsResource.reload();
  }

  public clearUserAccounts(): void {
    this.userAccountsResource.set([]);
  }

  // Resources

  private createUserAccountsResource(): any {
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
  getUserList(): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.apiUrl}`);
  }
}
