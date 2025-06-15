import { computed, Injectable, resource, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';

import { Account, AccountRequest } from '../model';

@Injectable({ providedIn: 'root' })
export class AccountService {

  // // Properties

  private apiUrl = '/api/account';

  private selectedAccountId = signal<string | null>(null);

  // Reactive account selected by ID

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

  public clearSelectedAccount(): void {
    this.userAccountResource.set(null);
  }


  createAccountResource(): any {
    return resource({
      params: () => ({ accountId: this.selectedAccountId() }),
      loader: async ({ params: { accountId } }) => {
        if (!accountId) return Promise.resolve(null);

        return await firstValueFrom(
          this.getItem(accountId)
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

  getTypes(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/type`);
  }
}
