import { Component } from '@angular/core';
import { AboutMeComponent } from '../about-me/about-me.component';
import { ContactLinksComponent } from '../contact-links/contact-links.component';
import { TechStackComponent } from '../tech-stack/tech-stack.component';
import { AboutThisComponent } from '../about-this/about-this.component';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';

/**
 * Component for the portfolio infos dialog
 * 
 * @export
 */
@Component({
  selector: 'app-portfolio-infos-dialog',
  imports: [
    AboutMeComponent,
    ContactLinksComponent,
    AboutThisComponent,
    TechStackComponent,
    MatDividerModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './portfolio-infos-dialog.component.html',
  styleUrl: './portfolio-infos-dialog.component.scss'
})
export class PortfolioInfosDialogComponent {

  /**
   * Initializes the component
   * Injects required services for dialog
   * 
   * @param dialogRef Service for dialog
   */
  constructor(
    private readonly dialogRef: MatDialogRef<PortfolioInfosDialogComponent>,
  ) { }

  /**
   * Closes the dialog
   */
  closeDialog(): void {
    this.dialogRef.close();
  }
}
