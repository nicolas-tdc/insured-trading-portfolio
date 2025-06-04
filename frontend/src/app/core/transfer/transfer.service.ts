import { computed, Injectable, resource, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';
import { Transfer, TransferRequest } from './model';

@Injectable({ providedIn: 'root' })
export class TransferService {

  // Properties

  private apiUrl = '/api/transfer';

  // Selected account
  public selectedAccountId = signal<string | null>(null);

  public selectAccount(accountId: string | null): void {
    if (!accountId) { return; }

    this.selectedAccountId.set(accountId);
  }

  // Account transfers reactive list

  private accountTransfersResource = this.createAccountTransfersResource();
  public accountTransfers = computed(() => this.accountTransfersResource.value());

  public reloadAccountTransfers(): void {
    this.accountTransfersResource.reload();
  }

  // Resources

  private createAccountTransfersResource(): any {
    return resource({
      request: () => ({ accountId: this.selectedAccountId() }),
      loader: async ({ request }) => {
        return await firstValueFrom(this.getAccountTransfers(request.accountId));
      },
    });
  }

  // Lifecycle

  constructor(
    private http: HttpClient,
  ) { }

  // API

  create(transferRequest: TransferRequest): Observable<Transfer> {
    return this.http.post<Transfer>(`${this.apiUrl}`, transferRequest);
  }

  getAccountTransfers(accountId: string | null): Observable<Transfer[]> {
    if (!accountId) { return new Observable<Transfer[]>(observer => observer.next([])); }

    return this.http.get<Transfer[]>(`${this.apiUrl}/account/${accountId}`);
  }
}