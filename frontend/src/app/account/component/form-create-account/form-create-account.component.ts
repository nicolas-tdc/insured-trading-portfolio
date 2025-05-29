import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AccountRequest, AccountType } from '../../model';
import { Currency } from '../../../currency/model';
import { AccountService } from '../../account.service';
import { CurrencyService } from '../../../currency/currency.service';

@Component({
  selector: 'app-form-create-account',
  imports: [
    CommonModule,
    FormsModule,
  ],
  templateUrl: './form-create-account.component.html',
  styleUrl: './form-create-account.component.scss'
})
export class FormCreateAccountComponent {

  // Properties

  _account: AccountRequest = {
    accountTypeId: '',
    currencyId: ''
  };
  accountTypes: AccountType[] = [];
  currencies: Currency[] = [];

  // Accessors

  set accountTypeId(value: string) { this._account.accountTypeId = value; }
  get accountTypeId() { return this._account.accountTypeId; }

  set currencyId(value: string) { this._account.currencyId = value; }
  get currencyId() { return this._account.currencyId; }

  // Lifecycle

  constructor(
    private router: Router,
    private accountService: AccountService,
    private currencyService: CurrencyService,
  ) { }

  ngOnInit(): void {
    this.loadAccountTypes();
    this.loadCurrencies();
  }

  // API

  createAccount(): void {
    this.accountService.create(this._account).subscribe(() => {
      this.router.navigate(['/dashboard']);
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
