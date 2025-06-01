import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserHeaderComponent } from '../../../core/auth/component/user-header/user-header.component';

@Component({
  selector: 'app-header',
  imports: [
    CommonModule,
    UserHeaderComponent,
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

}
