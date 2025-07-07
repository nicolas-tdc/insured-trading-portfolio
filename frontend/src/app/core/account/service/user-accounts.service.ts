import { HttpClient } from '@angular/common/http';
import { computed, Injectable, resource, signal } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';
import { Account } from '../model';
import { AuthService } from '../../auth/service';

@Injectable({
  providedIn: 'root'
})
export class UserAccountsService {

  // Lifecycle

  constructor(
    private http: HttpClient,
    private authService: AuthService,
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

  // Reactive list of accounts

  private userAccountsResource = resource<Account[], {}>({
    params: () => ({}),
    loader: async () => {
      return await firstValueFrom(this.getUserAccountsList());
    }
  });

  public userAccounts = computed(() => this.userAccountsResource.value());
  public reloadUserAccounts(): void { this.userAccountsResource.reload(); }
  public clearUserAccounts(): void { this.userAccountsResource.set([]); }

  // Sorting

  public reverseUserAccounts(): void {
    this.userAccountsResource.set(this.userAccountsResource.value()?.reverse() ?? []);
  }

  public sortByBalance(direction: 'asc' | 'desc'): void {
    if (direction === 'asc') {
      this.userAccountsResource.set(
      this.userAccountsResource.value()?.sort((a, b) => a.balance - b.balance) ?? []);
    } else {
      this.userAccountsResource.set(
      this.userAccountsResource.value()?.sort((a, b) => b.balance - a.balance) ?? []);
    }
  }

  public sortByAccountType(direction: 'asc' | 'desc'): void {
    if (direction === 'asc') {
      this.userAccountsResource.set(
      this.userAccountsResource.value()?.sort((a, b) => a.accountType.localeCompare(b.accountType)) ?? []);
    } else {
      this.userAccountsResource.set(
      this.userAccountsResource.value()?.sort((a, b) => b.accountType.localeCompare(a.accountType)) ?? []);
    }
  }

  public sortByAccountNumber(direction: 'asc' | 'desc'): void {
    if (direction === 'asc') {
      this.userAccountsResource.set(
      this.userAccountsResource.value()?.sort((a, b) => a.accountNumber.localeCompare(b.accountNumber)) ?? []);
    } else {
      this.userAccountsResource.set(
      this.userAccountsResource.value()?.sort((a, b) => b.accountNumber.localeCompare(a.accountNumber)) ?? []);
    }
  }
}
