import { Component, input } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { TransferAccountsService } from '../../service/transfer-accounts.service';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

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

  // Properties

  // Forms
  stepper = input<MatStepper | null>();
  selectAccountForm = input<FormGroup>();
  get enterExternalAccountForm(): FormGroup {
    return this.selectAccountForm()?.get('external') as FormGroup;
  }
  // Lifecycle

  constructor(
    private transferAccountsService: TransferAccountsService,
  ) { }

  // Validate accounts

  validateTargetAccount(): void {
    if (!this.enterExternalAccountForm || !this.enterExternalAccountForm?.valid) return;

    if (this.enterExternalAccountForm?.get('targetAccountNumber')) {
      this.transferAccountsService.clearInternalAccount();

      this.transferAccountsService.selectExternalAccount(
        this.enterExternalAccountForm?.get('targetAccountNumber')?.value
      );

      this.stepper()?.next();
    }
  }
}
