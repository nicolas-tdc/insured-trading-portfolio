import { Pipe, PipeTransform } from '@angular/core';

/**
 * SortIconPipe
 * 
 * Pipe for upward and downward icons for sorting buttons
 * 
 * @export
 */
@Pipe({
  name: 'sortIcon'
})
export class SortIconPipe implements PipeTransform {

  /**
   * Transform string to upward or downward icon class
   * 
   * @param {string} value
   * @returns {string}
   */
  transform(value: string): string {
    return value === 'asc' ? 'arrow_upward' : 'arrow_downward';
  }
}
