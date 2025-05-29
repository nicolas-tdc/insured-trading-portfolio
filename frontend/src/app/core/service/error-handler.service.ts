import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable()
export class ErrorHandlerService {
  handleError(error: HttpErrorResponse): void {
    console.error('API Error:', error);
  }
}
