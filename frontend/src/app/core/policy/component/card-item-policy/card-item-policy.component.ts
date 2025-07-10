import { Component, input } from '@angular/core';
import { Clipboard } from '@angular/cdk/clipboard';
import { Policy } from '../../model';
import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatIconModule } from '@angular/material/icon';
import { CopyToClipboardComponent } from '../../../shared/component/copy-to-clipboard/copy-to-clipboard.component';
import { EntityService } from '../../../entity/service';

@Component({
  selector: 'app-card-item-policy',
  imports: [
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatChipsModule,
    MatIconModule,
    MatTooltipModule,
    CopyToClipboardComponent,
  ],
  templateUrl: './card-item-policy.component.html',
  styleUrl: './card-item-policy.component.scss'
})
export class CardItemPolicyComponent {

  // Properties

  policy = input<Policy | null>();

  tooltipText = 'Copy to clipboard';

  // Lifecycle

  constructor(
    private clipboard: Clipboard,
    private entityService: EntityService,
  ) { }

  copyPolicyNumber() {
    const policyNumber = this.policy()?.policyNumber;
    if (!policyNumber) return;

    this.clipboard.copy(policyNumber);
    this.tooltipText = 'Copied!';
  }

  copyAccountNumber() {
    const accountNumber = this.policy()?.accountNumber;
    if (!accountNumber) return;

    this.clipboard.copy(accountNumber);
    this.tooltipText = 'Copied!';
  }

  resetTooltip() {
    this.tooltipText = 'Copy to clipboard';
  }

  getPolicyStatusClass(): string {
    return this.entityService.getStatusClass(this.policy()?.statusCode);
  }
}