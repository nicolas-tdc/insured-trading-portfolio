import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

/**
 * Error interceptor
 * 
 * @export
 */
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  /**
   * Intercept http requests errors
   * 
   * @param req Http request
   * @param next Http handler
   * @returns Http event
   */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        // Get error message
        const message = error?.error?.message || error.message || 'Unknown error';

        // Log error
        console.error('API Error:', message);
        console.error('Status Code:', error.status);
        console.error('URL:', error.url || 'Unknown');

        // Format error
        const formattedError = {
          ...error,
          message,
        };

        return throwError(() => formattedError);
      })
    );
  }
}

