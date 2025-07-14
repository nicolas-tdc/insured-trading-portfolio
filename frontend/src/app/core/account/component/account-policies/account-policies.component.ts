import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { CopyToClipboardComponent } from '../../../shared/component/copy-to-clipboard/copy-to-clipboard.component';
import { AccountService } from '../../service';
import { Account } from '../../model';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { UserPoliciesService } from '../../../policy/service';
import { FormCreatePolicyComponent } from '../../../policy/component/form-create-policy/form-create-policy.component';
import { MatButtonModule } from '@angular/material/button';

/**
 * AccountPoliciesComponent
 * 
 * Displays account policies
 * 
 * @export
 */
@Component({
  selector: 'app-account-policies',
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    CopyToClipboardComponent,
  ],
  templateUrl: './account-policies.component.html',
  styleUrl: './account-policies.component.scss'
})
export class AccountPoliciesComponent {

  /**
   * Current user's account
   * Provided by the account service
   * 
   * @returns Account
   */
  public get account(): Account | undefined | null { return this.accountService.userAccount(); }

  /**
   * Initializes the component.
   * Injects required services for account data.
   * 
   * @param accountService Service for account data
   */
  constructor(
    private readonly accountService: AccountService,
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

    // Reload user policies and account on form completion
    dialogRef.afterClosed().subscribe(result => {
      if (result === 'completed') {
        this.userPoliciesService.reloadUserPolicies();
        this.accountService.reloadUserAccount();
      }
    });
  }
}
