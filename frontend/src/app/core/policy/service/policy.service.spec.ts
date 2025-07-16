import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { PolicyService } from './policy.service';
import { Policy, PolicyRequest } from '../model';
import { PolicyType } from '../model/policy-type.model';
import { provideHttpClient } from '@angular/common/http';

describe('PolicyService', () => {
  let service: PolicyService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        PolicyService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(PolicyService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('#selectPolicy and #clearSelectedPolicy', () => {
    it('should set and clear selected policy id', () => {
      service.selectPolicy('policy123');
      expect((service as any).selectedPolicyId()).toBe('policy123');
      service.clearSelectedPolicy();
      expect((service as any).selectedPolicyId()).toBeNull();
    });
  });

  describe('#getItem', () => {
    it('should GET a policy by ID', (done: DoneFn) => {
      const expectedPolicy: Policy = {
        id: 'p456',
        typeCode: 'HOME',
        typeDisplayName: 'Home',
        statusCode: 'ACTIVE',
        statusDisplayName: 'Active',
        policyNumber: 'POL654321',
        accountNumber: 'ACC1234',
        currencyCode: 'USD',
        currencySymbol: '$',
        currencyFractionDigits: 2,
        premium: 1000,
        coverageAmount: 50000,
        startDate: new Date('2023-01-01'),
        endDate: new Date('2024-01-01'),
      };

      service.getItem('p456').subscribe(policy => {
        expect(policy).toEqual(expectedPolicy);
        done();
      });

      const req = httpMock.expectOne('/api/policy/p456');
      expect(req.request.method).toBe('GET');
      req.flush(expectedPolicy);
    });
  });

  describe('#getTypes', () => {
    it('should GET policy types', (done: DoneFn) => {
      const types: PolicyType[] = [
        { code: 'AUTO', displayName: 'Auto' },
        { code: 'HOME', displayName: 'Home' },
      ];

      service.getTypes().subscribe(res => {
        expect(res).toEqual(types);
        done();
      });

      const req = httpMock.expectOne('/api/policy/type');
      expect(req.request.method).toBe('GET');
      req.flush(types);
    });
  });
});
