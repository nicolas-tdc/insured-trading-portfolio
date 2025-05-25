import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Account, Transaction, TransferRequest } from '../models';

@Injectable({ providedIn: 'root' })
export class AccountsService {
  private apiUrl = '/api/banking';

  constructor(private http: HttpClient) {}

  getAccounts(): Observable<Account[]> {
    const accounts: Observable<Account[]> = this.http.get<Account[]>(`${this.apiUrl}/accounts`);
    console.log('Accounts loaded:', accounts);
    accounts.subscribe(data => {
      for (const account of data) {
        console.log(`Account ID: ${account.id}, Balance: ${account.balance}`);
      }
    });
    return accounts;
  }

  openAccount(): Observable<Account> {
    return this.http.post<Account>(`${this.apiUrl}/open-account`, {});
  }

  transfer(transferRequest: TransferRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/transfer`, transferRequest);
  }

  getTransactions(accountId: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiUrl}/transactions/${accountId}`);
  }
}
