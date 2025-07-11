import { CommonModule } from '@angular/common';
import { Component, input } from '@angular/core';
import { CardItemPolicyComponent } from '../card-item-policy/card-item-policy.component';
import { Policy } from '../../model';
import { UserPoliciesService } from '../../service';
import { MatIconModule } from '@angular/material/icon';
import { SortIconPipe } from '../../../shared/pipe/sort-icon.pipe';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-card-list-policies',
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    CardItemPolicyComponent,
    SortIconPipe,
  ],
  templateUrl: './card-list-policies.component.html',
  styleUrl: './card-list-policies.component.scss'
})
export class CardListPoliciesComponent {

  // Properties

  // List of user policies
  get policies(): Policy[] | undefined {return this.userPoliciesService.userPolicies(); }

  // Lifecycle

  constructor(
    private userPoliciesService: UserPoliciesService,
  ) { }

  // Sorting

  get sortField() { return this.userPoliciesService.sortFieldValue; }
  get sortDirection() { return this.userPoliciesService.sortDirectionValue; }

  toggleSortByPolicyNumber(): void { this.userPoliciesService.sortByField('policyNumber'); }

  toggleSortByAccountNumber(): void { this.userPoliciesService.sortByField('accountNumber'); }

  toggleSortByPolicyType(): void { this.userPoliciesService.sortByField('typeCode'); }
}
