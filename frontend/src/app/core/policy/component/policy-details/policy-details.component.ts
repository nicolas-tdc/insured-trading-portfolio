import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { CommonModule } from '@angular/common';
import { CopyToClipboardComponent } from '../../../shared/component/copy-to-clipboard/copy-to-clipboard.component';
import { MatButtonModule } from '@angular/material/button';
import { FormatAmountPipe } from '../../../currency/pipe/format-amount';
import { EntityService } from '../../../entity/service';
import { MatIconModule } from '@angular/material/icon';
import { PolicyService } from '../../service';
import { Policy } from '../../model';

@Component({
  selector: 'app-policy-details',
  imports: [
    CommonModule,
    MatCardModule,
    MatChipsModule,
    MatButtonModule,
    MatIconModule,
    CopyToClipboardComponent,
    FormatAmountPipe,
  ],
  templateUrl: './policy-details.component.html',
  styleUrl: './policy-details.component.scss'
})
export class PolicyDetailsComponent {


  /**
   * Get the policy
   * Provided by the PolicyService
   */
  public get policy(): Policy | undefined | null { return this.policyService.userPolicy(); }

  /**
   * Initializes the component
   * Injects required services for entities and policies
   * 
   * @param entityService Service for entities
   * @param policyService Service for policies
   */
  constructor(
    private readonly entityService: EntityService,
    private readonly policyService: PolicyService,
  ) { }

  /**
   * Returns the class for the policy status
   * 
   * @returns string
   */
  getPolicyStatusClass(): string {
    return this.entityService.getStatusClass(this.policy?.statusCode);
  }
}
