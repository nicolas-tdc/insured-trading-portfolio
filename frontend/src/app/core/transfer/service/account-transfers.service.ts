import { computed, Injectable, resource, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';
import { Transfer } from '../model';

@Injectable({
  providedIn: 'root'
})
export class AccountTransfersService {

  // Properties

  private apiUrl = '/api/transfer';

  // Selected account
  public selectedAccountId = signal<string | null>(null);
  public selectAccount(accountId: string | null): void { this.selectedAccountId.set(accountId); }

  // Reactive resource
  private params = computed(() => {
    const id = this.selectedAccountId();
    return id ? { accountId: id } : null;
  });

  private accountTransfersResource = resource<Transfer[], { accountId: string }>({
    params: () => {
      const params = this.params();
      if (!params) return { accountId: '' };
      return params;
    },
    loader: async ({ params: { accountId } }) => {
      return await firstValueFrom(this.getAccountTransfers(accountId));
    }
  });

  public accountTransfers = computed(() => this.accountTransfersResource.value());
  public reloadAccountTransfers(): void { this.accountTransfersResource.reload(); }
  public clearAccountTransfers(): void { this.accountTransfersResource.set([]); }

  // Lifecycle

  constructor(
    private http: HttpClient,
  ) { }

  // Account transfers

  getAccountTransfers(accountId: string | null): Observable<Transfer[]> {
    if (!accountId) { return new Observable<Transfer[]>(observer => observer.next([])); }

    return this.http.get<Transfer[]>(`${this.apiUrl}/account/${accountId}`);
  }
}
