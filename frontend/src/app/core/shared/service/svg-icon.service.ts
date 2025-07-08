import { Injectable } from '@angular/core';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';

@Injectable({
  providedIn: 'root'
})
export class SvgIconService {

  // Icons

  private icons: { name: string, path: string }[] = [
    { name: 'linkedin', path: 'assets/icons/linkedin.svg' },
    { name: 'github', path: 'assets/icons/github.svg' },
    { name: 'angular', path: 'assets/icons/angular.svg' },
    { name: 'java', path: 'assets/icons/java.svg' },
    { name: 'spring', path: 'assets/icons/spring.svg' },
    { name: 'postgresql', path: 'assets/icons/postgresql.svg' },
    { name: 'docker', path: 'assets/icons/docker.svg' },
  ];

  // Lifecycle

  constructor(
    private domSanitizer: DomSanitizer,
    private matIconRegistry: MatIconRegistry,
  ) { }

  // Register icons

  public registerIcons(): void {
    this.icons.forEach(icon => {
      this.matIconRegistry.addSvgIcon(
        icon.name,
        this.domSanitizer.bypassSecurityTrustResourceUrl(icon.path)
      );
    });
  }
}
