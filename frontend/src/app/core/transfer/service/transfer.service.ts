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

/**
 * TransferService
 * 
 * Service for managing transfers
 * 
 * @export
 */
@Injectable({ providedIn: 'root' })
export class TransferService {

  /**
   * API URL
   */
  private apiUrl: string = '/api/transfer';

  /**
   * Initializes the service
   * Injects required services for http client, dialog and account
   * 
   * @param http HTTP client
   * @param dialog Service for dialog
   * @param accountService Service for account
   */
  constructor(
    private readonly http: HttpClient,
    private readonly dialog: MatDialog,
    private readonly accountService: AccountService,
  ) { }

  /**
   * Validates an internal transfer
   * 
   * @param transferValidateInternalRequest Internal transfer validation request
   * @returns Observable<AccountSecure>
   */
  validateInternal(transferValidateInternalRequest: TransferValidateInternalRequest): Observable<AccountSecure> {
    return this.http.post<any>(`${this.apiUrl}/validate-internal`, transferValidateInternalRequest);
  }

  /**
   * Validates an external transfer
   * 
   * @param transferValidateExternalRequest External transfer validation request
   * @returns Observable<AccountSecure>
   */
  validateExternal(transferValidateExternalRequest: TransferValidateExternalRequest): Observable<AccountSecure> {
    return this.http.post<any>(`${this.apiUrl}/validate-external`, transferValidateExternalRequest);
  }

  /**
   * Creates a transfer
   * 
   * @param transferRequest Transfer request
   * @returns Observable<Transfer>
   */
  create(transferRequest: TransferRequest): Observable<Transfer> {
    return this.http.post<Transfer>(`${this.apiUrl}`, transferRequest);
  }

  /**
   * Opens the create transfer form dialog
   * 
   * @param accountId Account id
   * @returns void
   */
  openCreateTransferFormDialog(accountId: string | undefined): void {
    // Check if account id is defined
    if (!accountId) return;
    this.accountService.selectAccount(accountId);

    // Open the policy creation dialog form
    const dialogRef = this.dialog.open(FormCreateTransferComponent, {
      width: '600px',
      height: '550px',
    });

    // Reload user account on form completion
    dialogRef.afterClosed().subscribe((result: string) => {
      if (result === 'completed') {
        this.accountService.reloadUserAccount();
      }
    });
  }
}