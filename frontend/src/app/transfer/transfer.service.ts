import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Transfer, TransferRequest } from './model';

@Injectable({ providedIn: 'root' })
export class TransferService {
  private apiUrl = '/api/transfer';

  constructor(private http: HttpClient) {}

  create(transferRequest: TransferRequest): Observable<Transfer> {
    return this.http.post<Transfer>(`${this.apiUrl}`, transferRequest);
  }

  getAccountList(accountId: string): Observable<Transfer[]> {
    return this.http.get<Transfer[]>(`${this.apiUrl}/${accountId}`);
  }
}
