import { Component, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Policy } from '../../core/policy/model';
import { PolicyService } from '../../core/policy/policy.service';
import { PolicyDetailsComponent } from '../../core/policy/component/policy-details/policy-details.component';

@Component({
  selector: 'app-policy-page',
  imports: [
    CommonModule,
    RouterLink,
    PolicyDetailsComponent,
  ],
  templateUrl: './policy-page.component.html',
  styleUrl: './policy-page.component.scss'
})
export class PolicyPageComponent {

  // Properties

  policy = signal<Policy | null>(null);

  // Lifecycle

  constructor(
    private route: ActivatedRoute,
    private policyService: PolicyService,
  ) {}

  ngOnInit(): void {
    const policyId = this.route.snapshot.paramMap.get('policyId');
    if (!policyId) {
      return;
    }

    this.policyService.getItem(policyId).subscribe(data => {
      this.policy.set(data);
    });
  }
}
