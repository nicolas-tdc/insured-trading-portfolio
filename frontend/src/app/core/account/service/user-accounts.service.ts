import { HttpClient } from '@angular/common/http';
import { computed, Injectable, resource, ResourceRef, Signal, signal, WritableSignal } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';
import { Account } from '../model';
import { AuthService } from '../../auth/service';
import { SorterService } from '../../shared/service/sorter.service';
import { accountFieldTypes } from '../model/account-field-types.model';

/**
 * User accounts service
 * 
 * @export
 */
@Injectable({
  providedIn: 'root'
})
export class UserAccountsService {

  /**
   * Initializes the service
   * Injects required services for http client and authentication
   * 
   * @param http HTTP client
   * @param authService Service for authentication
   * @param sorterService Service for sorting
   */
  constructor(
    private readonly http: HttpClient,
    private readonly authService: AuthService,
    private readonly sorterService: SorterService,
  ) { }

  /**
   * API url
   */
  private apiUrl: string = '/api/account';

  /**
   * Gets list of user accounts
   * 
   * @returns List of accounts
   */
  getUserAccountsList(): Observable<Account[]> {
    // Check if user is logged in
    if (!this.authService.isLoggedIn()) {
      return new Observable(observer => {
        observer.next([]);
        observer.complete();
      });
    }

    return this.http.get<Account[]>(`${this.apiUrl}`);
  }

  /**
   * Resource for user accounts
   */
  private userAccountsResource: ResourceRef<Account[] | undefined> = resource<Account[], {}>({
    params: () => ({}),
    loader: async () => {
      return await firstValueFrom(this.getUserAccountsList());
    }
  });

  /**
   * List of user accounts
   */
  public userAccounts: Signal<Account[] | undefined> = computed(() => this.userAccountsResource.value());

  /**
   * Reloads user accounts
   * 
   * @returns void
   */
  public reloadUserAccounts(): void { this.userAccountsResource.reload(); }

  /**
   * Clears user accounts
   * 
   * @returns void
   */
  public clearUserAccounts(): void { this.userAccountsResource.set([]); }

  /**
   * Sort field for user accounts
   */
  private sortField: WritableSignal<keyof Account> = signal<keyof Account>('accountNumber');
 
 /**
  * Sort field for user accounts
  */
  public sortFieldValue: Signal<keyof Account> = computed(() => this.sortField());

  /**
   * Sort direction for user accounts
   */
  private sortDirection: WritableSignal<'asc' | 'desc'> = signal<'asc' | 'desc'>('asc');

  /**
   * Sort direction for user accounts
   */
  public sortDirectionValue: Signal<'asc' | 'desc'> = computed(() => this.sortDirection());

  /**
   * Sorts user accounts by field
   * 
   * @param field Field to sort by
   * @returns void
   */
  public sortByField(field: keyof Account): void {
    // If the field is the same as the current field, toggle the direction
    if (field === this.sortField()) {
      this.sortDirection.set(this.sortDirection() === 'asc' ? 'desc' : 'asc');
    } else {
      this.sortField.set(field);

      this.sortDirection.set('asc');
    }

    // Sort the accounts
    this.userAccountsResource.set(
      this.sorterService.sortListByField(
        this.userAccountsResource.value() ?? [],
        field,
        accountFieldTypes[field],
        this.sortDirection()
      )
    );
  }
}
