import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AuthService } from '../core/services/auth.service';

import { AccountsService } from '../core/services/accounts.service';
import { Account, Transaction, TransferRequest } from '../core/models';

import { PoliciesService } from '../core/services/policies.service';
import { Policy } from '../core/models';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  user: any = null;

  accounts: Account[] = [];
  selectedAccountId: string | null = null;
  transactionAccountId: string | null = null;
  targetAccountNumber: string = '';
  transferAmount: number = 0;
  transactions: Transaction[] = [];

  policies: Policy[] = [];
  selectedPolicyType: string = 'life';
  coverageAmount: number = 100000;

  constructor(
    private authService: AuthService,
    private bankingService: AccountsService,
    private insuranceService: PoliciesService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getUser();
    this.loadAccounts();
    this.loadPolicies();
  }

  loadAccounts(): void {
    this.bankingService.getAccounts().subscribe(accounts => {
      this.accounts = accounts;
    });
  }

  openAccount(): void {
    this.bankingService.openAccount().subscribe(() => {
      this.loadAccounts();
    });
  }

  loadPolicies(): void {
    this.insuranceService.getPolicies().subscribe(policies => {
      this.policies = policies;
    });
  }

  applyForPolicy(): void {
    if (!this.selectedAccountId || this.coverageAmount <= 0) {
      alert ('Please enter valid details');
      return;
    }

    this.insuranceService.applyPolicy(
      this.selectedPolicyType, this.coverageAmount, this.selectedAccountId
    ).subscribe(() => {
      this.selectedAccountId = null;
      this.loadPolicies();
    });
  }

  viewTransactions(accountId: string): void {
    this.transactionAccountId = accountId;
    this.bankingService.getTransactions(accountId).subscribe(txs => {
      this.transactions = txs;
    });
  }

  showTransferForm(accountId: string): void {
    this.selectedAccountId = accountId;
  }

  performTransfer(sourceAccountId: string): void {
    if (!this.targetAccountNumber || this.transferAmount <= 0) {
      alert('Please enter valid details');
      return;
    }

    const transferRequest: TransferRequest = {
      sourceAccountId: sourceAccountId,
      targetAccountNumber: this.targetAccountNumber,
      amount: this.transferAmount,
    };

    this.bankingService.transfer(transferRequest).subscribe(() => {
      this.selectedAccountId = null;
      this.loadAccounts();
    });
  }

  logout(): void {
    this.authService.logout();
    window.location.reload();
  }
}
