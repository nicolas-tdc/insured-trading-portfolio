import { Component, Input } from '@angular/core';
import { Policy } from '../../model';

@Component({
  selector: 'app-policy-details',
  imports: [],
  templateUrl: './policy-details.component.html',
  styleUrl: './policy-details.component.scss'
})
export class PolicyDetailsComponent {

  // Properties, Accessors

  // Policy
  @Input() policy: Policy | null = null;
}
