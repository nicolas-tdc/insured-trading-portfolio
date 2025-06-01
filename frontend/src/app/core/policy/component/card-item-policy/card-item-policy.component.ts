import { Component, Input } from '@angular/core';
import { Policy } from '../../model';
import { RouterLink } from '@angular/router';
import { PolicyDetailsComponent } from '../policy-details/policy-details.component';

@Component({
  selector: 'app-card-item-policy',
  imports: [
    RouterLink,
    PolicyDetailsComponent,
  ],
  templateUrl: './card-item-policy.component.html',
  styleUrl: './card-item-policy.component.scss'
})
export class CardItemPolicyComponent {

  // Properties, Accessors

  // Policy
  private _policy: Policy = {
    id: '',
    accountNumber: '',
    policyNumber: '',
    policyType: '',
    premium: 0,
    coverageAmount: 0,
    status: '',
  };
  get policy() { return this._policy; }
  @Input({ required: true })
  set policy(value: Policy) { this._policy = value; }
}