import { Component } from '@angular/core';
import { ThemeService } from '../../service/theme.service';

/**
 * LogoComponent
 * 
 * Displays the application logo.
 * 
 * @export
 */
@Component({
  selector: 'app-logo',
  imports: [],
  templateUrl: './logo.component.html',
  styleUrl: './logo.component.scss'
})
export class LogoComponent {

  /**
   * Current browser theme
   */
  public theme: 'dark' | 'light' = 'light';

  /**
   * Initializes the component
   * Injects required services for theming
   * 
   * @param themeService Theme service
   */
  constructor(
    private readonly themeService: ThemeService
  ) { }

  /**
   * Lifecycle hook called on component initialization
   * Sets the theme based on user preference
   * 
   * @returns void
   */
  ngOnInit(): void {
    this.theme = this.themeService.getUserPreference();

    window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', e => {
      this.theme = e.matches ? 'dark' : 'light';
    });
  }
}
