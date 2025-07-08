import { Component, input, Input } from '@angular/core';
import { Clipboard } from '@angular/cdk/clipboard';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-copy-to-clipboard',
  imports: [
    MatButtonModule,
    MatTooltipModule,
    MatIconModule,
  ],
  templateUrl: './copy-to-clipboard.component.html',
  styleUrls: ['./copy-to-clipboard.component.scss'],
})
export class CopyToClipboardComponent {

  // Properties

  content = input<string | undefined>();
  tooltipText = 'Copy to clipboard';

  // Lifecycle

  constructor(
    private clipboard: Clipboard
  ) { }

  // Clipboard

  copyToClipboard() {
    const value = this.content?.();
    if (!value) return;

    this.clipboard.copy(value);
    this.tooltipText = 'Copied';
  }

  resetTooltip() {
    this.tooltipText = 'Copy to clipboard';
  }
}
