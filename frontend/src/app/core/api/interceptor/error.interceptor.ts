import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpErrorResponse
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        const message = error?.error?.message || error.message || 'Unknown error';

        console.error('API Error:', message);
        console.error('Status Code:', error.status);
        console.error('URL:', error.url || 'Unknown');

        const formattedError = {
          ...error,
          message,
        };

        return throwError(() => formattedError);
      })
    );
  }
}

