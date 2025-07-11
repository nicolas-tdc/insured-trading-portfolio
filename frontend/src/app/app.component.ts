import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SvgIconService } from './core/shared/service/svg-icon.service';

/**
 * Main application component
 */
@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(
    private svgIconService: SvgIconService,
  ) {
    // Register SVG icons
    this.svgIconService.registerIcons();
  }
}
