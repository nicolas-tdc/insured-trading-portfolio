import { Component, input, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { CommonModule } from '@angular/common';
import { CopyToClipboardComponent } from '../../../shared/component/copy-to-clipboard/copy-to-clipboard.component';
import { MatButtonModule } from '@angular/material/button';
import { FormatAmountPipe } from '../../../currency/pipe/format-amount';
import { EntityService } from '../../../entity/service';
import { MatIconModule } from '@angular/material/icon';
import { PolicyService } from '../../service';

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

  // Properties

  public get policy() { return this.policyService.userPolicy(); }

  // Lifecycle

  constructor(
    private entityService: EntityService,
    private policyService: PolicyService,
  ) { }

  getPolicyStatusClass(): string {
    return this.entityService.getStatusClass(this.policy?.statusCode);
  }
}
