import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { PortfolioInfosDialogComponent } from '../portfolio-infos-dialog/portfolio-infos-dialog.component';

@Component({
  selector: 'app-portfolio-infos-header',
  imports: [
    MatIconModule,
    MatButtonModule,
  ],
  templateUrl: './portfolio-infos-header.component.html',
  styleUrl: './portfolio-infos-header.component.scss'
})
export class PortfolioInfosHeaderComponent {

  constructor(
    private readonly dialog: MatDialog,
  ) { }

  openPortfolioInfosDialog(): void {
    // Open the policy creation dialog form
    const dialogRef: MatDialogRef<PortfolioInfosDialogComponent> = this.dialog.open(
      PortfolioInfosDialogComponent,
      {
        width: '600px',
        height: '600px',
      }
    );
  }
}
