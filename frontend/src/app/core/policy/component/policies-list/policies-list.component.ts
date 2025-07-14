import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { PoliciesListItemComponent } from '../policies-list-item/policies-list-item.component';
import { Policy } from '../../model';
import { UserPoliciesService } from '../../service';
import { PoliciesListSortComponent } from '../policies-list-sort/policies-list-sort.component';
import { PoliciesListHeaderComponent } from '../policies-list-header/policies-list-header.component';
import { PoliciesListEmptyComponent } from '../policies-list-empty/policies-list-empty.component';

/**
 * PoliciesListComponent
 * 
 * Displays a list of policies
 * 
 * @export
 */
@Component({
  selector: 'app-policies-list',
  imports: [
    CommonModule,
    PoliciesListHeaderComponent,
    PoliciesListSortComponent,
    PoliciesListItemComponent,
    PoliciesListEmptyComponent,
  ],
  templateUrl: './policies-list.component.html',
  styleUrl: './policies-list.component.scss'
})
export class PoliciesListComponent {

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
}
