import { Component, input } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { TransferAccountsService } from '../../service/transfer-accounts.service';
import { AccountService, UserAccountsService } from '../../../account/service';
import { CommonModule } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import { FormatAmountSignedPipe } from '../../../currency/pipe/format-amount-signed';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { AccountSecure } from '../../../account/model/account-secure.model';
import { Account } from '../../../account/model';

@Component({
  selector: 'app-form-account-internal',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatButtonModule,
    MatFormFieldModule,
    FormatAmountSignedPipe,
  ],
  templateUrl: './form-account-internal.component.html',
  styleUrl: './form-account-internal.component.scss'
})
export class FormAccountInternalComponent {

  // Properties

  // Forms
  stepper = input<MatStepper | null>();
  selectAccountForm = input<FormGroup>();
  get selectInternalAccountForm(): FormGroup {
    return this.selectAccountForm()?.get('internal') as FormGroup;
  }
  public get sourceAccount(): Account | null | undefined { return this.accountService.userAccount(); }

  // Accounts
  public get userAccounts(): Account[] | undefined {
    if (!this.sourceAccount) return [];

    return this.userAccountsService.userAccounts()?.filter(
      (account: any) => account.id !== this.sourceAccount?.id);
  }

  // Lifecycle

  constructor(
    private transferAccountsService: TransferAccountsService,
    private userAccountsService: UserAccountsService,
    private accountService: AccountService,
  ) { }

  // Validate accounts

  validateTargetAccount(): void {
    if (!this.selectInternalAccountForm || !this.selectInternalAccountForm?.valid) return;

    if (this.selectInternalAccountForm?.get('targetAccountId')) {
      this.transferAccountsService.clearExternalAccount();

      this.transferAccountsService.selectInternalAccount(
        this.selectInternalAccountForm?.get('targetAccountId')?.value
      );
      
      this.stepper()?.next();
    }
  }

}
