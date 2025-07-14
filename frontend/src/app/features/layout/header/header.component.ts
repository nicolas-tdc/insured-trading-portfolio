import { Component } from '@angular/core';
import { UserHeaderComponent } from '../../../core/auth/component/user-header/user-header.component';
import { LogoComponent } from '../../../core/shared/component/logo/logo.component';
import { RouterLink } from '@angular/router';

/**
 * HeaderComponent
 * 
 * Displays the header section of the application.
 */
@Component({
  selector: 'app-header',
  imports: [
    UserHeaderComponent,
    LogoComponent,
    RouterLink,
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent { }
