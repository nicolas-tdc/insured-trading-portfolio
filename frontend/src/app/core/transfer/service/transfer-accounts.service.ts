import { computed, Injectable, resource, ResourceRef, Signal, signal, WritableSignal } from '@angular/core';
import { AccountSecure } from '../../account/model/account-secure.model';
import { firstValueFrom, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { TransferValidateInternalRequest } from '../model/transfer-validate-internal-request';
import { TransferValidateExternalRequest } from '../model/transfer-validate-external-request';

/**
 * TransferAccountsService
 * 
 * Service for managing transfer accounts
 * 
 * @export
 */
@Injectable({
  providedIn: 'root'
})
export class TransferAccountsService {

  /**
   * Api url
   */
  private apiUrl: string = '/api/transfer';

  /**
   * Clear transfer accounts
   * 
   * @returns void
   */
  public clearTransferAccounts(): void {
    this.clearSourceAccount();
    this.clearInternalAccount();
    this.clearExternalAccount();
    this.clearTargetAccount();
  }

  /**
   * Selected source account
   * 
   * @type {WritableSignal<string | null>}
   */
  private sourceAccountId: WritableSignal<string | null> = signal<string | null>(null);

  /**
   * Select source account
   * 
   * @param accountId Account id
   * @returns void
   */
  public selectSourceAccount(accountId: string): void { this.sourceAccountId.set(accountId); }

  /**
   * Clear source account
   * 
   * @returns void
   */
  public clearSourceAccount(): void { this.sourceAccountId.set(null); }

  /**
   * Selected internal account
   * 
   * @type {WritableSignal<string | null>}
   */
  private internalTargetAccountId: WritableSignal<string | null> = signal<string | null>(null);

  /**
   * Select internal account
   * 
   * @param accountId Account id
   * @returns void
   */
  public selectInternalAccount(accountId: string): void { this.internalTargetAccountId.set(accountId); }

  /**
   * Clear internal account
   * 
   * @returns void
   */
  public clearInternalAccount(): void { this.internalTargetAccountId.set(null); }

  /**
   * Selected external account
   * 
   * @type {WritableSignal<string | null>}
   */
  private externalTargetAccountNumber: WritableSignal<string | null> = signal<string | null>(null);

  /**
   * Select external account
   * 
   * @param accountNumber Account number
   * @returns void
   */
  public selectExternalAccount(accountNumber: string | null): void { this.externalTargetAccountNumber.set(accountNumber); }

  /**
   * Clear external account
   * 
   * @returns void
   */
  public clearExternalAccount(): void { this.externalTargetAccountNumber.set(null); }

  /**
   * Get target account
   * 
   * @type {Signal<{ sourceAccountId: string | null; internalTargetAccountId: string | null; externalTargetAccountNumber: string | null; }>}
   */
  private params: Signal<{ sourceAccountId: string | null; internalTargetAccountId: string | null; externalTargetAccountNumber: string | null; }> = computed(() => ({
    sourceAccountId: this.sourceAccountId(),
    internalTargetAccountId: this.internalTargetAccountId(),
    externalTargetAccountNumber: this.externalTargetAccountNumber()
  }));

  /**
   * Target account
   * 
   * @type {ResourceRef<AccountSecure | null | undefined>}
   */
  private targetAccountResource: ResourceRef<AccountSecure | null | undefined> = resource<AccountSecure | null, {
      sourceAccountId: string | null;
      internalTargetAccountId: string | null;
      externalTargetAccountNumber: string | null;
    }> ({
    params: () => this.params(),
    loader: async ({
      params: { sourceAccountId, internalTargetAccountId, externalTargetAccountNumber }
    }) => {
      // Check params
      if (!sourceAccountId) return Promise.resolve(null);
      if (!internalTargetAccountId && !externalTargetAccountNumber) return Promise.resolve(null);

      try {
        // Get internal target account
        if (internalTargetAccountId) {
          return await firstValueFrom(
            this.getInternalTargetAccount(sourceAccountId, internalTargetAccountId)
          );
        }

        // Get external target account
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

  /**
   * Get target account
   * 
   * @readonly
   * @type {Signal<AccountSecure | null | undefined>}
   */
  public targetAccount: Signal<AccountSecure | null | undefined> = computed(() => this.targetAccountResource.value());

  /**
   * Get target account error
   * 
   * @readonly
   * @type {Signal<Error | undefined>}
   */
  public targetAccountError: Signal<Error | undefined> = computed(() => this.targetAccountResource.error());

  /**
   * Clear target account
   * 
   * @returns void
   */
  public clearTargetAccount(): void { this.targetAccountResource.set(null); }

  /**
   * Reload target account
   * 
   * @returns void
   */
  public reloadTargetAccount(): void { this.targetAccountResource?.reload(); }

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
   * Get internal target account
   * 
   * @param sourceAccountId Source account id
   * @param targetAccountId Target account id
   * @returns Observable<AccountSecure>
   */
  getInternalTargetAccount(sourceAccountId: string, targetAccountId: string): Observable<AccountSecure> {
    // Build request
    const request: TransferValidateInternalRequest = {
      sourceAccountId: sourceAccountId,
      targetAccountId: targetAccountId
    }

    return this.http.post<AccountSecure>(`${this.apiUrl}/validate-internal`, request);
  }

  /**
   * Get external target account
   * 
   * @param sourceAccountId Source account id
   * @param targetAccountNumber Target account number
   * @returns Observable<AccountSecure>
   */
  getExternalTargetAccount(sourceAccountId: string, targetAccountNumber: string): Observable<AccountSecure> {
    // Build request
    const request: TransferValidateExternalRequest = {
      sourceAccountId: sourceAccountId,
      targetAccountNumber: targetAccountNumber
    }

    return this.http.post<AccountSecure>(`${this.apiUrl}/validate-external`, request);
  }
}