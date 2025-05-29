import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CardItemPolicyComponent } from '../../policy/component/card-item-policy/card-item-policy.component';
import { Policy } from '../../policy/model';
import { PolicyService } from '../../policy/policy.service';

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
    private policyService: PolicyService,
  ) {}

  ngOnInit(): void {
    const paramId = this.route.snapshot.paramMap.get('accountId');
    if (!paramId) {
      return;
    }

    this.policyService.getItem(paramId).subscribe(data => {
      this._policy = data;
    });
  }
}
