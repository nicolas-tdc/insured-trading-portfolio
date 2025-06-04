import { computed, inject, Injectable, resource, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';

import { Account, AccountRequest } from './model';

@Injectable({ providedIn: 'root' })
export class AccountService {

  // Properties

  private apiUrl = '/api/account';

  // Reactive list of accounts

  private userAccountsResource = this.createUserAccountsResource();
  public userAccounts = computed(() => this.userAccountsResource.value());

  public reloadUserAccounts(): void {
    this.userAccountsResource.reload();
  }

  // Reactive account selected by ID

  // Selected account
  private selectedAccountId = signal<string | null>(null);

  public selectAccount(id: string | null): void {
    this.selectedAccountId.set(id);
  }

  public getSelectedAccountId(): string | null {
    return this.selectedAccountId();
  }

  // Reactive selected account
  private userAccountResource = this.createAccountResource();
  public userAccount = computed(() => this.userAccountResource.value());

  public reloadUserAccount(): void {
    this.userAccountResource.reload();
  }
  // Resources

  private createUserAccountsResource(): any {
    return resource({
      request: () => ({ }),
      loader: async ({ }) => {
        return await firstValueFrom(this.getUserList());
      },
    });
  }

  createAccountResource(): any {
    return resource({
      request: () => ({ accountId: this.selectedAccountId() }),
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

  create(request: AccountRequest): Observable<Account> {
    return this.http.post<Account>(`${this.apiUrl}`, request);
  }

  getItem(accountId: string): Observable<Account> {
    return this.http.get<Account>(`${this.apiUrl}/${accountId}`);
  }

  getUserList(): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.apiUrl}`);
  }

  getTypes(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/type`);
  }
}
