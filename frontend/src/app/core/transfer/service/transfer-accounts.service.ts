import { computed, Injectable, resource, signal } from '@angular/core';
import { AccountSecure } from '../../account/model/account-secure.model';
import { firstValueFrom, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { TransferValidateInternalRequest } from '../model/transfer-validate-internal-request';
import { TransferValidateExternalRequest } from '../model/transfer-validate-external-request';

@Injectable({
  providedIn: 'root'
})
export class TransferAccountsService {

  // Properties

  private apiUrl = '/api/transfer';

  // Clear all
  public clearTransferAccounts(): void {
    this.clearSourceAccount();
    this.clearInternalAccount();
    this.clearExternalAccount();
    this.clearTargetAccount();
  }

  // Selected source account
  private sourceAccountId = signal<string | null>(null);
  public selectSourceAccount(accountId: string): void { this.sourceAccountId.set(accountId); }
  public clearSourceAccount(): void { this.sourceAccountId.set(null); }

  // Selected internal account
  private internalTargetAccountId = signal<string | null>(null);
  public selectInternalAccount(accountId: string): void { this.internalTargetAccountId.set(accountId); }
  public clearInternalAccount(): void { this.internalTargetAccountId.set(null); }

  // Selected external account
  private externalTargetAccountNumber = signal<string | null>(null);
  public selectExternalAccount(accountNumber: string | null): void { this.externalTargetAccountNumber.set(accountNumber); }
  public clearExternalAccount(): void { this.externalTargetAccountNumber.set(null); }

  // Reactive params
  private params = computed(() => ({
    sourceAccountId: this.sourceAccountId(),
    internalTargetAccountId: this.internalTargetAccountId(),
    externalTargetAccountNumber: this.externalTargetAccountNumber()
  }));

  // Reactive resource
  private targetAccountResource = resource<AccountSecure | null, {
      sourceAccountId: string | null;
      internalTargetAccountId: string | null;
      externalTargetAccountNumber: string | null;
    }> ({
    params: () => this.params(),
    loader: async ({
      params: { sourceAccountId, internalTargetAccountId, externalTargetAccountNumber }
    }) => {
      if (!sourceAccountId) return Promise.resolve(null);
      if (!internalTargetAccountId && !externalTargetAccountNumber) return Promise.resolve(null);

      try {
        if (internalTargetAccountId) {
          return await firstValueFrom(
            this.getInternalTargetAccount(sourceAccountId, internalTargetAccountId)
          );
        }
        if (externalTargetAccountNumber) {
          return await firstValueFrom(
            this.getExternalTargetAccount(sourceAccountId, externalTargetAccountNumber)
          );
        }
        return Promise.resolve(null);
      } catch (error: any) {
        throw new Error(error.message);
      }
    }
  });

  public targetAccount = computed(() => this.targetAccountResource.value());
  public targetAccountError = computed(() => this.targetAccountResource.error());
  public clearTargetAccount(): void { this.targetAccountResource.set(null); }
  public reloadTargetAccount(): void { this.targetAccountResource?.reload(); }

  // Lifecycle

  constructor(
    private http: HttpClient,
  ) { }

  getInternalTargetAccount(sourceAccountId: string, targetAccountId: string): Observable<AccountSecure> {
    const request: TransferValidateInternalRequest = {
      sourceAccountId: sourceAccountId,
      targetAccountId: targetAccountId
    }
    return this.http.post<AccountSecure>(`${this.apiUrl}/validate-internal`, request);
  }

  getExternalTargetAccount(sourceAccountId: string, targetAccountNumber: string): Observable<AccountSecure> {
    const request: TransferValidateExternalRequest = {
      sourceAccountId: sourceAccountId,
      targetAccountNumber: targetAccountNumber
    }

    return this.http.post<AccountSecure>(`${this.apiUrl}/validate-external`, request);
  }
}