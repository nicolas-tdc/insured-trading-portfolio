import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { UserPoliciesService } from '../../service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { FormCreatePolicyComponent } from '../form-create-policy/form-create-policy.component';

/**
 * PoliciesListHeaderComponent
 * 
 * Displays a list of user policies.
 * 
 * @export
 */
@Component({
  selector: 'app-policies-list-header',
  imports: [
    MatCardModule,
    MatButtonModule,
  ],
  templateUrl: './policies-list-header.component.html',
  styleUrl: './policies-list-header.component.scss'
})
export class PoliciesListHeaderComponent {

  /**
   * Initializes the component.
   * Injects required services for user policies data.
   * 
   * @param userPoliciesService Service for user policies
   * @param dialog Service for dialog
   */
  constructor(
    private readonly userPoliciesService: UserPoliciesService,
    private readonly dialog: MatDialog,
  ) { }

  /**
   * Opens a dialog form to create a new policy.
   * 
   * @returns void
   */
  openCreatePolicyFormDialog(): void {
    // Open the policy creation dialog form
    const dialogRef: MatDialogRef<FormCreatePolicyComponent> = this.dialog.open(
      FormCreatePolicyComponent,
      {
        width: '600px',
        height: '500px',
      }
    );

    // Reload user policies on form completion
    dialogRef.afterClosed().subscribe(result => {
      if (result === 'completed') {
        this.userPoliciesService.reloadUserPolicies();
      }
    });
  }
}
