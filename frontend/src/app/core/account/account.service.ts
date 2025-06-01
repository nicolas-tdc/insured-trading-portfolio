import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Account, AccountRequest } from './model';

@Injectable({ providedIn: 'root' })
export class AccountService {

  // Properties

  // API
  private apiUrl = '/api/account';

  // Lifecycle

  constructor(
    private http: HttpClient,
  ) { }

  // API

  create(request: AccountRequest): Observable<Account> {
    return this.http.post<Account>(`${this.apiUrl}`, request);
  }

  getItem(accountId: string): Observable<Account> {
    return this.http.get<Account>(`${this.apiUrl}/${accountId}`);
  }

  getList(): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.apiUrl}`);
  }

  getTypes(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/type`);
  }
}
