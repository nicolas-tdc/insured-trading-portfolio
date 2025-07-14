import { Component, input, InputSignal } from '@angular/core';
import { Clipboard } from '@angular/cdk/clipboard';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';

/**
 * Copy to clipboard component
 * 
 * @export
 */
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

  /**
   * Clipboard content
   * Provided as input
   */
  content: InputSignal<string | undefined> = input<string | undefined>();

  /**
   * Tooltip text
   */
  tooltipText: string = 'Copy to clipboard';

  /**
   * Initializes the component
   * Injects required services for clipboard
   * 
   * @param clipboard Clipboard service
   */
  constructor(
    private readonly clipboard: Clipboard
  ) { }

  /**
   * Copies the content to the clipboard
   * 
   * @returns void
   */
  copyToClipboard(): void {
    // Get the content
    const value = this.content?.();
    if (!value) return;

    // Copy to clipboard
    this.clipboard.copy(value);
    this.tooltipText = 'Copied';
  }

  /**
   * Resets the tooltip text
   * 
   * @returns void
   */
  resetTooltip(): void {
    this.tooltipText = 'Copy to clipboard';
  }
}
