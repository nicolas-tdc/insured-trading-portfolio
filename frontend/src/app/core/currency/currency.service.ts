import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Currency } from './model';

@Injectable({ providedIn: 'root' })
export class CurrencyService {

  // Properties

  // API
  private apiUrl = '/api/currency';

  // Lifecycle

  constructor(
    private http: HttpClient,
  ) { }

  // API

  getList(): Observable<Currency[]> {
    return this.http.get<Currency[]>(`${this.apiUrl}`);
  }
}
