import { CommonModule } from '@angular/common';
import { Component, input } from '@angular/core';
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
export class CardListPoliciesComponent {

  // Properties

  policies = input<Policy[] | null>();
}
