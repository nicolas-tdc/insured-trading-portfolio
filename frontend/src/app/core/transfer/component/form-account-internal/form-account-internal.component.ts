import { Component, input, InputSignal } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { TransferAccountsService } from '../../service/transfer-accounts.service';
import { AccountService, UserAccountsService } from '../../../account/service';
import { CommonModule } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import { FormatAmountSignedPipe } from '../../../currency/pipe/format-amount-signed';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Account } from '../../../account/model';

/**
 * FormAccountInternalComponent
 * 
 * Form for selecting an internal account
 * 
 * @export
 */
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

  /**
   * Stepper
   * Provided as input
   */
  stepper: InputSignal<MatStepper | null | undefined> = input<MatStepper | null>();

  /**
   * Select account form
   * Provided as input
   */
  selectAccountForm: InputSignal<FormGroup | undefined> = input<FormGroup>();

  /**
   * Internal account form
   * 
   * @readonly
   * @type {FormGroup}
   */
  get selectInternalAccountForm(): FormGroup {
    return this.selectAccountForm()?.get('internal') as FormGroup;
  }

  /**
   * Get source account
   * 
   * @readonly
   * @type {Account | null | undefined}
   */
  public get sourceAccount(): Account | null | undefined { return this.accountService.userAccount(); }

  /**
   * Get user accounts that are not the source account and have the same currency
   * 
   * @readonly
   * @type {Account[] | undefined}
   */
  public get userAccounts(): Account[] | undefined {
    if (!this.sourceAccount) return [];

    // Filter from all user accounts
    return this.userAccountsService.userAccounts()?.filter(
      (account: any) =>
        account.id !== this.sourceAccount?.id && account.currencyCode === this.sourceAccount?.currencyCode
    );
  }

  /**
   * Initializes the component
   * Injects required services for transfer accounts, user accounts and account
   * 
   * @param transferAccountsService Service for transfer accounts
   * @param userAccountsService Service for user accounts
   * @param accountService Service for account
   */
  constructor(
    private readonly transferAccountsService: TransferAccountsService,
    private readonly userAccountsService: UserAccountsService,
    private readonly accountService: AccountService,
  ) { }

  /**
   * Validate target account
   * 
   * @returns void
   */
  validateTargetAccount(): void {
    // Check if form is valid
    if (!this.selectInternalAccountForm || !this.selectInternalAccountForm?.valid) return;

    // Clear external account and select internal account
    if (this.selectInternalAccountForm?.get('targetAccountId')) {
      this.transferAccountsService.clearExternalAccount();
      this.transferAccountsService.selectInternalAccount(
        this.selectInternalAccountForm?.get('targetAccountId')?.value
      );

      // Go to next step
      this.stepper()?.next();
    }
  }

}
