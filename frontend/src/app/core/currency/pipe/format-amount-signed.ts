import { Pipe, PipeTransform } from '@angular/core';

/**
 * FormatAmountSignedPipe
 * 
 * Pipe for formatting signed amounts
 * 
 * @export
 */
@Pipe({
  name: 'formatAmountSigned',
  standalone: true
})
export class FormatAmountSignedPipe implements PipeTransform {

  /**
   * Transform amount into a signed fixed amount depending on the item's currency
   * 
   * @param {number} amount 
   * @param {any} item 
   * @returns {string}
   */
  transform(amount: number | undefined, item: any): string {
    // Check if amount and item are defined
    if (amount === undefined || item === undefined) return '';

    // Fix amount to the currency's fraction digits
    const fixedAmount: string = Math.abs(amount).toFixed(item.currencyFractionDigits);

    // Get the sign
    const sign: string = amount < 0 ? '- ' : '+ ';

    return sign + fixedAmount + ' ' + item.currencySymbol;
  }
}
