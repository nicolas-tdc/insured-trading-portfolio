import { Injectable } from '@angular/core';

/**
 * Theme service
 * 
 * @export
 */
@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  /**
   * Get user theme preference
   * 
   * @returns 'dark' | 'light'
   */
  getUserPreference(): 'dark' | 'light' {
    return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
  }

}