import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { CardItemPolicyComponent } from '../card-item-policy/card-item-policy.component';
import { Policy } from '../../model';

@Component({
  selector: 'app-card-list-policies',
  imports: [
    CommonModule,
    CardItemPolicyComponent,
  ],
  templateUrl: './card-list-policies.component.html',
  styleUrl: './card-list-policies.component.scss'
})
export class ListCardPoliciesComponent {

  // Properties

  private _policies: Policy[] = [];

  // Accessors

  @Input()
  set policies(value: Policy[]) { this._policies = value; }

  get policies() { return this._policies; }
}
