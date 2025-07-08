import { HttpClient } from '@angular/common/http';
import { computed, Injectable, resource, signal } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';
import { Account } from '../model';
import { AuthService } from '../../auth/service';
import { SorterService } from '../../shared/service/sorter.service';
import { accountFieldTypes } from '../model/account-field-types.model';

@Injectable({
  providedIn: 'root'
})
export class UserAccountsService {

  // Lifecycle

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private sorterService: SorterService,
  ) { }

  // API

  private apiUrl = '/api/account';

  getUserAccountsList(): Observable<Account[]> {
    if (!this.authService.isLoggedIn()) {
      return new Observable(observer => {
        observer.next([]);
        observer.complete();
      });
    }

    return this.http.get<Account[]>(`${this.apiUrl}`);
  }

  // List of accounts - reactive resource

  private userAccountsResource = resource<Account[], {}>({
    params: () => ({}),
    loader: async () => {
      return await firstValueFrom(this.getUserAccountsList());
    }
  });

  // List of accounts - basic handlers

  public userAccounts = computed(() => this.userAccountsResource.value());

  public reloadUserAccounts(): void { this.userAccountsResource.reload(); }

  public clearUserAccounts(): void { this.userAccountsResource.set([]); }

  // List of accounts - sorting handlers

  private sortField = signal<keyof Account>('accountNumber');
  public sortFieldValue = computed(() => this.sortField());

  private sortDirection = signal<'asc' | 'desc'>('asc');
  public sortDirectionValue = computed(() => this.sortDirection());

  public sortByField(field: keyof Account): void {
    if (field === this.sortField()) {
      this.sortDirection.set(this.sortDirection() === 'asc' ? 'desc' : 'asc');
    } else {
      this.sortField.set(field);

      this.sortDirection.set('asc');
    }

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
