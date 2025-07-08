import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SvgIconService } from './core/shared/service/svg-icon.service';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';

  constructor(
    private svgIconService: SvgIconService,
  ) {
    this.svgIconService.registerIcons();
  }
}
