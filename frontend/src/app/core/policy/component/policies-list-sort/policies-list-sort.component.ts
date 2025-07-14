import { Component, Signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { SortIconPipe } from '../../../shared/pipe/sort-icon.pipe';
import { Policy } from '../../model';
import { UserPoliciesService } from '../../service';

/**
 * PoliciesListSortComponent
 * 
 * Component for sorting user policies
 * 
 * @export
 */
@Component({
  selector: 'app-policies-list-sort',
  imports: [
    MatListModule,
    MatButtonModule,
    MatIconModule,
    SortIconPipe,
  ],
  templateUrl: './policies-list-sort.component.html',
  styleUrl: './policies-list-sort.component.scss'
})
export class PoliciesListSortComponent {

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
