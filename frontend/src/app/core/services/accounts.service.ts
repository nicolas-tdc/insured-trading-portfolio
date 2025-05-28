import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Account, AccountRequest, AccountType } from '../models';

@Injectable({ providedIn: 'root' })
export class AccountsService {
  private accountApiUrl = '/api/account';

  constructor(private http: HttpClient) {}

  create(request: AccountRequest): Observable<Account> {
    return this.http.post<Account>(`${this.accountApiUrl}`, request);
  }

  getItem(accountId: string): Observable<Account> {
    return this.http.get<Account>(`${this.accountApiUrl}/${accountId}`);
  }

  getList(): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.accountApiUrl}`);
  }

  getTypes(): Observable<AccountType[]> {
    return this.http.get<AccountType[]>(`${this.accountApiUrl}/type`);
  }
}
