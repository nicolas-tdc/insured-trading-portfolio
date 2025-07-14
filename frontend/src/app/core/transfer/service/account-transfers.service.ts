import { computed, Injectable, resource, ResourceRef, Signal, signal, WritableSignal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';
import { Transfer } from '../model';

/**
 * AccountTransfersService
 * 
 * Service for managing account transfers
 * 
 * @export
 */
@Injectable({
  providedIn: 'root'
})
export class AccountTransfersService {

  // API URL
  private apiUrl: string = '/api/transfer';

  /**
   * Selected account id
   * 
   * @type {WritableSignal<string | null>}
   */
  public selectedAccountId: WritableSignal<string | null> = signal<string | null>(null);

  /**
   * Select account
   * 
   * @param accountId Account id
   * @returns void
   */
  public selectAccount(accountId: string | null): void { this.selectedAccountId.set(accountId); }

  /**
   * Params
   * 
   * @type {Signal<{ accountId: string } | null>}
   */
  private params: Signal<{ accountId: string } | null> = computed(() => {
    const id = this.selectedAccountId();
    return id ? { accountId: id } : null;
  });

  /**
   * Account transfers resource
   * 
   * @type {ResourceRef<Transfer[] | undefined>}
   */
  private accountTransfersResource: ResourceRef<Transfer[] | undefined> = resource<Transfer[], { accountId: string }>({
    params: () => {
      const params = this.params();
      if (!params) return { accountId: '' };
      return params;
    },
    loader: async ({ params: { accountId } }) => {
      return await firstValueFrom(this.getAccountTransfers(accountId));
    }
  });

  /**
   * Account transfers
   * 
   * @type {Signal<Transfer[] | undefined>}
   * @readonly
   */
  public accountTransfers: Signal<Transfer[] | undefined> = computed(() => this.accountTransfersResource.value());

  /**
   * Reload account transfers
   * 
   * @returns void
   */
  public reloadAccountTransfers(): void { this.accountTransfersResource.reload(); }

  /**
   * Clear account transfers
   * 
   * @returns void
   */
  public clearAccountTransfers(): void { this.accountTransfersResource.set([]); }

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
   * Gets account transfers
   * 
   * @param accountId Account id
   * @returns Account transfers
   */
  getAccountTransfers(accountId: string | null): Observable<Transfer[]> {
    // Check if account id is defined
    if (!accountId) { return new Observable<Transfer[]>(observer => observer.next([])); }

    return this.http.get<Transfer[]>(`${this.apiUrl}/account/${accountId}`);
  }
}
