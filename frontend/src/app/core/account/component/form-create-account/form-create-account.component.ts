import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Currency } from '../../../currency/model';
import { AccountService } from '../../service';
import { CurrencyService } from '../../../currency/service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogRef } from '@angular/material/dialog';
import { UserAccountsService } from '../../service';
import { AccountType } from '../../model/account-type.model';
import { MatCardModule } from '@angular/material/card';

/**
 * FormCreateAccountComponent
 * 
 * Form for creating a new account
 * 
 * @export
 */
@Component({
  selector: 'app-form-create-account',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatButtonModule,
    MatCardModule,
  ],
  templateUrl: './form-create-account.component.html',
  styleUrl: './form-create-account.component.scss'
})
export class FormCreateAccountComponent implements OnInit {

  /**
   * Account form group
   */
  accountForm!: FormGroup;

  /**
   * Account types
   */
  accountTypes: AccountType[] = [];

  /**
   * Currencies
   */
  currencies: Currency[] = [];

  /**
   * Initializes the component
   * Injects required services for account, user accounts and currency data
   * 
   * @param accountService Service for account data
   * @param userAccountsService Service for user accounts
   * @param currencyService Service for currency data
   * @param dialogRef Service for dialog
   */
  constructor(
    private readonly accountService: AccountService,
    private readonly userAccountsService: UserAccountsService,
    private readonly currencyService: CurrencyService,
    public readonly dialogRef: MatDialogRef<FormCreateAccountComponent>,
  ) { }

  /**
   * Lifecycle hook called on component initialization
   * Loads account types and currencies
   */
  ngOnInit(): void {
    // Load data
    this.loadAccountTypes();
    this.loadCurrencies();

    // Create form
    this.accountForm = new FormGroup({
      typeCode: new FormControl('', [
        Validators.required,
        Validators.minLength(1),
      ]),
      currencyCode: new FormControl('', [
        Validators.required,
      ]),
    });
  }

  /**
   * Creates a new account
   * 
   * @returns void
   */
  createAccount(): void {
    // Check form validity
    if (this.accountForm.invalid) return;

    // Create account, reload user accounts and close dialog
    this.accountService.create(this.accountForm.value).subscribe(() => {
      this.userAccountsService.reloadUserAccounts();
      this.dialogRef.close('completed');
    });
  }

  /**
   * Closes the dialog
   * 
   * @returns void
   */
  closeDialog(): void {
    this.dialogRef.close();
  }

  /**
   * Loads account types
   * 
   * @returns void
   */
  loadAccountTypes(): void {
    this.accountService.getTypes().subscribe(data => {
      this.accountTypes = data;
    });
  }

  /**
   * Loads currencies
   * 
   * @returns void
   */
  loadCurrencies(): void {
    this.currencyService.getList().subscribe(data => {
      this.currencies = data;
    });
  }
}
