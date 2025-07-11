import { computed, Injectable, resource, ResourceRef, Signal, signal, WritableSignal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';
import { Account, AccountRequest } from '../model';
import { AccountType } from '../model/account-type.model';

/**
 * Account service
 * 
 * @export
 */
@Injectable({ providedIn: 'root' })
export class AccountService {

  /**
   * Initializes the service
   * Injects required services for http client
   * 
   * @param http HTTP client
   */
  constructor(
    private readonly http: HttpClient,
  ) { }

  /**
   * Api url
   */
  private apiUrl: string = '/api/account';

  /**
   * Selected account
   */
  public selectedAccountId: WritableSignal<string | null> = signal<string | null>(null);

  /**
   * Select account
   * 
   * @param id Account id
   * @returns void
   */
  public selectAccount(id: string | null): void { this.selectedAccountId.set(id); }

  /**
   * Get selected account id
   * 
   * @returns Account id
   */
  public getSelectedAccountId(): string | null { return this.selectedAccountId(); }

  /**
   * Account params
   */
  private params: Signal<{ accountId: string } | null> = computed(() => {
    const id = this.selectedAccountId();

    return id ? { accountId: id } : null;
  });

  /**
   * Selected account resource
   */
  private userAccountResource: ResourceRef<Account | null | undefined> = resource<Account | null, { accountId: string }>({
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

  /**
   * Selected account
   */
  public userAccount: Signal<Account | null | undefined> = computed(() => this.userAccountResource.value());

  /**
   * Reloads selected account
   * 
   * @returns void
   */
  public reloadUserAccount(): void { this.userAccountResource?.reload(); }

  /**
   * Clears selected account
   * 
   * @returns void
   */
  public clearSelectedAccount(): void { this.userAccountResource.set(null); }

  /**
   * Creates an account
   * 
   * @param request Account creation request
   * @returns Account
   */
  create(request: AccountRequest): Observable<Account> {
    return this.http.post<Account>(`${this.apiUrl}`, request);
  }

  /**
   * Gets an account
   * 
   * @param accountId Account id
   * @returns Account
   */
  getItem(accountId: string): Observable<Account> {
    return this.http.get<Account>(`${this.apiUrl}/${accountId}`);
  }

  /**
   * Gets account types
   * 
   * @returns Account types
   */
  getTypes(): Observable<AccountType[]> {
    return this.http.get<AccountType[]>(`${this.apiUrl}/type`);
  }
}
