import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatAmountSigned',
  standalone: true
})
export class FormatAmountSignedPipe implements PipeTransform {
  transform(amount: number | undefined, item: any): string {
    if (amount === undefined || item === undefined) return '';

    const abs = Math.abs(amount).toFixed(item.currencyFractionDigits);
    const sign = amount < 0 ? '- ' : '+ ';

    return sign + abs + ' ' + item.currencySymbol;
  }
}
