import { Component, OnInit } from '@angular/core';
import { CardListPoliciesComponent } from '../../core/policy/component/card-list-policies/card-list-policies.component';
import { CardListAccountsComponent } from '../../core/account/component/card-list-accounts/card-list-accounts.component';
import { FormCreateAccountComponent } from '../../core/account/component/form-create-account/form-create-account.component';
import { FormCreatePolicyComponent } from '../../core/policy/component/form-create-policy/form-create-policy.component';
import { MatDialog } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { UserAccountsService } from '../../core/account/service';
import { UserPoliciesService } from '../../core/policy/service';

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

  get accounts() { return this.userAccountsService.userAccounts(); }

  get policies() { return this.userPoliciesService.userPolicies(); }

  // Lifecycle

  constructor(
    private userAccountsService: UserAccountsService,
    private userPoliciesService: UserPoliciesService,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.userAccountsService.reloadUserAccounts();
    this.userPoliciesService.reloadUserPolicies();
  }

  ngOnDestroy(): void {
    this.userAccountsService.clearUserAccounts();
    this.userPoliciesService.clearUserPolicies();
  }

  // Form dialogs

  openCreateAccountFormDialog(): void {
    const dialogRef = this.dialog.open(FormCreateAccountComponent, {
      width: '600px',
      height: '500px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'completed') {
        this.userAccountsService.reloadUserAccounts();
      }
    });
  }

  openCreatePolicyFormDialog(): void {
    const dialogRef = this.dialog.open(FormCreatePolicyComponent, {
      width: '600px',
      height: '500px',
      data: this.accounts
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'completed') {
        this.userPoliciesService.reloadUserPolicies();
      }
    });
  }
}