import { Component } from '@angular/core';
import { ContactLinksComponent } from '../../../core/portfolio/component/contact-links/contact-links.component';
import { AboutMeComponent } from '../../../core/portfolio/component/about-me/about-me.component';
import { TechStackComponent } from '../../../core/portfolio/component/tech-stack/tech-stack.component';
import { PortfolioInfosButtonComponent } from '../../../core/portfolio/component/portfolio-infos-button/portfolio-infos-button.component';

/**
 * FooterComponent
 * 
 * Displays the footer section of the application.
 */
@Component({
  selector: 'app-footer',
  imports: [
    ContactLinksComponent,
    PortfolioInfosButtonComponent,
    AboutMeComponent,
    TechStackComponent,
  ],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss'
})
export class FooterComponent { }
