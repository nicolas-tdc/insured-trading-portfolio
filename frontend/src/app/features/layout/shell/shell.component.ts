import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';
import { MatDividerModule } from '@angular/material/divider';

/**
 * ShellComponent
 * 
 * Displays the main layout of the application, including the header, main content, and footer.
 */
@Component({
  selector: 'app-shell',
  imports: [
    RouterOutlet,
    FooterComponent,
    HeaderComponent,
    MatDividerModule,
  ],
  templateUrl: './shell.component.html',
  styleUrl: './shell.component.scss'
})
export class ShellComponent { }
