import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Currency } from '../model';

/**
 * Currency service
 * 
 * @export
 */
@Injectable({ providedIn: 'root' })
export class CurrencyService {

  /**
   * API URL
   */
  private apiUrl: string = '/api/currency';

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
   * Gets list of currencies
   * 
   * @returns List of currencies
   */
  getList(): Observable<Currency[]> {
    return this.http.get<Currency[]>(`${this.apiUrl}`);
  }
}
