import { Injectable } from '@angular/core';

/**
 * Sorter service
 * 
 * @export
 */
@Injectable({
  providedIn: 'root'
})
export class SorterService {

  /**
   * Sorts a list by a specific field depending on its type
   * 
   * @param list List to sort
   * @param field Field to sort by
   * @param fieldType Field type of the field to sort by
   * @param direction Sort direction
   * @returns 
   */
  public sortListByField(list: any[], field: string, fieldType: string, direction: 'asc' | 'desc'): any[] {
    switch (fieldType) {
      case 'number':
        return this.sortListByNumberField(list, field, direction);
      case 'string':
        return this.sortListByStringField(list, field, direction);
      default:
        return list;
    }
  }

  /**
   * 
   * @param list List to sort
   * @param field Field to sort by
   * @param direction Sort direction
   * @returns any[]
   */
  public sortListByNumberField(list: any[], field: string, direction: 'asc' | 'desc'): any[] {
    // Sort the list in ascending order
    if (direction === 'asc') {
      return list.sort((a, b) => a[field] - b[field]);
    }

    // Sort the list in descending order
    return list.sort((a, b) => b[field] - a[field]);
  }

  /**
   * 
   * @param list List to sort
   * @param field Field to sort by
   * @param direction Sort direction
   * @returns any[]
   */
  public sortListByStringField(list: any[], field: string, direction: 'asc' | 'desc'): any[] {
    // Sort the list in ascending order
    if (direction === 'asc') {
      return list.sort((a, b) => a[field].localeCompare(b[field]));
    }
    
    // Sort the list in descending order
    return list.sort((a, b) => b[field].localeCompare(a[field]));
  }
}
