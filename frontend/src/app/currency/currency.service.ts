import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Currency } from './model';

@Injectable({ providedIn: 'root' })
export class CurrencyService {
  private currencyApiUrl = '/api/currency';

  constructor(private http: HttpClient) {}

  getList(): Observable<Currency[]> {
    return this.http.get<Currency[]>(`${this.currencyApiUrl}`);
  }
}
