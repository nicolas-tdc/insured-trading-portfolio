import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PolicyService } from '../../core/policy/service';
import { PolicyDetailsComponent } from '../../core/policy/component/policy-details/policy-details.component';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../core/auth/service';

/**
 * PolicyPageComponent
 * 
 * Displays the details of a specific policy.
 */
@Component({
  selector: 'app-policy-page',
  imports: [
    CommonModule,
    RouterLink,
    PolicyDetailsComponent,
    MatButtonModule,
  ],
  templateUrl: './policy-page.component.html',
  styleUrl: './policy-page.component.scss'
})
export class PolicyPageComponent implements OnInit, OnDestroy {

  /**
   * Initializes the component.
   * Injects required services for policy data and routing.
   * 
   * @param route 
   * @param policyService 
   */
  constructor(
    private readonly policyService: PolicyService,
    private readonly authService: AuthService,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
  ) { }

  /**
   * Lifecycle hook called on component initialization.
   * Selects the policy based on the route parameter.
   */
  ngOnInit(): void {
    // Redirect to authentication page if not logged in
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/authentication']);
    }

    // Get the policyId from the route
    const policyId: string | null = this.route.snapshot.paramMap.get('policyId');
    if (!policyId) return;

    // Select the policy in policy service
    this.policyService.selectPolicy(policyId);
  }

  /**
   * Lifecycle hook called on component destruction.
   * Clears the selected policy.
   */
  ngOnDestroy(): void {
    // Clear the selected policy
    this.policyService.selectPolicy(null);
    this.policyService.clearSelectedPolicy();
  }
}
