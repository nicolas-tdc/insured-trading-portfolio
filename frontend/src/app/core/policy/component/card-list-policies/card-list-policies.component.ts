import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { CardItemPolicyComponent } from '../card-item-policy/card-item-policy.component';
import { Policy } from '../../model';
import { PolicyService } from '../../policy.service';

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

  // Properties, Accessors

  @Input() policies: Policy[] = []
}
