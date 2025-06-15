import { computed, Injectable, resource, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';
import { Transfer, TransferRequest } from '../model';
import { FormRequestTransferComponent } from '../component/form-request-transfer/form-request-transfer.component';
import { AccountService } from '../../account/service';
import { MatDialog } from '@angular/material/dialog';

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

  public clearAccountTransfers(): void {
    this.accountTransfersResource.set([]);
  }

  // Resources

  private createAccountTransfersResource(): any {
    return resource({
      params: () => ({ accountId: this.selectedAccountId() }),
      loader: async ({ params: { accountId } }) => {
        return await firstValueFrom(this.getAccountTransfers(accountId));
      },
    });
  }

  // Lifecycle

  constructor(
    private http: HttpClient,
    private accountService: AccountService,
    private dialog: MatDialog,
  ) { }

  // API

  create(transferRequest: TransferRequest): Observable<Transfer> {
    return this.http.post<Transfer>(`${this.apiUrl}`, transferRequest);
  }

  getAccountTransfers(accountId: string | null): Observable<Transfer[]> {
    if (!accountId) { return new Observable<Transfer[]>(observer => observer.next([])); }

    return this.http.get<Transfer[]>(`${this.apiUrl}/account/${accountId}`);
  }

  openCreateTransferFormDialog(account: any) {
    if (!account) return;

    const dialogRef = this.dialog.open(FormRequestTransferComponent, {
      width: '600px',
      height: '500px',
      data: account
    });

    dialogRef.afterClosed().subscribe((result: string) => {
      if (result === 'completed') {
        this.accountService.reloadUserAccount();
      }
    });
  }
}