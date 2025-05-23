import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { BankingService } from '../services/banking.service';
import { Account } from '../models/account.model';
import { Transaction } from '../models/transaction.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, FormsModule],
  standalone: true,
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  user: any = null;
  accounts: Account[] = [];
  selectedAccountNumber: string | null = null;
  transactionAccountNumber: string | null = null;
  targetAccountNumber: string = '';
  transferAmount: number = 0;
  transactions: Transaction[] = [];

  constructor(
    private authService: AuthService,
    private bankingService: BankingService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getUser();
    this.loadAccounts();
  }

  loadAccounts(): void {
    this.bankingService.getAccounts().subscribe(accounts => {
      this.accounts = accounts;
      accounts.forEach(account => {
        console.log(`Account ID: ${account.id}, Balance: ${account.balance}`);
      }
      );
    });
  }

  openAccount(): void {
    this.bankingService.openAccount().subscribe(() => {
      this.loadAccounts();
    });
  }

  viewTransactions(accountId: string): void {
    this.transactionAccountNumber = accountId;
    this.bankingService.getTransactions(accountId).subscribe(txs => {
      this.transactions = txs;
    });
  }

  showTransferForm(accountId: string): void {
    this.selectedAccountNumber = accountId;
  }

  performTransfer(sourceAccountNumber: string): void {
    if (!this.targetAccountNumber || this.transferAmount <= 0) {
      alert('Please enter valid details');
      return;
    }

    this.bankingService.transfer(sourceAccountNumber, this.targetAccountNumber, this.transferAmount).subscribe(() => {
      this.selectedAccountNumber = null;
      this.loadAccounts();
    });
  }

  logout(): void {
    this.authService.logout();
    window.location.reload();
  }
}
