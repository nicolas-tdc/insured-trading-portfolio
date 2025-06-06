import { Component, OnInit } from '@angular/core';
import { CardListPoliciesComponent } from '../../core/policy/component/card-list-policies/card-list-policies.component';
import { CardListAccountsComponent } from '../../core/account/component/card-list-accounts/card-list-accounts.component';
import { FormCreateAccountComponent } from '../../core/account/component/form-create-account/form-create-account.component';
import { FormCreatePolicyComponent } from '../../core/policy/component/form-create-policy/form-create-policy.component';
import { AccountService } from '../../core/account/account.service';
import { PolicyService } from '../../core/policy/policy.service';
import { MatDialog } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-dashboard',
  imports: [
    CardListAccountsComponent,
    CardListPoliciesComponent,
    MatButtonModule,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  // Properties

  get accounts() { return this.accountService.userAccounts(); }
  get policies() { return this.policyService.userPolicies(); }

  // Lifecycle

  constructor(
    private accountService: AccountService,
    private policyService: PolicyService,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.accountService.reloadUserAccounts();
    this.policyService.reloadUserPolicies();
  }

  // Form dialogs

  openCreateAccountFormDialog(): void {
    const dialogRef = this.dialog.open(FormCreateAccountComponent, {
      width: '400px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'completed') {
        this.accountService.reloadUserAccounts();
      }
    });
  }

  openCreatePolicyFormDialog(): void {
    const dialogRef = this.dialog.open(FormCreatePolicyComponent, {
      width: '400px',
      data: this.accounts
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'completed') {
        this.policyService.reloadUserPolicies();
      }
    });
  }
}