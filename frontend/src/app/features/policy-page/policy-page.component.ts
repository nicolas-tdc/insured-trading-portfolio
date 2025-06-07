import { Component, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Policy } from '../../core/policy/model';
import { PolicyService } from '../../core/policy/service/policy.service';
import { PolicyDetailsComponent } from '../../core/policy/component/policy-details/policy-details.component';
import { MatButtonModule } from '@angular/material/button';

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
export class PolicyPageComponent {

  // Properties

  public get policy() { return this.policyService.userPolicy(); }

  // Lifecycle

  constructor(
    private route: ActivatedRoute,
    private policyService: PolicyService,
  ) {}

  ngOnInit(): void {
    const policyId = this.route.snapshot.paramMap.get('policyId');
    if (!policyId) return;

    this.policyService.selectPolicy(policyId);
  }

  ngOnDestroy(): void {
    this.policyService.selectPolicy(null);
  }
}
