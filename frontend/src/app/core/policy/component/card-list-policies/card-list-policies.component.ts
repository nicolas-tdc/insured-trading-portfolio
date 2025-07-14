import { CommonModule } from '@angular/common';
import { Component, input, Signal } from '@angular/core';
import { CardItemPolicyComponent } from '../card-item-policy/card-item-policy.component';
import { Policy } from '../../model';
import { UserPoliciesService } from '../../service';
import { MatIconModule } from '@angular/material/icon';
import { SortIconPipe } from '../../../shared/pipe/sort-icon.pipe';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';

/**
 * CardListPoliciesComponent
 * 
 * Displays a list of policies
 * 
 * @export
 */
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

  /**
   * The list of policies
   * Provided by the UserPoliciesService
   * 
   * @returns Signal<Policy[]>
   */
  get policies(): Policy[] | undefined {return this.userPoliciesService.userPolicies(); }

  /**
   * Initializes the component
   * Injects required services for user policies
   * 
   * @param userPoliciesService Service for user policies
   */
  constructor(
    private readonly userPoliciesService: UserPoliciesService,
  ) { }

  /**
   * The field to sort by
   * Provided by the UserPoliciesService
   * 
   * @returns Signal<keyof Policy>
   */
  get sortField(): Signal<keyof Policy> { return this.userPoliciesService.sortFieldValue; }

  /**
   * The direction to sort by
   * Provided by the UserPoliciesService
   * 
   * @returns Signal<'asc' | 'desc'>
   */
  get sortDirection(): Signal<'asc' | 'desc'> { return this.userPoliciesService.sortDirectionValue; }

  /**
   * Sorts policies by policy number
   * Executed by the UserPoliciesService
   * 
   * @returns void
   */
  toggleSortByPolicyNumber(): void { this.userPoliciesService.sortByField('policyNumber'); }

  /**
   * Sorts policies by account number
   * Executed by the UserPoliciesService
   * 
   * @returns void
   */
  toggleSortByAccountNumber(): void { this.userPoliciesService.sortByField('accountNumber'); }

  /**
   * Sorts policies by policy type
   * Executed by the UserPoliciesService
   * 
   * @returns void
   */
  toggleSortByPolicyType(): void { this.userPoliciesService.sortByField('typeCode'); }
}
