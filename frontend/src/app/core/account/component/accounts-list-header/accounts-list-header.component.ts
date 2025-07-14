import { Component } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { UserAccountsService } from '../../service';
import { FormCreateAccountComponent } from '../form-create-account/form-create-account.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

/**
 * AccountsListHeaderComponent
 * 
 * Displays a list of user accounts.
 * Provides buttons to create new accounts.
 * 
 * @export
 */
@Component({
  selector: 'app-accounts-list-header',
  imports: [
    MatCardModule,
    MatButtonModule
  ],
  templateUrl: './accounts-list-header.component.html',
  styleUrl: './accounts-list-header.component.scss'
})
export class AccountsListHeaderComponent {

  /**
   * Initializes the component.
   * Injects required services for user accounts data.
   * 
   * @param userAccountsService Service for user accounts
   * @param dialog Service for dialog
   */
  constructor(
    private readonly userAccountsService: UserAccountsService,
    private readonly dialog: MatDialog
  ) { }

  /**
   * Opens a dialog form to create a new account.
   * 
   * @returns void
   */
  openCreateAccountFormDialog(): void {
    // Open the policy creation dialog form
    const dialogRef: MatDialogRef<FormCreateAccountComponent> = this.dialog.open(
      FormCreateAccountComponent,
      {
        width: '600px',
        height: '500px',
      }
    );

    // Reload user accounts on form completion
    dialogRef.afterClosed().subscribe(result => {
      if (result === 'completed') {
        this.userAccountsService.reloadUserAccounts();
      }
    });
  }
}
