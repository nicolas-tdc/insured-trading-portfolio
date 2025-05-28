import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PoliciesService, Policy } from '../../core';
import { CommonModule } from '@angular/common';
import { CardItemPolicyComponent } from '../../components/policies/card-item-policy/card-item-policy.component';

@Component({
  selector: 'app-policy-details',
  imports: [
    CommonModule,
    CardItemPolicyComponent,
  ],
  templateUrl: './policy-details.component.html',
  styleUrl: './policy-details.component.scss'
})
export class PolicyDetailsComponent {

  // Properties

  private _policy: Policy | null = null;

  // Accessors

  get policy() { return this._policy; }

  // Lifecycle

  constructor(
    private route: ActivatedRoute,
    private policiesService: PoliciesService,
  ) {}

  ngOnInit(): void {
    const paramId = this.route.snapshot.paramMap.get('accountId');
    if (!paramId) {
      return;
    }

    this.policiesService.getItem(paramId).subscribe(data => {
      this._policy = data;
    });
  }
}
