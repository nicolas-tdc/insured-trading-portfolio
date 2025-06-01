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
        console.error('API Error:', error.message || 'Unknown error');
        console.error('Status Code:', error.status);
        console.error('URL:', error.url || 'Unknown');
        if (error.error) {
          console.error('Backend error:', error.error);
        }

        return throwError(() => error);
      })
    );
  }
}
