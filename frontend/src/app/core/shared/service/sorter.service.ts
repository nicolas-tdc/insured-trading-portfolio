import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SorterService {

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

  public sortListByNumberField(list: any[], field: string, direction: 'asc' | 'desc'): any[] {
    if (direction === 'asc') {
      return list.sort((a, b) => a[field] - b[field]);
    }
    
    return list.sort((a, b) => b[field] - a[field]);
  }

  public sortListByStringField(list: any[], field: string, direction: 'asc' | 'desc'): any[] {
    if (direction === 'asc') {
      return list.sort((a, b) => a[field].localeCompare(b[field]));
    }
    
    return list.sort((a, b) => b[field].localeCompare(a[field]));
  }
}
