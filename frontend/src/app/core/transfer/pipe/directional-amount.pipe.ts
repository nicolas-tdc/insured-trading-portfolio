import { Pipe, PipeTransform } from '@angular/core';
import { Transfer } from '../model';

/**
 * DirectionalAmountPipe
 * 
 * Pipe for displaying the signed amount depending on transfer direction
 * 
 * @export
 */
@Pipe({
  name: 'directionalAmount',
  standalone: true
})
export class DirectionalAmountPipe implements PipeTransform {

  /**
   * Transform transfer into a signed amount depending on transfer direction
   * 
   * @param transfer Transfer
   * @param currentAccountNumber Current account number 
   * @returns number
   */
  transform(transfer: Transfer, currentAccountNumber: string | undefined): number {
    // Check if current account number and transfer are defined
    if (!currentAccountNumber || !transfer) return transfer.amount;

    // Return the signed amount depending on transfer direction
    if (transfer.sourceAccountNumber === currentAccountNumber) {
      return -transfer.amount;
    } else if (transfer.targetAccountNumber === currentAccountNumber) {
      return transfer.amount;
    } else {
      return transfer.amount;
    }
  }
}
