import { Component } from '@angular/core';
import { AccountRequest, AccountsService, AccountType, CurrenciesService, Currency } from '../../../core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

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
    private accountsService: AccountsService,
    private currenciesService: CurrenciesService,
  ) { }

  ngOnInit(): void {
    this.loadAccountTypes();
    this.loadCurrencies();
  }

  // API

  createAccount(): void {
    this.accountsService.create(this._account).subscribe(() => {
      this.router.navigate(['/dashboard']);
    });
  }

  loadAccountTypes(): void {
    this.accountsService.getTypes().subscribe(data => {
      this.accountTypes = data;
    });
  }

  loadCurrencies(): void {
    this.currenciesService.getList().subscribe(data => {
      this.currencies = data;
    });
  }
}
