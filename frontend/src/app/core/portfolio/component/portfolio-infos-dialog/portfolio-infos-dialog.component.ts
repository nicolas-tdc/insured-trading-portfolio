import { Component } from '@angular/core';
import { AboutMeComponent } from '../about-me/about-me.component';
import { ContactLinksComponent } from '../contact-links/contact-links.component';
import { TechStackComponent } from '../tech-stack/tech-stack.component';
import { AboutThisComponent } from '../about-this/about-this.component';
import { MatDividerModule } from '@angular/material/divider';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-portfolio-infos-dialog',
  imports: [
    AboutMeComponent,
    ContactLinksComponent,
    AboutThisComponent,
    TechStackComponent,
    MatDividerModule,
    MatCardModule,
  ],
  templateUrl: './portfolio-infos-dialog.component.html',
  styleUrl: './portfolio-infos-dialog.component.scss'
})
export class PortfolioInfosDialogComponent {

}
