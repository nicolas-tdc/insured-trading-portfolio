import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'sortIcon'
})
export class SortIconPipe implements PipeTransform {

  transform(value: string): string {
    return value === 'asc' ? 'arrow_upward' : 'arrow_downward';
  }
}
