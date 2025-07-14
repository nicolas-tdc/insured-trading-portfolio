import { Pipe, PipeTransform } from '@angular/core';

/**
 * FormatAmountPipe
 * 
 * Pipe for formatting amounts
 * 
 * @export
 */
@Pipe({
  name: 'formatAmount'
})
export class FormatAmountPipe implements PipeTransform {

  /**
   * Transform amount into a fixed amount depending on the item's currency
   * 
   * @param {number} amount 
   * @param {any} item 
   * @returns {string}
   */
  transform(amount: number | undefined, item: any): string {
    return amount?.toFixed(item.currencyFractionDigits) + ' ' + item.currencySymbol;
  }
}
