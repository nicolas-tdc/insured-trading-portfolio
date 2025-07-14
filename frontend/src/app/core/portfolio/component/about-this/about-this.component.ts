import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-about-this',
  imports: [
    MatCardModule,
    MatIconModule,
  ],
  templateUrl: './about-this.component.html',
  styleUrl: './about-this.component.scss'
})
export class AboutThisComponent {

}
