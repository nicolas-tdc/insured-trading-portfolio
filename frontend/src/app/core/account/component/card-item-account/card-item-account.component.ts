import { Component, input, InputSignal, WritableSignal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Clipboard } from '@angular/cdk/clipboard';
import { Account } from '../../model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { TransferService } from '../../../transfer/service';
import { CopyToClipboardComponent } from '../../../shared/component/copy-to-clipboard/copy-to-clipboard.component';
import { FormatAmountSignedPipe } from '../../../currency/pipe/format-amount-signed';
import { EntityService } from '../../../entity/service';

/**
 * CardItemAccountComponent
 * 
 * Displays a card for an account
 * 
 * @export
 */
@Component({
  selector: 'app-card-item-account',
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatChipsModule,
    MatIconModule,
    MatTooltipModule,
    CopyToClipboardComponent,
    FormatAmountSignedPipe,
  ],
  templateUrl: './card-item-account.component.html',
  styleUrl: './card-item-account.component.scss'
})
export class CardItemAccountComponent {

  /**
   * The account to display
   * Provided as input
   */
  account: InputSignal<Account | null | undefined> = input<Account | null>();

  /**
   * The tooltip text
   */
  tooltipText: string = 'Copy to clipboard';

  /**
   * Initializes the component.
   * Injects required services for clipboard, transfer and entity data.
   * 
   * @param clipboard Service for clipboard utils
   * @param transferService Service for transfer data
   * @param entityService Service for entity data
   */
  constructor(
    private readonly clipboard: Clipboard,
    private readonly transferService: TransferService,
    private readonly entityService: EntityService,
  ) { }

  /**
   * Copies the account number to the clipboard
   * 
   * @returns void
   */
  copyAccountNumber(): void {
    // Get the account number
    const accountNumber: string | undefined = this.account()?.accountNumber;
    if (!accountNumber) return;

    // Copy to clipboard
    this.clipboard.copy(accountNumber);
    this.tooltipText = 'Copied!';
  }

  /**
   * Resets the tooltip text
   * 
   * @returns void
   */
  resetTooltip() {
    // Reset tooltip
    this.tooltipText = 'Copy to clipboard';
  }

  /**
   * Opens the transfer form dialog
   * 
   * @returns void
   */
  openTransferDialog(): void {
    // Open the transfer form dialog
    this.transferService.openCreateTransferFormDialog(this.account()?.id);
  }

  /**
   * Gets the account status class
   * 
   * @returns string
   */
  getAccountStatusClass(): string {
    // Get the entity status class
    return this.entityService.getStatusClass(this.account()?.statusCode);
  }
}
