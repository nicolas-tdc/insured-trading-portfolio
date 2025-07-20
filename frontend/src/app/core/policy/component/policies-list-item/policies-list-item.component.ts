import { Component, input, InputSignal } from '@angular/core';
import { Clipboard } from '@angular/cdk/clipboard';
import { Policy } from '../../model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatIconModule } from '@angular/material/icon';
import { CopyToClipboardComponent } from '../../../shared/component/copy-to-clipboard/copy-to-clipboard.component';
import { EntityService } from '../../../entity/service';
import { MatDialog } from '@angular/material/dialog';
import { PolicyDetailsComponent } from '../policy-details/policy-details.component';
import { PolicyService } from '../../service';

@Component({
  selector: 'app-policies-list-item',
  imports: [
    MatCardModule,
    MatButtonModule,
    MatChipsModule,
    MatIconModule,
    MatTooltipModule,
    CopyToClipboardComponent,
  ],
  templateUrl: './policies-list-item.component.html',
  styleUrl: './policies-list-item.component.scss'
})
export class PoliciesListItemComponent {

  /**
   * The policy to display
   * Provided as input
   */
  policy: InputSignal<Policy | null | undefined> = input<Policy | null>();

  /**
   * Tooltip text
   */
  tooltipText: string = 'Copy to clipboard';

  /**
   * Initializes the component
   * Injects required services for clipboard and entities
   * 
   * @param clipboard Service for clipboard
   * @param entityService Service for entities
   * @param dialog Service for dialog
   */
  constructor(
    private readonly clipboard: Clipboard,
    private readonly entityService: EntityService,
    private readonly dialog: MatDialog,
    private readonly policyService: PolicyService,
  ) { }

  /**
   * Copies the policy number to the clipboard
   * 
   * @returns void
   */
  copyPolicyNumber(): void {
    // Get the policy number
    const policyNumber = this.policy()?.policyNumber;
    if (!policyNumber) return;

    // Copy to clipboard
    this.clipboard.copy(policyNumber);
    this.tooltipText = 'Copied!';
  }

  /**
   * Copies the account number to the clipboard
   * 
   * @returns void
   */
  copyAccountNumber() {
    // Get the account number
    const accountNumber = this.policy()?.accountNumber;
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
  resetTooltip(): void {
    this.tooltipText = 'Copy to clipboard';
  }

  /**
   * Returns the class for the policy status
   * 
   * @returns string
   */
  getPolicyStatusClass(): string {
    return this.entityService.getStatusClass(this.policy()?.statusCode);
  }

  /**
   * Opens the policy details dialog
   * 
   * @returns void
   */
  openPolicyDetailsDialog(): void {
    // Select the policy
    const policyId = this.policy()?.id;
    if (!policyId) return;

    if (policyId !== this.policyService.selectedPolicyIdValue) {
      this.policyService.selectPolicy(policyId);
    }

    // Open the dialog
    this.dialog.open(
      PolicyDetailsComponent,
      {
        width: '600px',
        height: '500px',
      });
  }
}