import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';

/**
 * FooterComponent
 * 
 * Displays the footer section of the application.
 */
@Component({
  selector: 'app-footer',
  imports: [
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    MatIconModule,
  ],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss'
})
export class FooterComponent { }
