import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Transfer, TransferRequest } from '../model';
import { AccountService } from '../../account/service';
import { MatDialog } from '@angular/material/dialog';
import { AccountSecure } from '../../account/model/account-secure.model';
import { TransferValidateInternalRequest } from '../model/transfer-validate-internal-request';
import { TransferValidateExternalRequest } from '../model/transfer-validate-external-request';
import { FormCreateTransferComponent } from '../component/form-create-transfer/form-create-transfer.component';

@Injectable({ providedIn: 'root' })
export class TransferService {

  // Properties

  private apiUrl = '/api/transfer';

  // Lifecycle

  constructor(
    private http: HttpClient,
    private dialog: MatDialog,
    private accountService: AccountService,
  ) { }

  // API

  validateInternal(transferValidateInternalRequest: TransferValidateInternalRequest): Observable<AccountSecure> {
    return this.http.post<any>(`${this.apiUrl}/validate-internal`, transferValidateInternalRequest);
  }

  validateExternal(transferValidateExternalRequest: TransferValidateExternalRequest): Observable<AccountSecure> {
    return this.http.post<any>(`${this.apiUrl}/validate-external`, transferValidateExternalRequest);
  }

  create(transferRequest: TransferRequest): Observable<Transfer> {
    return this.http.post<Transfer>(`${this.apiUrl}`, transferRequest);
  }

  openCreateTransferFormDialog(accountId: string | undefined): void {
    if (!accountId) return;

    this.accountService.selectAccount(accountId);

    const dialogRef = this.dialog.open(FormCreateTransferComponent, {
      width: '600px',
      height: '500px',
    });

    dialogRef.afterClosed().subscribe((result: string) => {
      if (result === 'completed') {
        this.accountService.reloadUserAccount();
      }
    });
  }
}