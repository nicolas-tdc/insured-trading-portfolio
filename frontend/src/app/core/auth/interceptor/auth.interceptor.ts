import { Injectable, inject } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../service';

/**
 * Auth interceptor
 * 
 * @export
 */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  /**
   * Intercept http requests for authentication
   * 
   * @param req Http request
   * @param next Http handler
   * @returns Http event
   */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Inject required services
    const authService = inject(AuthService);

    // Get token
    const token = authService.getToken();

    // Add token to request
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }

    return next.handle(req);
  }
}
