import { Component, input, InputSignal } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { TransferAccountsService } from '../../service/transfer-accounts.service';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

/**
 * FormAccountExternalComponent
 * 
 * Form for selecting an external account
 * 
 * @export
 */
@Component({
  selector: 'app-form-account-external',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
  ],
  templateUrl: './form-account-external.component.html',
  styleUrl: './form-account-external.component.scss'
})
export class FormAccountExternalComponent {

  /**
   * Initializes the component
   * Injects required services for transfer accounts
   * 
   * @param readonlytransferAccountsService Service for transfer accounts
   */
  constructor(
    private readonly transferAccountsService: TransferAccountsService,
  ) { }

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
   * External account form
   * 
   * @readonly
   * @type {FormGroup}
   */
  get enterExternalAccountForm(): FormGroup {
    return this.selectAccountForm()?.get('external') as FormGroup;
  }

  /**
   * Validate target account
   * 
   * @returns void
   */
  validateTargetAccount(): void {
    // Check if form is valid
    if (!this.enterExternalAccountForm || !this.enterExternalAccountForm?.valid) return;

    // Clear internal account and select external account
    if (this.enterExternalAccountForm?.get('targetAccountNumber')) {
      this.transferAccountsService.clearInternalAccount();
      this.transferAccountsService.selectExternalAccount(
        this.enterExternalAccountForm?.get('targetAccountNumber')?.value
      );

      // Go to next step
      this.stepper()?.next();
    }
  }
}
