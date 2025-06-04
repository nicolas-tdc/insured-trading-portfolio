import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserHeaderComponent } from '../../../core/auth/component/user-header/user-header.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [
    CommonModule,
    UserHeaderComponent,
    MatToolbarModule,
    MatButtonModule,
    RouterLink,
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

}
