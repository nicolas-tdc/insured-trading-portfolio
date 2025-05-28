import { Component, Input } from '@angular/core';
import { Policy } from '../../../core';

@Component({
  selector: 'app-card-item-policy',
  imports: [],
  templateUrl: './card-item-policy.component.html',
  styleUrl: './card-item-policy.component.scss'
})
export class CardItemPolicyComponent {

  // Properties

  private _policy: Policy | null = null;

  // Accessors

  @Input({ required: true })
  set policy(value: Policy) { this._policy = value; }

  get policyId() { return this._policy?.id; }
  get policyType() { return this._policy?.type; }
  get coverageAmount() { return this._policy?.coverageAmount.toFixed(2); }
  get premium() { return this._policy?.premium.toFixed(2); }
  get status() { return this._policy?.status; }
  get accountNumber() { return this._policy?.accountNumber; }
}
