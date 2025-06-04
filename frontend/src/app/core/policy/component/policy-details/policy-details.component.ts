import { Component, input, Input } from '@angular/core';
import { Policy } from '../../model';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-policy-details',
  imports: [
    CommonModule,
    MatCardModule,
    MatChipsModule,
  ],
  templateUrl: './policy-details.component.html',
  styleUrl: './policy-details.component.scss'
})
export class PolicyDetailsComponent {

  // Properties

  policy = input<Policy | null>();
}
