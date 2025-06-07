import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Currency } from '../../../currency/model';
import { AccountService } from '../../service/account.service';
import { CurrencyService } from '../../../currency/currency.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatButton } from '@angular/material/button';
import { MatDialogRef } from '@angular/material/dialog';
import { UserAccountsService } from '../../service/user-accounts.service';

@Component({
  selector: 'app-form-create-account',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatButton,
  ],
  templateUrl: './form-create-account.component.html',
  styleUrl: './form-create-account.component.scss'
})
export class FormCreateAccountComponent implements OnInit {

  // Properties

  accountForm!: FormGroup;
  accountTypes: string[] = [];
  currencies: Currency[] = [];

  // Lifecycle

  constructor(
    private accountService: AccountService,
    private userAccountsService: UserAccountsService,
    private currencyService: CurrencyService,
    public dialogRef: MatDialogRef<FormCreateAccountComponent>,
  ) { }


  ngOnInit(): void {
    this.loadAccountTypes();
    this.loadCurrencies();

    this.accountForm = new FormGroup({
      accountType: new FormControl('', [
        Validators.required,
        Validators.minLength(1),
      ]),
      currencyId: new FormControl('', [
        Validators.required,
      ]),
    });
  }

  // API

  createAccount(): void {
    if (this.accountForm.invalid) return;

    this.accountService.create(this.accountForm.value).subscribe(() => {
      this.userAccountsService.reloadUserAccounts();
      this.dialogRef.close('completed');
    });
  }

  loadAccountTypes(): void {
    this.accountService.getTypes().subscribe(data => {
      this.accountTypes = data;
    });
  }

  loadCurrencies(): void {
    this.currencyService.getList().subscribe(data => {
      this.currencies = data;
    });
  }
}
