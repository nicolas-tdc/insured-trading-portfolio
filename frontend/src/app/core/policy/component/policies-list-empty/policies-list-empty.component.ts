import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-policies-list-empty',
  imports: [
    MatCardModule,
  ],
  templateUrl: './policies-list-empty.component.html',
  styleUrl: './policies-list-empty.component.scss'
})
export class PoliciesListEmptyComponent {

}
