import { computed, Injectable, resource, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';

import { Account, AccountRequest } from '../model';
import { AccountType } from '../model/account-type.model';

@Injectable({ providedIn: 'root' })
export class AccountService {

  // // Properties

  private apiUrl = '/api/account';

  // Selected account
  public selectedAccountId = signal<string | null>(null);
  public selectAccount(id: string | null): void { this.selectedAccountId.set(id); }
  public getSelectedAccountId(): string | null { return this.selectedAccountId(); }

  // Reactive params
  private params = computed(() => {
    const id = this.selectedAccountId();
    return id ? { accountId: id } : null;
  });

  // Reactive resource
  private userAccountResource = resource<Account | null, { accountId: string }>({
    params: () => {
      const p = this.params();
      if (!p) {
        return { accountId: '' };
      }
      return p;
    },
    loader: async ({ params: { accountId } }) => {
      if (!accountId) return Promise.resolve(null);

      return await firstValueFrom(this.getItem(accountId));
    }
  });

  public userAccount = computed(() => this.userAccountResource.value());
  public reloadUserAccount(): void { this.userAccountResource?.reload(); }
  public clearSelectedAccount(): void { this.userAccountResource.set(null); }

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

  getTypes(): Observable<AccountType[]> {
    return this.http.get<AccountType[]>(`${this.apiUrl}/type`);
  }
}
