import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Account, AccountRequest } from '../../model';
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

  // Properties, Accessors

  @Input() accounts: Account[] = [];

  // Account
  private _account: AccountRequest = {
    accountType: '',
    currencyId: '',
  };

  get accountType() { return this._account.accountType; }
  set accountType(value: string) { this._account.accountType = value; }

  get currencyId() { return this._account.currencyId; }
  set currencyId(value: string) { this._account.currencyId = value; }

  // Account Types
  private _accountTypes: string[] = [];
  get accountTypes() { return this._accountTypes; }

  // Currencies
  private _currencies: Currency[] = [];
  get currencies() { return this._currencies; }

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
    this.accountService.create(this._account).subscribe((data) => {
      this.accounts.push(data);
      this.router.navigate(['/dashboard']);
    });
  }

  loadAccountTypes(): void {
    this.accountService.getTypes().subscribe(data => {
      this._accountTypes = data;
    });
  }

  loadCurrencies(): void {
    this.currencyService.getList().subscribe(data => {
      this._currencies = data;
    });
  }
}
