import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SorterService {

  constructor() { }

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
