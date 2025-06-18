import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatAmount'
})
export class FormatAmountPipe implements PipeTransform {

  transform(amount: number | undefined, item: any): unknown {
    return amount?.toFixed(item.currencyFractionDigits) + ' ' + item.currencySymbol;
  }
}
