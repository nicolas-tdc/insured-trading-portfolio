import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-contact-links',
  imports: [
    MatListModule,
    MatIconModule,
  ],
  templateUrl: './contact-links.component.html',
  styleUrl: './contact-links.component.scss'
})
export class ContactLinksComponent {

}
